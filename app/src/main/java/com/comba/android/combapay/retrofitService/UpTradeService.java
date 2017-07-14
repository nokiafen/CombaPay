package com.comba.android.combapay.retrofitService;

import com.comba.android.combapay.beans.TradeNumResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chenhailin on 2017/6/20.
 */


public interface UpTradeService {
    @FormUrlEncoded
    @POST("api/unionpay/createOrder")
    Call<TradeNumResult> getTradeNum(@Field("orderAmount") String oderAmount, @Field("merchantId") String merchantId);
}


