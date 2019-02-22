package com.android.byc.myhousecoins.model;

import android.content.Context;
import android.content.Intent;

import com.android.byc.myhousecoins.view.MainActivity;
import com.android.byc.myhousecoins.view.SecondActivity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 14:04
 * @description
 */
public class StrategyMapper {

    public static final String WATCH_FOOWW_COLLEGE = "1";
    public static final String SHARE_FOOWW_COLLEGE = "2";
    public static final String ADD_FRIENDS = "3";
    public static final String INVITE_REGISTE = "4";
    public static final String INVITE_OPEN = "5";

    public static final Map<String, Strategy> STRATEGY_MAP = new HashMap<>();
    static {
        Consumer<Context> goFoowwCollege =new Consumer<Context>() {
            @Override
            public void accept(Context context) {
                Intent intentCollege = new Intent(context, SecondActivity.class);
                intentCollege.putExtra("fromActivity", "MainActivity");
                context.startActivity(intentCollege);
            }
        };

        Consumer<Context> goFriends =new Consumer<Context>() {
            @Override
            public void accept(Context context) {
                Intent intentFriends = new Intent(context, SecondActivity.class);
                context.startActivity(intentFriends);
            }
        };
        STRATEGY_MAP.put(WATCH_FOOWW_COLLEGE, new Strategy("去观看", goFoowwCollege));
        STRATEGY_MAP.put(SHARE_FOOWW_COLLEGE, new Strategy("去分享", goFoowwCollege));
        STRATEGY_MAP.put(ADD_FRIENDS, new Strategy("去添加", goFriends));
        STRATEGY_MAP.put(INVITE_REGISTE, new Strategy("去邀请", goFriends));
        STRATEGY_MAP.put(INVITE_OPEN, new Strategy("去邀请", goFriends));
    }
    public static Strategy getStrategy(String pkTask) {
        return STRATEGY_MAP.get(pkTask);
    }
}
