package com.android.byc.myhousecoins.interfaces;

import com.android.byc.myhousecoins.db.CurrencyTasksEntity;
import com.android.byc.myhousecoins.db.UserModelEntity;

import java.util.List;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 14:54
 * @description
 */
public interface IMyCoinsContract {
    interface View {
        void refreshMyCoins(UserModelEntity userModelEntity);

    }
    interface Presenter {
        void fetchDataFromLocal();
        void refreshCoins();
    }
}
