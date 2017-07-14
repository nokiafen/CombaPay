package com.comba.someonefund.net.retrofitService;

import com.comba.someonefund.beans.StateModifyResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by chenhailin on 2017/7/13.
 */

public interface UserSateService {
    @FormUrlEncoded
    @POST("api/service")
    Call<StateModifyResult> modifyState(@Field("showStatus") Integer showState, @Field("addressShow") Integer addressShow, @Field("id") Integer id);

}
