package com.comba.android.combapay.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liangchunfeng on 2017/6/12.
 */

public class PayContent implements Parcelable {

    private String name;
    private String title;
    private String content;
    private  int drawableSource;

    public int getPayType() {
        return payType;
    }

    /**
     *
     * @param payType 0 支付宝 1银行卡 2微信
     */
    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDrawableSource() {
        return drawableSource;
    }

    public void setDrawableSource(int drawableSource) {
        this.drawableSource = drawableSource;
    }

    /**
     * 0 支付宝 1银行卡 2微信
     */
    private int payType=0;//


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PayContent() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.drawableSource);
        dest.writeInt(this.payType);
    }

    protected PayContent(Parcel in) {
        this.name = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.drawableSource = in.readInt();
        this.payType = in.readInt();
    }

    public static final Creator<PayContent> CREATOR = new Creator<PayContent>() {
        @Override
        public PayContent createFromParcel(Parcel source) {
            return new PayContent(source);
        }

        @Override
        public PayContent[] newArray(int size) {
            return new PayContent[size];
        }
    };
}
