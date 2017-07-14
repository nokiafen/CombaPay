package com.comba.someonefund.net.retrofitService;

import com.comba.someonefund.beans.IndustryWorktype;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by chenhailin on 2017/7/13.
 */

public interface IndusWorkService {
    @POST("api/industry")
    Call<IndustryWorktype> getWorkType();
}
