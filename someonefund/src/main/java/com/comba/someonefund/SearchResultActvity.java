package com.comba.someonefund;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comba.someonefund.adapters.PhoneClickListenr;
import com.comba.someonefund.adapters.SearchResultReAdapter;
import com.comba.someonefund.beans.FindResultBean;
import com.comba.someonefund.beans.SearchResult;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenhailin on 2017/7/12.
 */

public class SearchResultActvity extends AppCompatActivity {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_Reuslt)
    RecyclerView rvReusltRecycleView;

    SearchResultReAdapter adapter;

    ArrayList<FindResultBean.ValueEntity> results;
    private FindResultBean findResultBean;
    FindResultBean.ValueEntity waitToCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_layout);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("SearchResult")) {
            findResultBean = (FindResultBean) bundle.getSerializable("SearchResult");
        }
        llBack.setVisibility(View.VISIBLE);
        tvTitle.setText("查找结果");
        initViews();
        initData();
    }


    private void initViews() {
        results = new ArrayList<>();
        rvReusltRecycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchResultReAdapter(R.layout.search_result_item_view, results);
        rvReusltRecycleView.setAdapter(adapter);
        adapter.setPhoneClickListener(new PhoneClickListenr() {
            @Override
            public void call(final FindResultBean.ValueEntity bean) {
                StyledDialog.buildIosAlert("提示", "呼叫"+bean.getName()+"?", new MyDialogListener() {
                    @Override
                    public void onFirst() {
                        if (AndPermission.hasPermission(SearchResultActvity.this, Manifest.permission.CALL_PHONE)) {
                            callTargetOne(bean);
                        } else {
                            AndPermission.with(SearchResultActvity.this)
                                    .permission(Manifest.permission.CALL_PHONE)
                                    .requestCode(100)
                                    .send();
                            waitToCall=bean;
                        }
                    }
                    @Override
                    public void onSecond() {

                    }
                }).show();
            }
        });
    }

    private void callTargetOne(FindResultBean.ValueEntity bean) {
        try {
            Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + bean.getPhone()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, new PermissionListener() {
            @Override
            public void onSucceed(int requestCode, List<String> grantPermissions) {
                callTargetOne(waitToCall);
            }

            @Override
            public void onFailed(int requestCode, List<String> deniedPermissions) {
            }
        });
    }

    private void initData() {
        adapter.setData((ArrayList<FindResultBean.ValueEntity>) findResultBean.getValue());
    }

    @OnClick(R.id.ll_back)
    public void onViewClicked() {
        this.onBackPressed();
    }
}
