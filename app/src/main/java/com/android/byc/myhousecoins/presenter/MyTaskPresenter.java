package com.android.byc.myhousecoins.presenter;

import android.util.Log;

import com.android.byc.myhousecoins.db.CurrencyTaskRecordsEntity;
import com.android.byc.myhousecoins.db.CurrencyTasksEntity;
import com.android.byc.myhousecoins.db.DBManager;
import com.android.byc.myhousecoins.db.DaoSession;
import com.android.byc.myhousecoins.db.DataTable;
import com.android.byc.myhousecoins.interfaces.IMyTaskContract;
import com.android.byc.myhousecoins.myapplication.MyApplication;
import com.android.byc.myhousecoins.utility.Network;
import com.android.byc.myhousecoins.utility.Response;
import com.android.byc.myhousecoins.view.CurrencyTaskInfo;
import com.android.byc.myhousecoins.view.MyTaskFragment;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 17:48
 * @description
 */
public class MyTaskPresenter extends BasePresenter<IMyTaskContract.View> implements IMyTaskContract.Presenter{

    private DaoSession daoSession;
    public MyTaskPresenter(MyTaskFragment view) {
        this.view = view;
        this.context = view.getContext();
        this.dbManager = new DBManager(context);
    }

    @Override
    public void fetchDataFromLocal() {
        Callable<List<CurrencyTasksEntity>> callable = new Callable<List<CurrencyTasksEntity>>() {
            @Override
            public List<CurrencyTasksEntity> call() throws Exception {
                List<CurrencyTasksEntity> currencyTasks =
                        MyApplication.getmDaoSession().queryRaw(CurrencyTasksEntity.class, "");
                List<CurrencyTaskRecordsEntity> currencyTaskRecords =
                        MyApplication.getmDaoSession().queryRaw(CurrencyTaskRecordsEntity.class, "");
                return new CurrencyTaskInfo(currencyTasks, currencyTaskRecords).getAssociatedCurrencyTasks();
            }
        };

        Observable.fromCallable(callable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<List<CurrencyTasksEntity>>() {
                    @Override
                    public void accept(List<CurrencyTasksEntity> currencyTasks) throws Exception {
                        view.refreshTaskPanel(currencyTasks);
                    }
                })
                .subscribe(new Observer<List<CurrencyTasksEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<CurrencyTasksEntity> currencyTasksEntities) {

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

    /**
     *
     */
    @Override
    public void fetchDataFromRemote() {

        String pkUser = "6342ba83-d37d-47a5-b629-8c500d52a374";
        String updateTime = "";
        List<CurrencyTasksEntity> currencyTasks;
        Network.getInstance().getCoinsTaskApi()
                .getCurrencyTaskInfo(pkUser, updateTime)
                .map(new Function<Response<List<DataTable>>, CurrencyTaskInfo>() {
                    @Override
                    public CurrencyTaskInfo apply(Response<List<DataTable>> dataTableResponse) throws Exception {
                        List<CurrencyTasksEntity> currencyTasks = dataTableResponse.getData().get(0)
                                .GetEntities(CurrencyTasksEntity.class);
                        List<CurrencyTaskRecordsEntity> currencyTaskRecords = dataTableResponse.getData().get(1)
                                .GetEntities(CurrencyTaskRecordsEntity.class);
                        return new CurrencyTaskInfo(currencyTasks, currencyTaskRecords);
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<CurrencyTaskInfo>() {
                    @Override
                    public void accept(CurrencyTaskInfo currencyTasks) {
                        view.refreshTaskPanel(currencyTasks.getAssociatedCurrencyTasks());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .doOnNext(new Consumer<CurrencyTaskInfo>() {
                    @Override
                    public void accept(CurrencyTaskInfo currencyTaskInfo) throws Exception {
                        MyApplication.getmDaoSession().getCurrencyTasksEntityDao().insertOrReplaceInTx(currencyTaskInfo.getCurrencyTasks());
                        MyApplication.getmDaoSession().getCurrencyTaskRecordsEntityDao().insertOrReplaceInTx(currencyTaskInfo.getCurrencyTaskRecords());
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Observer<CurrencyTaskInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CurrencyTaskInfo currencyTaskInfo) {

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
