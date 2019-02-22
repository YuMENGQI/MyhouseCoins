package com.android.byc.myhousecoins.presenter;

import android.util.Log;

import com.android.byc.myhousecoins.api.CoinsTaskApi;
import com.android.byc.myhousecoins.db.DBManager;
import com.android.byc.myhousecoins.db.DataTable;
import com.android.byc.myhousecoins.db.RankingListResponse;
import com.android.byc.myhousecoins.db.UserModelEntity;
import com.android.byc.myhousecoins.interfaces.IRankingListContract;
import com.android.byc.myhousecoins.myapplication.MyApplication;
import com.android.byc.myhousecoins.utility.Network;
import com.android.byc.myhousecoins.utility.Response;
import com.android.byc.myhousecoins.view.RankingListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.android.byc.myhousecoins.utility.Utility.ConverterUUIDToHexString;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 17:48
 * @description
 */
public class RankingListPresenter extends BasePresenter<IRankingListContract.View>
implements IRankingListContract.Presenter{
    public RankingListPresenter(RankingListFragment view) {
        this.view = view;
        this.context = view.getContext();
        this.dbManager = new DBManager(context);
    }

    @Override
    public void fetchDataFromLocal() {
        final String pkCompany = "d9201913-78f1-4c4e-9967-5fb7cf4d0473";
        Callable<List<UserModelEntity>> callable = new Callable<List<UserModelEntity>>() {
            @Override
            public List<UserModelEntity> call() throws Exception {
                return MyApplication.getmDaoSession().queryRaw(UserModelEntity.class,
                        "where PKCompany=" + ConverterUUIDToHexString(pkCompany) + " and CurrencyAmount != 0 ");
            }
        };

        Observable.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<UserModelEntity>>() {
                    @Override
                    public void accept(List<UserModelEntity> list) throws Exception {
                        view.refreshRankingList(list);
                    }
                })
                .subscribe(new Observer<List<UserModelEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<UserModelEntity> userModelEntities) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("", "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void fetchDataFromRemote() {
        String pkCompany = "d9201913-78f1-4c4e-9967-5fb7cf4d0473";

        Network.getInstance().getCoinsTaskApi()
                .getCurrencyList(pkCompany)
                .map(new Function<Response<List<DataTable>>, List<UserModelEntity>>() {
                    @Override
                    public List<UserModelEntity> apply(Response<List<DataTable>> listResponse) throws Exception {
                        List<RankingListResponse> rankingListResponses = listResponse.getData().get(0)
                                .GetEntities(RankingListResponse.class);

                        List<UserModelEntity> list = new ArrayList<>();
                        for (RankingListResponse rank : rankingListResponses) {
                            UserModelEntity userModelEntity = new UserModelEntity();
                            userModelEntity.setPKUser(UUID.randomUUID());
                            userModelEntity.setCurrencyAmount(rank.CurrencyAmount);
                            list.add(userModelEntity);
                        }
                        return list;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<UserModelEntity>>() {
                    @Override
                    public void accept(List<UserModelEntity> userModelEntities) throws Exception {

                    }
                });



    }
}
