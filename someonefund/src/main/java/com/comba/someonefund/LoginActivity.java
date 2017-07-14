package com.comba.someonefund;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.comba.someonefund.beans.LoginResult;
import com.comba.someonefund.net.RetrofitWrapper;
import com.comba.someonefund.net.retrofitService.LoginService;
import com.comba.someonefund.net.retrofitService.UpTradeService;
import com.comba.someonefund.utils.UiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chenhailin on 2017/7/12.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.ed_userName)
    EditText edUserName;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.apbt_goFind)
    AppCompatButton apbtGoFind;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        UiService.getUiInstance().getProfessionWorkType(new UiService.Notice() {
            @Override
            public void loadOver() {
                //
            }
        });
    }

    @OnClick({R.id.apbt_goFind, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.apbt_goFind:
                if (TextUtils.isEmpty(edUserName.getText().toString())) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edPassword.getText().toString())) {
                    Toast.makeText(this, "请输入登录密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                startLogin();
                break;
            case R.id.tv_register:
                Intent intentRegister = new Intent();
                intentRegister.setClassName(this, RegisterActivity.class.getName());
                startActivity(intentRegister);
                break;
        }
    }

    private void startLogin() {
        RetrofitWrapper.getInstance().create(LoginService.class).login(edUserName.getText().toString(), edPassword.getText().toString()).enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                LoginResult result = response.body();
                if (result == null) {
                    Toast.makeText(LoginActivity.this, R.string.net_failed, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (result.getCode() == 0) {
                    Intent intent = new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("LoginResult",result);
                   intent.putExtras(bundle);
                    intent.setClassName(LoginActivity.this, MainActivity.class.getName());
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this, R.string.net_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
