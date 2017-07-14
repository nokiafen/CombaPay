package com.comba.android.combapay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
//import com.alipay.sdk.app.PayTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by liangchunfeng on 2017/6/13.
 */

public class AliPayHelper {

    /**
     * 支付业务
     */
    private static final int SDK_PAY_FLAG = 1;
    /**
     * 授权业务
     */
    private static final int SDK_AUTH_FLAG = 2;

    private static Map<String,String> orderMap;

    private static Context mContext;


    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(mContext,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(mContext,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };



    private static class MyPayTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            HttpPost post = new HttpPost(params[0]);
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            List<String> keys = new ArrayList<String>(orderMap.keySet());
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = orderMap.get(key);
                param.add(new BasicNameValuePair(key,value));
            }
            try {
//                post.setEntity(new UrlEncodedFormEntity(param, HTTP.UTF_8));
                HttpResponse response = new DefaultHttpClient().execute(post);
                if(response.getStatusLine().getStatusCode() == 200){
                    HttpEntity entity = response.getEntity();
                    String orderInfo = EntityUtils.toString(entity);
                    return orderInfo;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String orderInfo) {
            super.onPostExecute(orderInfo);
            if(!TextUtils.isEmpty(orderInfo)){
                JSONObject res = JSONObject.parseObject(orderInfo);
                if(res.getInteger("code")==0){
                    final String info = res.getString("value");
                    Runnable payRunnable = new Runnable() {

                        @Override
                        public void run() {
////                            PayTask alipay = new PayTask((Activity) mContext);
////                            Map<String, String> result = alipay.payV2(info, true);
//                            Log.i("msp", result.toString());
//
//                            Message msg = new Message();
//                            msg.what = SDK_PAY_FLAG;
//                            msg.obj = result;
//                            mHandler.sendMessage(msg);
                        }
                    };

                    Thread payThread = new Thread(payRunnable);
                    payThread.start();
                }
            }
        }
    }


    /**
     * 支付宝支付
     * @param context   上下文对象
     * @param bundle    商品信息
     */
    public static void pay(final Context context, Bundle bundle){
        if(TextUtils.isEmpty(ParameterInfo.APPID) || (TextUtils.isEmpty(ParameterInfo.RSA2_PRIVATE) && TextUtils.isEmpty(ParameterInfo.RSA_PRIVATE))){
            new AlertDialog.Builder(context).setTitle("警告").setMessage("参数信息配置不完整")
                    .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((Activity)context).finish();
                        }
                    }).show();
            return;
        }
        mContext = context;

        orderMap = resolveBundle(bundle);
        if(orderMap.isEmpty()){
            Toast.makeText(context,"订单信息参数不全",Toast.LENGTH_SHORT).show();
            return;
        }
        new MyPayTask().execute("http://172.16.228.60:8081/platform-web/api/pay");

    }

    /**
     * 解析bundle数据：订单总额，body,subject
     * @param bundle
     */
    private static Map<String,String> resolveBundle(Bundle bundle) {
        Map<String,String> bundleMap = new HashMap<>();
        String totalAmount = bundle.getString("totalAmount");
        if(TextUtils.isEmpty(totalAmount)){
            return bundleMap;
        }
        String body = bundle.getString("body","");
        String subject = bundle.getString("subject");
        bundleMap.put("totalAmount",totalAmount);
        bundleMap.put("body",body);
        bundleMap.put("subject",subject);
        return bundleMap;
    }

}
