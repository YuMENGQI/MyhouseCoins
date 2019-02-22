package com.android.byc.myhousecoins.interfaces;

import com.android.byc.myhousecoins.db.CurrencyRecordsEntity;

import java.util.List;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 17:46
 * @description
 */
public interface IRecordDetailContract {
     interface View {
         void refreshList(List<CurrencyRecordsEntity> allList);
         void refreshPartList(List<CurrencyRecordsEntity> partList);
    }
    interface Presenter {
         void fetchData();
    }
}
