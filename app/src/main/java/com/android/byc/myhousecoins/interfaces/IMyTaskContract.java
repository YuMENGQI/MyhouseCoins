package com.android.byc.myhousecoins.interfaces;

import com.android.byc.myhousecoins.db.CurrencyTasksEntity;

import java.util.List;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 17:45
 * @description
 */
public interface IMyTaskContract {

     interface View {
         void refreshTaskPanel(List<CurrencyTasksEntity> currencyTasks);
    }

     interface Presenter {
         void fetchDataFromLocal();
         void fetchDataFromRemote();
    }
}
