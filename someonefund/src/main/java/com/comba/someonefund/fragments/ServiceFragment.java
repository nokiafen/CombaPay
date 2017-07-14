package com.comba.someonefund.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.comba.someonefund.MainActivity;
import com.comba.someonefund.R;
import com.comba.someonefund.beans.LoginResult;
import com.comba.someonefund.beans.StateModifyResult;
import com.comba.someonefund.net.RetrofitWrapper;
import com.comba.someonefund.net.retrofitService.UserSateService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chenhailin on 2017/7/11.
 */

public class ServiceFragment extends Fragment {
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.switch_compat)
    SwitchCompat switchCompat;
    Unbinder unbinder;
    private View mFragmentView;
    private LoginResult loginResult;

    private  boolean returnning=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginResult= ((MainActivity)getActivity()).result;
        if (mFragmentView != null) {
            ViewGroup parent = (ViewGroup) mFragmentView.getParent();
            if (parent != null) {
                parent.removeView(mFragmentView);
            }
        } else {
            mFragmentView = View.inflate(getActivity(), R.layout.service_fragment_layout, null);
            ButterKnife.bind(this, mFragmentView);
            initView();
        }
        unbinder = ButterKnife.bind(this, mFragmentView);
        return mFragmentView;
    }


    private void initView() {
        if(loginResult.getValue().getAddressShow()==1){ //地址 1 show 0 不显示
            switchCompat.setChecked(true);
            switchCompat.setTag(1);
        }else{
            switchCompat.setChecked(false);
            switchCompat.setTag(0);
        }

        if(loginResult.getValue().getShowStatus()==0){//idle 0 idle  1 noidle
            radiogroup.check(R.id.idle);
            radiogroup.setTag(0);
        }else{
            radiogroup.check(R.id.no_idle);
            radiogroup.setTag(1);
        }
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(returnning){
                    return;
                }
                  switch(checkedId){
                    case  R.id.idle:
                        radiogroup.setTag(0);
                        modifyState(false);
                     break;
                      case R.id.no_idle:
                          radiogroup.setTag(1);
                          modifyState(false);
                     break;

                    }
            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(returnning){
                    return;
                }
                switchCompat.setTag(isChecked?1:0);
                modifyState(true);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void modifyState(final boolean addressModify) {
        RetrofitWrapper.getInstance().create(UserSateService.class).modifyState((Integer)radiogroup.getTag(), (Integer)switchCompat.getTag(),loginResult.getValue().getId()).enqueue(new Callback<StateModifyResult>() {
            @Override
            public void onResponse(Call<StateModifyResult> call, Response<StateModifyResult> response) {
                StateModifyResult result = response.body();
                if (result == null) {
                    Toast.makeText(getActivity(), R.string.net_failed, Toast.LENGTH_SHORT).show();
                    netErrorReturn(addressModify);
                    return;
                }
                if (result.getCode() == 0) {
                    Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    netErrorReturn(addressModify);
                }
            }

            @Override
            public void onFailure(Call<StateModifyResult> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.net_failed, Toast.LENGTH_SHORT).show();
                netErrorReturn(addressModify);
            }
        });
    }

    /**
     * 网络异常 状态回退
     * @param addressModify
     */
    private void netErrorReturn(boolean addressModify){
        returnning=true; //暂时禁掉事件 防止递归调用
        if(addressModify){
            switchCompat.toggle();
            switchCompat.setTag(switchCompat.isChecked()?1:0);
        }else{
            Integer tag= (Integer) radiogroup.getTag();
            if(tag==0){
                radiogroup.setTag(1);
                radiogroup.check(R.id.no_idle);
            }else{
                radiogroup.setTag(0);
                radiogroup.check(R.id.idle);
            }
        }
        returnning=false;
    }

}
