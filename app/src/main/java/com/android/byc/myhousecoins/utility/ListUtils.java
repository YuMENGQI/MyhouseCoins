package com.android.byc.myhousecoins.utility;

import com.android.byc.myhousecoins.db.UserModelEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/21 9:15
 * @description
 */
public class ListUtils {
    // 降序
    public static List<UserModelEntity> filterThenSort(List<UserModelEntity> userModels) {
        List<UserModelEntity> list = new ArrayList<>();
        if (userModels == null || userModels.size() == 0) {
            return list;
        }
        for (UserModelEntity entity : userModels) {
            if (entity.getCurrencyAmount() != 0) {
                list.add(entity);
            }
        }
        Collections.sort(list, new Comparator<UserModelEntity>() {
            @Override
            public int compare(UserModelEntity t1, UserModelEntity t2) {
                return t2.getCurrencyAmount() - t1.getCurrencyAmount();
            }
        });
        return list;
    }
}
