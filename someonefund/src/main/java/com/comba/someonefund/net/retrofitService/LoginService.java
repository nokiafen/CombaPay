package com.comba.someonefund.net.retrofitService;

import com.comba.someonefund.beans.LoginResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chenhailin on 2017/7/13.
 */

public interface LoginService {

    @FormUrlEncoded
    @POST("api/login")
    Call<LoginResult> login(@Field("name") String name, @Field("password") String password);
}
