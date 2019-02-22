package com.android.byc.myhousecoins.api;

import com.android.byc.myhousecoins.db.CurrencyAmountResponse;
import com.android.byc.myhousecoins.db.DataTable;
import com.android.byc.myhousecoins.utility.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 16:28
 * @description
 */
public interface CoinsTaskApi {
    // 房屋币数量
    @FormUrlEncoded
    @POST("/Mobile/GetFoowwCurrency")
    Observable<Response<CurrencyAmountResponse>> getFoowwCurrency(
            @Field("PKUser")
            String pkUSer
    );
    // 当前任务信息
    @FormUrlEncoded
    @POST("/Mobile/GetCurrencyTaskInfo")
    Observable<Response<List<DataTable>>> getCurrencyTaskInfo(
            @Field("PKUser") String pkUser,
            @Field("updateTime") String updateTime
    );
    // 排行榜信息
    @FormUrlEncoded
    @POST("/Mobile/GetCurrencyList")
    Observable<Response<List<DataTable>>> getCurrencyList(
            @Field("pkCompany") String pkCompany
    );
    // 房屋收入支出
    @FormUrlEncoded
    @POST("/Mobile/GetCurrencyRecords")
    Observable<Response<List<DataTable>>> getCurrencyRecords(
            @Field("PKUser") String pkUser,
            @Field("UpdateTime") String updateTime
    );
}
