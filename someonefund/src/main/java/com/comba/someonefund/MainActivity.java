package com.comba.someonefund;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comba.someonefund.beans.LoginResult;
import com.comba.someonefund.fragments.FindFragment;
import com.comba.someonefund.fragments.ServiceFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_line)
    TextView tvTitleLine;
    @BindView(R.id.id_title_bar)
    LinearLayout idTitleBar;
    @BindView(R.id.id_viewPager)
    ViewPager idViewPager;
    @BindView(R.id.id_service_tab_img)
    ImageButton idServiceTabImg;
    @BindView(R.id.id_service_text)
    TextView idServiceText;
    @BindView(R.id.id_service_tab)
    LinearLayout idServiceTab;
    @BindView(R.id.id_find_tab_img)
    ImageButton idFindTabImg;
    @BindView(R.id.id_find_text)
    TextView idFindText;
    @BindView(R.id.id_find_tab)
    LinearLayout idFindTab;


    //适配器
    private FragmentPagerAdapter mPagerAdapter;

    private ArrayList<Fragment> fragmentList;
    private Fragment serviceFragment;
    private Fragment findFragment;
    public LoginResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("LoginResult")) {
            result = (LoginResult) bundle.getSerializable("LoginResult");
        }
        initViews();

    }

    private void initViews() {
        fragmentList = new ArrayList<Fragment>();
        serviceFragment = new ServiceFragment();
        findFragment = new FindFragment();
        fragmentList.add(serviceFragment);
        fragmentList.add(findFragment);
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

        idViewPager.setAdapter(mPagerAdapter);

        idViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                idViewPager.setCurrentItem(position);
                tabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabSelected(0);
    }


    private void tabSelected(int position) {
        switch (position) {
            case 0:
                tvTitle.setText("提供服务");
                idServiceTabImg.setImageResource(R.drawable.service_checked);
                idServiceText.setTextColor(getResources().getColor(R.color.colorPrimary));
                idFindTabImg.setImageResource(R.drawable.find_unchecked);
                idFindText.setTextColor(Color.BLACK);
                break;

            case 1:
                tvTitle.setText("找人");
                idServiceTabImg.setImageResource(R.drawable.service_unchecked);
                idServiceText.setTextColor(Color.BLACK);
                idFindTabImg.setImageResource(R.drawable.find_checked);
                idFindText.setTextColor(getResources().getColor(R.color.colorPrimary));

                break;
        }

    }


    @OnClick({R.id.imageView, R.id.ll_back, R.id.tv_title, R.id.tv_title_line, R.id.id_title_bar, R.id.id_viewPager, R.id.id_service_tab, R.id.id_find_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView:
                break;
            case R.id.ll_back:
                break;
            case R.id.tv_title:
                break;
            case R.id.tv_title_line:
                break;
            case R.id.id_title_bar:
                break;
            case R.id.id_viewPager:
                break;
            case R.id.id_service_tab:
                tabSelected(0);
                idViewPager.setCurrentItem(0);

                break;

            case R.id.id_find_tab:
                tabSelected(1);
                idViewPager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
