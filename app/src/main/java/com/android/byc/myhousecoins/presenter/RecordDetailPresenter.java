package com.android.byc.myhousecoins.presenter;

import android.util.Log;

import com.android.byc.myhousecoins.db.CurrencyRecordsEntity;
import com.android.byc.myhousecoins.db.DBManager;
import com.android.byc.myhousecoins.db.DataTable;
import com.android.byc.myhousecoins.interfaces.IRecordDetailContract;
import com.android.byc.myhousecoins.myapplication.MyApplication;
import com.android.byc.myhousecoins.utility.Network;
import com.android.byc.myhousecoins.utility.Response;
import com.android.byc.myhousecoins.view.RecordDetailActivity;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
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
 * @date 2019/2/20 17:50
 * @description
 */
public class RecordDetailPresenter extends BasePresenter<IRecordDetailContract.View>
implements IRecordDetailContract.Presenter{

    public RecordDetailPresenter(RecordDetailActivity view) {
        this.view = view;
        this.context = view;
        this.dbManager = new DBManager(context);
    }

    @Override
    public void fetchData() {
        final String pkUser = "6342ba83-d37d-47a5-b629-8c500d52a374";
        Callable<List<CurrencyRecordsEntity>> callable = new Callable<List<CurrencyRecordsEntity>>() {
            @Override
            public List<CurrencyRecordsEntity> call() {
                return MyApplication.getmDaoSession().getCurrencyRecordsEntityDao()
                        .queryRaw("where PKUser = " +  ConverterUUIDToHexString(pkUser) + " order by CreateTime DESC");
            }
        };
        Observable.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<CurrencyRecordsEntity>>() {
                    @Override
                    public void accept(List<CurrencyRecordsEntity> allList) {
                        view.refreshList(allList);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<List<CurrencyRecordsEntity>, Observable<Response<List<DataTable>>>>() {
                    @Override
                    public Observable<Response<List<DataTable>>> apply(List<CurrencyRecordsEntity> allList) {

                        String createTime = ""; // 取最大的createTime
                        if (allList.size() > 0) {
                            createTime = allList.get(0).getCreateTime();
                        }
                        return Network.getInstance().getCoinsTaskApi()
                .getCurrencyRecords(pkUser, createTime);
                    }
                })
                .map(new Function<Response<List<DataTable>>, List<CurrencyRecordsEntity>>() {
                    @Override
                    public List<CurrencyRecordsEntity> apply(Response<List<DataTable>> listResponse) throws Exception{
                        return listResponse.getData().get(0).GetEntities(CurrencyRecordsEntity.class);
                    }
                })
                .doOnNext(new Consumer<List<CurrencyRecordsEntity>>() {
                    @Override
                    public void accept(List<CurrencyRecordsEntity> partList) throws Exception {
                        MyApplication.getmDaoSession().getCurrencyRecordsEntityDao().insertOrReplaceInTx(partList);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<CurrencyRecordsEntity>>() {
                    @Override
                    public void accept(List<CurrencyRecordsEntity> allLocalList) throws Exception {
                        view.refreshList(allLocalList);
                    }
                })
                .subscribe(new Observer<List<CurrencyRecordsEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CurrencyRecordsEntity> currencyRecordsEntities) {

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


}
