package com.comba.someonefund.net.retrofitService;

import com.comba.someonefund.beans.LoginResult;
import com.comba.someonefund.beans.RegisterResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chenhailin on 2017/7/13.
 */

public interface RegiserService {
    @FormUrlEncoded
    @POST("api/register")
    Call<RegisterResult> register(@Field("name") String name, @Field("password") String password, @Field("realName") String realName, @Field("phone")
            String phone, @Field("email") String email, @Field("address") String address, @Field("factory") String factory, @Field("industry") Integer industry, @Field("workType") Integer workType);
}
