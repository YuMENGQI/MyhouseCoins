package com.android.byc.myhousecoins.interfaces;

import com.android.byc.myhousecoins.db.UserModelEntity;

import java.util.List;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 17:45
 * @description
 */
public interface IRankingListContract {
    interface View {
        void refreshMyRank(UserModelEntity user, int rank);
        void refreshRankingList(List<UserModelEntity> users);
    }
    interface Presenter {
        void fetchDataFromLocal();
        void fetchDataFromRemote();
    }
}
