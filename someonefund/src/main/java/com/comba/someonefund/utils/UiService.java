package com.comba.someonefund.utils;

import com.comba.someonefund.beans.IndustryWorktype;
import com.comba.someonefund.net.RetrofitWrapper;
import com.comba.someonefund.net.retrofitService.IndusWorkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chenhailin on 2017/7/13.
 */

public class UiService {

    private  static UiService uiInsatance;

    private ArrayList<String> workTypes;
    private ArrayList<String> professinalTypes;
    private List<IndustryWorktype.ValueEntity.WorkTypeEntity> workTypeEntities;
    private List<IndustryWorktype.ValueEntity.IndustryEntity> industryEntities;

    public  void getProfessionWorkType(Notice notice) {
                this.notice=notice;
        RetrofitWrapper.getInstance().create(IndusWorkService.class).getWorkType().
                enqueue(new Callback<IndustryWorktype>() {
                    @Override
                    public void onResponse(Call<IndustryWorktype> call, Response<IndustryWorktype> response) {
                        IndustryWorktype result=  response.body();
                        if(result==null){
//                            Toast.makeText(RegisterActivity.this,R.string.net_failed,Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (result.getCode()==0) {
                            resetOptions(result );
                        }else{
//                            Toast.makeText(RegisterActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<IndustryWorktype> call, Throwable t) {
//                        Toast.makeText(RegisterActivity.this,R.string.net_failed,Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private  void resetOptions(IndustryWorktype result) {
         workTypeEntities= result.getValue().getWorkType();
        industryEntities= result.getValue().getIndustry();
        workTypes.clear();
        for(IndustryWorktype.ValueEntity.WorkTypeEntity bean:workTypeEntities){
            workTypes.add(bean.getName());
        }
        for (IndustryWorktype.ValueEntity.IndustryEntity bean:industryEntities){
            professinalTypes.add(bean.getName());
        }
        if(notice!=null){
            notice.loadOver();
        }
    }

    public static UiService getUiInstance(){
        if(uiInsatance==null){
            uiInsatance=new UiService();
        }
        return  uiInsatance;
    }

    private  UiService(){
        workTypes=new ArrayList<>();
        professinalTypes=new ArrayList<>();
    }

    public int getIdFromWorkType(String name){
        if(workTypeEntities==null||workTypeEntities.size()<=0){
            return -1;
        }
        int index=-1;
        for(IndustryWorktype.ValueEntity.WorkTypeEntity bean:workTypeEntities){
           if(bean.getName().equals(name)){
               index=bean.getId();
               break;
           }
        }
        return  index;
    }

    public ArrayList<String> getWorkTypes() {
        return workTypes;
    }

    public ArrayList<String> getProfessinalTypes() {
        return professinalTypes;
    }

    public int getIdFromIndustryEntities(String name){
        if(industryEntities==null||industryEntities.size()<=0){
            return -1;

        }
        int index=-1;
        for(IndustryWorktype.ValueEntity.IndustryEntity bean:industryEntities){
            if(bean.getName().equals(name)){
                index=bean.getId();
                break;
            }
        }
        return  index;
    }

    public interface  Notice{
        void loadOver();
    }
    public Notice notice;
}
