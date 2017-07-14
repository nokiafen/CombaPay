package com.comba.someonefund.net.retrofitService;

import com.comba.someonefund.beans.FindResultBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chenhailin on 2017/7/13.
 */

public interface FindService  {
    @FormUrlEncoded
    @POST("api/findman")
    Call<FindResultBean> find(@Field("factory") String factory, @Field("industry") Integer industry, @Field("workType") Integer workType);
}
