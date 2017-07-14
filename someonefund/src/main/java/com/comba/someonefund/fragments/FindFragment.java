package com.comba.someonefund.fragments;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.comba.someonefund.LoginActivity;
import com.comba.someonefund.MainActivity;
import com.comba.someonefund.R;
import com.comba.someonefund.RegisterActivity;
import com.comba.someonefund.SearchResultActvity;
import com.comba.someonefund.beans.FindResultBean;
import com.comba.someonefund.beans.LoginResult;
import com.comba.someonefund.beans.SearchResult;
import com.comba.someonefund.net.RetrofitWrapper;
import com.comba.someonefund.net.retrofitService.FindService;
import com.comba.someonefund.net.retrofitService.LoginService;
import com.comba.someonefund.net.retrofitService.UserSateService;
import com.comba.someonefund.utils.UiService;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chenhailin on 2017/7/11.
 */

public class FindFragment extends Fragment {
    @BindView(R.id.ed_companyName)
    EditText edCompanyName;
    @BindView(R.id.tv_professionalDeatil)
    TextView tvProfessionalDeatil;
    @BindView(R.id.rel_professional)
    RelativeLayout relProfessional;
    @BindView(R.id.tv_typeDetail)
    TextView tvTypeDetail;
    @BindView(R.id.rel_workType)
    RelativeLayout relWorkType;
    Unbinder unbinder;
    private View mFragmentView;
    @BindView(R.id.apbt_goFind)
    AppCompatButton abt_foFind;

    @BindColor(R.color.bottom_dialog_certain) int certain; // int or ColorStateList field

    private OptionsPickerView professionPicker,workTypePicker;
    private ArrayList<String> professionOptions,workTypeOptions;
    private LoginResult loginResult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loginResult= ((MainActivity)getActivity()).result;
        if (mFragmentView != null) {
            ViewGroup parent = (ViewGroup) mFragmentView.getParent();
            if (parent != null) {
                parent.removeView(mFragmentView);
            }
        } else {
            mFragmentView = inflater.inflate(R.layout.find_fragment_layout, container, false);
            ButterKnife.bind(this, mFragmentView);
            loadWorkTypeProfessional();
        }
        unbinder = ButterKnife.bind(this, mFragmentView);
        return mFragmentView;
    }

    private void loadWorkTypeProfessional() {
        if (UiService.getUiInstance().getProfessinalTypes().size()<=0) {
            UiService.getUiInstance().getProfessionWorkType(new UiService.Notice() {
                @Override
                public void loadOver() {
                    initView();
                }
            });
        } else{ initView();}
    }

    private void initView() {
            professionPicker = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    String str1=UiService.getUiInstance().getProfessinalTypes().get(options1);

                    tvProfessionalDeatil.setText(str1);
                }
            }).setSubmitText("完成"). setContentTextSize(18).setSubmitColor(certain).setCancelColor(certain).build();;
            professionPicker.setPicker(UiService.getUiInstance().getProfessinalTypes());

            workTypePicker = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    String str1=UiService.getUiInstance().getWorkTypes().get(options1);

                    tvTypeDetail.setText(str1);
                }
            }).setSubmitText("完成"). setContentTextSize(18).setSubmitColor(certain).setCancelColor(certain).build();
            workTypePicker.setPicker(UiService.getUiInstance().getWorkTypes());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({ R.id.rel_professional, R.id.rel_workType,R.id.apbt_goFind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_professionalDeatil:
                break;
            case R.id.rel_professional:
                if(professionPicker!=null){
                    hideSoftInput();
                    professionPicker.show();
                }else{
                    Toast.makeText(getActivity(),"数据加载中，请检查网络稍后重试",Toast.LENGTH_SHORT).show();
                    loadWorkTypeProfessional();
                }
                break;
            case R.id.tv_typeDetail:
                break;
            case R.id.rel_workType:
                if(workTypePicker!=null){
                    hideSoftInput();
                    workTypePicker.show();
                }else{
                    Toast.makeText(getActivity(),"数据加载中，请检查网络稍后重试",Toast.LENGTH_SHORT).show();
                    loadWorkTypeProfessional();
                }
                break;
            case R.id.apbt_goFind:
//                if(TextUtils.isEmpty(edCompanyName.getText().toString())){
//                    Toast.makeText(getActivity(),"请先输入厂商名",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(tvProfessionalDeatil.getText().toString())){
//                    Toast.makeText(getActivity(),"请先选择行业",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(tvTypeDetail.getText().toString())){
//                    Toast.makeText(getActivity(),"请先选择工种",Toast.LENGTH_SHORT).show();
//                    return;
//                }
                finds();
                break;
        }
    }

    private  void finds(){
            RetrofitWrapper.getInstance().create(FindService.class).find(TextUtils.isEmpty(edCompanyName.getText().toString())?null:edCompanyName.getText().toString(), TextUtils.isEmpty(tvProfessionalDeatil.getText().toString())?null: UiService.getUiInstance().getIdFromIndustryEntities(tvProfessionalDeatil.getText().toString()),TextUtils.isEmpty(tvTypeDetail.getText().toString())?null:UiService.getUiInstance().getIdFromWorkType(tvTypeDetail.getText().toString())).enqueue(new Callback<FindResultBean>() {
                @Override
                public void onResponse(Call<FindResultBean> call, Response<FindResultBean> response) {
                    FindResultBean result = response.body();
                    if (result == null) {
                        Toast.makeText(getActivity(), R.string.net_failed, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (result.getCode() == 0) {
                        if (result.getValue()!=null&&result.getValue().size()>0) {
                            Intent intent = new Intent();
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("SearchResult",result);
                            intent.putExtras(bundle);
                            intent.setClassName(getActivity(), SearchResultActvity.class.getName());
                            startActivity(intent);
                        }else{
                            Toast.makeText(getActivity(), "未查询到符合条件的人员", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<FindResultBean> call, Throwable t) {
                    Toast.makeText(getActivity(), R.string.net_failed, Toast.LENGTH_SHORT).show();
                }
            });
        }

    private void hideSoftInput(){
        try {
            ((InputMethodManager)getActivity().getSystemService(Service.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
