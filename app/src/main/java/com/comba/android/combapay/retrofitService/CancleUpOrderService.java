package com.comba.android.combapay.retrofitService;

import com.comba.android.combapay.beans.OrderCancleResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chenhailin on 2017/6/21.
 */

public interface CancleUpOrderService {
    @GET("api/unionpay/cancelOrder/${orderId}")
    Call<OrderCancleResult> cancleOrder(@Path("orderId") String oderId);
}
