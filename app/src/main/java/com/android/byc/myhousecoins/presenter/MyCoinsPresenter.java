package com.android.byc.myhousecoins.presenter;

import android.util.Log;

import com.android.byc.myhousecoins.db.CurrencyAmountResponse;
import com.android.byc.myhousecoins.db.CurrencyTaskRecordsEntity;
import com.android.byc.myhousecoins.db.CurrencyTasksEntity;
import com.android.byc.myhousecoins.db.DBManager;
import com.android.byc.myhousecoins.db.UserModelEntity;
import com.android.byc.myhousecoins.interfaces.IMyCoinsContract;
import com.android.byc.myhousecoins.myapplication.MyApplication;
import com.android.byc.myhousecoins.utility.Network;
import com.android.byc.myhousecoins.utility.Response;
import com.android.byc.myhousecoins.view.CurrencyTaskInfo;
import com.android.byc.myhousecoins.view.MainActivity;

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
 * @date 2019/2/20 15:45
 * @description
 */
public class MyCoinsPresenter extends BasePresenter<IMyCoinsContract.View> implements IMyCoinsContract.Presenter {
    public MyCoinsPresenter(MainActivity view) {
        this.view = view;
        this.context = view;
        this.dbManager = new DBManager(view);
    }

    @Override
    public void fetchDataFromLocal() {
        final String pkUser = "6342ba83-d37d-47a5-b629-8c500d52a374";
        Callable<List<UserModelEntity>> callable = new Callable<List<UserModelEntity>>() {
            @Override
            public List<UserModelEntity> call() throws Exception {
                return MyApplication.getmDaoSession().queryRaw(UserModelEntity.class,
                        "where PKUser=" + ConverterUUIDToHexString(pkUser));
            }
        };
        // 查本地账户用来更新积分余额
        Observable.fromCallable(callable)
                .observeOn(Schedulers.io())
                .map(new Function<List<UserModelEntity>, UserModelEntity>() {
                    @Override
                    public UserModelEntity apply(List<UserModelEntity> list) throws Exception {
                        List<UserModelEntity> a = new ArrayList<>();
                        return list.get(0);
                    }
                })
                .doOnNext(new Consumer<UserModelEntity>() {
                    @Override
                    public void accept(UserModelEntity userModel) throws Exception {
                        view.refreshMyCoins(userModel);
                    }
                })
                .subscribe(new Observer<UserModelEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserModelEntity userModelEntity) {

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
    public void refreshCoins() {
        final String pkUser = "6342ba83-d37d-47a5-b629-8c500d52a374";
        Network.getInstance().getCoinsTaskApi()
                .getFoowwCurrency(pkUser)
                .map(new Function<Response<CurrencyAmountResponse>, UserModelEntity>() {
                    @Override
                    public UserModelEntity apply(Response<CurrencyAmountResponse> currencyAmountResponse) throws Exception {

                        List<UserModelEntity> userList = MyApplication.getmDaoSession().queryRaw(UserModelEntity.class, "where PKUser="
                                + ConverterUUIDToHexString(pkUser) );
                        UserModelEntity userModelEntity = new UserModelEntity();
                        int currencyAmount = currencyAmountResponse.getData().getCurrencyCoinsAmount();
                        if (userList.size() > 0) {
                            userList.get(0).setCurrencyAmount(currencyAmount);
                            MyApplication.getmDaoSession().insertOrReplace(userList);
                            return userList.get(0);
                        } else {
                            userModelEntity.setPKUser(UUID.fromString(pkUser));
                            userModelEntity.setCurrencyAmount(currencyAmount);
                            MyApplication.getmDaoSession().insertOrReplace(userModelEntity);

                        }
                        return  userModelEntity;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<UserModelEntity>() {
                    @Override
                    public void accept(UserModelEntity userModel) throws Exception {
                        view.refreshMyCoins(userModel);
                    }
                })
                .subscribe(new Observer<UserModelEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserModelEntity userModelEntity) {
                        view.refreshMyCoins(userModelEntity);

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
