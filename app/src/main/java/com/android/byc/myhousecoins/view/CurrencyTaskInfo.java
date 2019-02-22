package com.android.byc.myhousecoins.view;

import com.android.byc.myhousecoins.db.CurrencyTaskRecordsEntity;
import com.android.byc.myhousecoins.db.CurrencyTasksEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/21 11:26
 * @description
 */
public class CurrencyTaskInfo {
    private List<CurrencyTasksEntity> currencyTasks;
    private List<CurrencyTaskRecordsEntity> currencyTaskRecords;

    public CurrencyTaskInfo(@NonNull List<CurrencyTasksEntity> currencyTasks,
                            List<CurrencyTaskRecordsEntity> currencyTaskRecords) {
        this.currencyTasks = currencyTasks;
        this.currencyTaskRecords = currencyTaskRecords;
    }

    public List<CurrencyTasksEntity> getAssociatedCurrencyTasks() {
        for (CurrencyTasksEntity currencyTask : currencyTasks) {
            List<CurrencyTaskRecordsEntity> associated = new ArrayList<>();
            for (CurrencyTaskRecordsEntity currencyTaskRecord : currencyTaskRecords) {
                if (currencyTaskRecord.getCurrencyTaskId().equals(currencyTask.getId())) {
                associated.add(currencyTaskRecord);
            }
        }
        currencyTask.setCurrencyTaskRecords(associated);
    }
    return currencyTasks;
    }

    public List<CurrencyTasksEntity> getCurrencyTasks() {
        return currencyTasks;
    }

    public void setCurrencyTasks(List<CurrencyTasksEntity> currencyTasks) {
        this.currencyTasks = currencyTasks;
    }

    public List<CurrencyTaskRecordsEntity> getCurrencyTaskRecords() {
        return currencyTaskRecords;
    }

    public void setCurrencyTaskRecords(List<CurrencyTaskRecordsEntity> currencyTaskRecords) {
        this.currencyTaskRecords = currencyTaskRecords;
    }
}
