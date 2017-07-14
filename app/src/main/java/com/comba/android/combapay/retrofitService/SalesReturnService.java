package com.comba.android.combapay.retrofitService;

import com.comba.android.combapay.beans.SalesReturnResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by chenhailin on 2017/6/21.
 */

public interface SalesReturnService {
    @GET("api/unionpay/refund/${orderId}")
    Call<SalesReturnResult> saleRetrun(@Path("orderId") String orderId);
}
