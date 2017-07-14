package com.comba.someonefund;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.comba.someonefund.beans.IndustryWorktype;
import com.comba.someonefund.beans.JsonBean;
import com.comba.someonefund.beans.RegisterResult;
import com.comba.someonefund.net.RetrofitWrapper;
import com.comba.someonefund.net.retrofitService.IndusWorkService;
import com.comba.someonefund.net.retrofitService.RegiserService;
import com.comba.someonefund.net.retrofitService.UpTradeService;
import com.comba.someonefund.utils.GetJsonDataUtil;
import com.comba.someonefund.utils.UiService;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chenhailin on 2017/7/12.
 */

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ed_userName)
    EditText edUserName;
    @BindView(R.id.tv_textpassword)
    TextView tvTextpassword;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.tv_localDeatil)
    TextView tvLocalDeatil;
    @BindView(R.id.rel_local)
    RelativeLayout relLocal;
    @BindView(R.id.ed_factoryName)
    EditText edFactoryName;
    @BindView(R.id.tv_professionalDeatil)
    TextView tvProfessionalDeatil;
    @BindView(R.id.rel_professional)
    RelativeLayout relProfessional;
    @BindView(R.id.tv_typeDetail)
    TextView tvTypeDetail;
    @BindView(R.id.rel_workType)
    RelativeLayout relWorkType;
    @BindView(R.id.apbt_register)
    AppCompatButton apbtRegister;

    private OptionsPickerView professionPicker, workTypePicker;
    private ArrayList<String> professionOptions, workTypeOptions;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private boolean dataHasLoad;
    private ProgressDialog progressDialog;

    @BindColor(R.color.bottom_dialog_certain)
    int certain; // int or ColorStateList field

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
        progressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        tvTitle.setText("新用户注册");
        llBack.setVisibility(View.VISIBLE);
        new AyscTask().execute(); //加载城市data
        loadWorkTypeProfessional();
    }

    private void loadWorkTypeProfessional() {
        if (UiService.getUiInstance().getProfessinalTypes().size() <= 0) {
            UiService.getUiInstance().getProfessionWorkType(new UiService.Notice() {
                @Override
                public void loadOver() {
                    initViews();
                }
            });
        } else {
            initViews();
        }
    }

    private void initViews() {
        professionPicker = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str1 = UiService.getUiInstance().getProfessinalTypes().get(options1);

                tvProfessionalDeatil.setText(str1);
            }
        }).setSubmitText("完成").setContentTextSize(18).setSubmitColor(certain).setCancelColor(certain).build();
        ;
        professionPicker.setPicker(UiService.getUiInstance().getProfessinalTypes());

        workTypePicker = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str1 = UiService.getUiInstance().getWorkTypes().get(options1);

                tvTypeDetail.setText(str1);
            }
        }).setSubmitText("完成").setContentTextSize(18).setSubmitColor(certain).setCancelColor(certain).build();
        workTypePicker.setPicker(UiService.getUiInstance().getWorkTypes());
    }

    @OnClick({R.id.ll_back, R.id.rel_local, R.id.rel_professional, R.id.rel_workType, R.id.apbt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                break;
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.rel_local:
                if (dataHasLoad) {
                    ShowPickerView();
                } else {
                    Toast.makeText(this, "数据加载中，请稍后再试", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.rel_professional:
                if (professionPicker != null) {
                    professionPicker.show();
                } else {
                    Toast.makeText(this, "数据加载中，请检查网络稍后再试", Toast.LENGTH_SHORT).show();
                    loadWorkTypeProfessional();
                }
                break;
            case R.id.rel_workType:
                if (workTypePicker != null) {
                    workTypePicker.show();
                } else {
                    Toast.makeText(this, "数据加载中，请检查网络稍后再试", Toast.LENGTH_SHORT).show();
                    loadWorkTypeProfessional();
                }
                break;
            case R.id.apbt_register:
                if (TextUtils.isEmpty(edUserName.getText().toString())) {
                    Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edPassword.getText().toString())) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edName.getText().toString())) {
                    Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edPhone.getText().toString())) {
                    Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edEmail.getText().toString())) {
                    Toast.makeText(this, "请先输入邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tvLocalDeatil.getText().toString())) {
                    Toast.makeText(this, "请先选择地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edFactoryName.getText().toString())) {
                    Toast.makeText(this, "请先填写厂家名称", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tvProfessionalDeatil.getText().toString())) {
                    Toast.makeText(this, "请先选择行业", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tvTypeDetail.getText().toString())) {
                    Toast.makeText(this, "请先选择工种", Toast.LENGTH_SHORT).show();
                    return;
                }
                //提交。。。
                postRegisterData();
                break;
        }
    }

    /**
     * 提交注册数据
     */
    private void postRegisterData() {
        RetrofitWrapper.getInstance().create(RegiserService.class).register(edUserName.getText().toString(), edPassword.getText().toString(), edName.getText().toString(),
                edPhone.getText().toString(), edEmail.getText().toString(), tvLocalDeatil.getText().toString(), edFactoryName.getText().toString(),
                UiService.getUiInstance().getIdFromIndustryEntities(tvProfessionalDeatil.getText().toString()), UiService.getUiInstance().getIdFromWorkType(tvTypeDetail.getText().toString())).
                enqueue(new Callback<RegisterResult>() {
                    @Override
                    public void onResponse(Call<RegisterResult> call, Response<RegisterResult> response) {
                        RegisterResult result = response.body();
                        if (result == null) {
                            Toast.makeText(RegisterActivity.this, R.string.net_failed, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (result.getCode() == 0) {
                            RegisterActivity.this.finish();
                            Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResult> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, R.string.net_failed, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void getCityData() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(getClass().getSimpleName(), "jsonLoadingFail");
        }
        return detail;
    }

    public class AyscTask extends AsyncTask<Void, Float, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataHasLoad = true;
            initLocationPick();
        }

        @Override
        protected Void doInBackground(Void... params) {
            getCityData();
            return null;
        }
    }

    private void initLocationPick() {


    }


    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = "";
                if (!options1Items.get(options1).getPickerViewText().equals(options2Items.get(options1).get(options2))) {
                    tx = options1Items.get(options1).getPickerViewText() +
                            options2Items.get(options1).get(options2) +
                            options3Items.get(options1).get(options2).get(options3);
                } else {
                    tx = options2Items.get(options1).get(options2) +
                            options3Items.get(options1).get(options2).get(options3);
                }
                tvLocalDeatil.setText(tx);
            }
        })

                .setTitleText("")
                .setCyclic(true, false, true)
                .setSubmitText("完成")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .setSubmitColor(certain).setCancelColor(certain).build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }
}
