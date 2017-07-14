package com.comba.android.combapay.retrofitService;

import com.comba.android.combapay.beans.PayResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by chenhailin on 2017/6/21.
 */

public interface PayResultService {

    @GET("api/unionpay/orderStatus/{orderId}")
    Call<PayResult> getPayResult(@Path("orderId") String oderId);
}
