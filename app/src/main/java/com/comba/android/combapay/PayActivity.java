package com.comba.android.combapay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.comba.android.combapay.adapter.PayMethodAdapter;
import com.comba.android.combapay.common.PayContent;
import com.comba.android.combapay.common.StatusUtil;
import com.comba.android.combapay.unionpay.UPPayActivity;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends AppCompatActivity {
    // @param payType 0 支付宝 1银行卡 2微信
    private static  final  int UPPAYSTARTCODE=0x11;
    /**
     * 该bundle用于存储上一个Activity传入的bundle
     */
    private Bundle bundle;

    private ListView mListView;

    private PayMethodAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setContentView(R.layout.activity_pay);
        StatusUtil.fontColorReset(this,false);
        bundle = getIntent().getExtras();

        List<PayContent> payContents = new ArrayList<>();

        PayContent pay1 = new PayContent();
        pay1.setTitle("支付宝支付");
        pay1.setContent("推荐有支付宝账号的用户使用");
        pay1.setDrawableSource(R.mipmap.ic_launcher);
        pay1.setPayType(0);
        PayContent pay2 = new PayContent();
        pay2.setTitle("微信支付");
        pay2.setContent("每天随机抽取免单-推荐");
        pay2.setDrawableSource(R.mipmap.ic_launcher);
        pay2.setPayType(2);
        PayContent pay3 = new PayContent();
        pay3.setTitle("银行卡支付");
        pay3.setContent("支持储蓄卡，无需开通网银");
        pay3.setDrawableSource(R.mipmap.ic_launcher);
        pay3.setPayType(1);
        payContents.add(pay1);

        payContents.add(pay3);

        payContents.add(pay2);

        mListView = (ListView)findViewById(R.id.payMethodList);
        mAdapter = new PayMethodAdapter(this);
        mAdapter.setPayMethodList(payContents);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(listener);
    }

    private AdapterView.OnItemClickListener listener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PayContent content= (PayContent) mListView.getAdapter().getItem(position);
            switch (content.getPayType()){
                case 0:
                    Toast.makeText(PayActivity.this,"支付宝",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(PayActivity.this,"银行卡支付",Toast.LENGTH_SHORT).show();
                    startActivityForResult(new Intent(PayActivity.this, UPPayActivity.class),UPPAYSTARTCODE);
                    break;
                case 2:
                    Toast.makeText(PayActivity.this,"微信支付",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==UPPAYSTARTCODE){
            if(resultCode==UPPayActivity.RESUTL_CODEPAY_SUCCESS){
                Toast.makeText(this,"it is a good deal",Toast.LENGTH_LONG).show();
                setResult(UPPayActivity.RESUTL_CODEPAY_SUCCESS);
                this.finish();
                //银联支付成功返回商品页
            }else if(resultCode==UPPayActivity.RESUTL_CODEPAY_FAIL){
                Toast.makeText(this,"it is a fail deal",Toast.LENGTH_SHORT).show();
            }else if(resultCode==UPPayActivity.RESUTL_CODEPAY_CANCLE){
                Toast.makeText(this,"it is a cancle deal",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
