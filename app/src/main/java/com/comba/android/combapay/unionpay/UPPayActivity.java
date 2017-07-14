package com.comba.android.combapay.unionpay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.comba.android.combapay.R;
import com.comba.android.combapay.beans.OrderCancleResult;
import com.comba.android.combapay.beans.PayResult;
import com.comba.android.combapay.beans.SalesReturnResult;
import com.comba.android.combapay.beans.TradeNumResult;
import com.comba.android.combapay.common.RetrofitWrapper;
import com.comba.android.combapay.retrofitService.CancleUpOrderService;
import com.comba.android.combapay.retrofitService.PayResultService;
import com.comba.android.combapay.retrofitService.SalesReturnService;
import com.comba.android.combapay.retrofitService.UpTradeService;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chenhailin on 2017/6/5.
 */

public class UPPayActivity extends AppCompatActivity {
    public static final int PLUGIN_VALID = 0;
    public static final int PLUGIN_NOT_INSTALLED = -1;
    public static final int PLUGIN_NEED_UPGRADE = 2;
    public static final String LOG_TAG="UPPayActivity";
    private final String mMode = "01";
    //模拟获取订单url
    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";

    public  static final  int RESUTL_CODEPAY_SUCCESS=0x01;
    public  static final  int RESUTL_CODEPAY_FAIL=0x02;
    public  static final  int RESUTL_CODEPAY_CANCLE=0x03;
    private UpTradeService upTradeService;
    private Call<TradeNumResult> callUPTrade;
    private  String currentOrderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window=getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        wl.alpha=0.0f;
        window.setAttributes(wl);
        setContentView(R.layout.up_layout);
         upTradeService = RetrofitWrapper.getInstance().create(UpTradeService.class);
         callUPTrade = upTradeService.getTradeNum("123456","777290058148497");
            toPay();
    }



    //点击事件发起支付
    public void toPay(){
        // “00” – 银联正式环境
        // “01” – 银联测试环境，该环境中不发生真实交易
//        String serverMode = mMode;
          getTradeNum();

//        getPayResult("564232");
//        canCelOrder("2343");
//        salesReturn("66565");
    }

    //唤起银联插件
    private void evokeUpPlugIn(UPPayActivity upPayActivity, String tn, String serverMode) {
        int ret = UPPayAssistEx.startPay(upPayActivity, null, null, tn, serverMode);
        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件
            Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

            builder.setNegativeButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UPPayAssistEx.installUPPayPlugin(UPPayActivity.this);
                            dialog.dismiss();
                        }
                    });

            builder.setPositiveButton("取消",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }
        Log.e(LOG_TAG, "" + ret);
    }



    /**
     * 请求生成订单号
     */
    private void getTradeNum() {
        callUPTrade.enqueue(new Callback<TradeNumResult>() {
            @Override
            public void onResponse(Call<TradeNumResult> call, Response<TradeNumResult> response) {
                TradeNumResult beans= response.body();
                if(beans==null){
                    Toast.makeText(UPPayActivity.this,R.string.net_failed,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (beans.getCode()==0) {
              currentOrderId= beans.getValue().getOrderId();
                    evokeUpPlugIn(UPPayActivity.this,beans.getValue().getTnCode(),mMode);
                }else{
                    Toast.makeText(UPPayActivity.this,beans.getMessage(),Toast.LENGTH_SHORT).show();
                    setResultUnion(RESUTL_CODEPAY_FAIL);
                }
            }

            @Override
            public void onFailure(Call<TradeNumResult> call, Throwable throwable) {
                Toast.makeText(UPPayActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
               setResultUnion(RESUTL_CODEPAY_FAIL);
                UPPayActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {

//            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
//            // result_data结构见c）result_data参数说明
//            if (data.hasExtra("result_data")) {
//                String result = data.getExtras().getString("result_data");
//                try {
//                    JSONObject resultJson = new JSONObject(result);
//                    String sign = resultJson.getString("sign");
//                    String dataOrg = resultJson.getString("data");
//                    // 此处的verify建议送去商户后台做验签
//                    // 如要放在手机端验，则代码必须支持更新证书
//                    boolean ret = verify(dataOrg, sign, mMode);
//                    if (ret) {
//                        // 验签成功，显示支付结果
//                        msg = "支付成功！";
//                    } else {
//                        // 验签失败
//                        msg = "支付失败！";
//                    }
//                } catch (JSONException e) {
//                }
//            }
//            // 结果result_data为成功时，去商户后台查询一下再展示成功
//            msg = "支付成功！";
//            this.setResultUnion(RESUTL_CODEPAY_SUCCESS);
            getPayResult(currentOrderId);

        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
            this.setResultUnion(RESUTL_CODEPAY_FAIL);
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
            this.setResultUnion(RESUTL_CODEPAY_CANCLE);
        }
//        this.setResultUnion(1);

    }

    private boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;
    }


    public  void setResultUnion(int code){
            setResult(code);
            this.finish();
    }


    /**
     *
     * @param orderId
     * @return
     */
    public void getPayResult(String orderId){
        RetrofitWrapper.getInstance().create(PayResultService.class).getPayResult(orderId).enqueue(new Callback<PayResult>() {
            @Override
            public void onResponse(Call<PayResult> call, Response<PayResult> response) {
              PayResult result=  response.body();
                if(result==null){
                    Toast.makeText(UPPayActivity.this,R.string.net_failed,Toast.LENGTH_SHORT).show();
                    UPPayActivity.this.setResultUnion(RESUTL_CODEPAY_FAIL);
                    return;
                }
                if (result.getCode()==0) {
                    UPPayActivity.this.setResultUnion(RESUTL_CODEPAY_SUCCESS);
                }else{
                    Toast.makeText(UPPayActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                    setResultUnion(RESUTL_CODEPAY_FAIL);
                }
            }

            @Override
            public void onFailure(Call<PayResult> call, Throwable t) {
                Toast.makeText(UPPayActivity.this,R.string.net_failed,Toast.LENGTH_SHORT).show();
                setResultUnion(RESUTL_CODEPAY_FAIL);
                UPPayActivity.this.finish();
            }
        });

    }


    /**
     *
     * @param orderId
     * @return
     */
    public void  canCelOrder(String orderId){
        RetrofitWrapper.getInstance().create(CancleUpOrderService.class).cancleOrder(orderId).enqueue(new Callback<OrderCancleResult>() {
            @Override
            public void onResponse(Call<OrderCancleResult> call, Response<OrderCancleResult> response) {
                OrderCancleResult result=  response.body();
                if(result==null){
                    Toast.makeText(UPPayActivity.this,R.string.net_failed,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (result.getCode()==0) {

                }else{
                    Toast.makeText(UPPayActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                    setResultUnion(RESUTL_CODEPAY_FAIL);
                }
            }

            @Override
            public void onFailure(Call<OrderCancleResult> call, Throwable t) {
                Toast.makeText(UPPayActivity.this, R.string.net_failed,Toast.LENGTH_SHORT).show();
                setResultUnion(RESUTL_CODEPAY_FAIL);
                UPPayActivity.this.finish();
            }
        });
    }


    /**
     * @param orderId
     * @return
     */
    public void salesReturn (String orderId){
        RetrofitWrapper.getInstance().create(SalesReturnService.class).saleRetrun(orderId).enqueue(new Callback<SalesReturnResult>() {
            @Override
            public void onResponse(Call<SalesReturnResult> call, Response<SalesReturnResult> response) {
                SalesReturnResult result=  response.body();
                if(result==null){
                    Toast.makeText(UPPayActivity.this,R.string.net_failed,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (result.getCode()==0) {

                }else{
                    Toast.makeText(UPPayActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                    setResultUnion(RESUTL_CODEPAY_FAIL);
                }
            }

            @Override
            public void onFailure(Call<SalesReturnResult> call, Throwable t) {
                Toast.makeText(UPPayActivity.this,R.string.net_failed,Toast.LENGTH_SHORT).show();
                setResultUnion(RESUTL_CODEPAY_FAIL);
                UPPayActivity.this.finish();
            }
        });

    }



}
