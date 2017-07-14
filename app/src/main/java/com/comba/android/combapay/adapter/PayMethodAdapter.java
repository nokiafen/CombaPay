package com.comba.android.combapay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.comba.android.combapay.R;
import com.comba.android.combapay.common.PayContent;

import java.util.List;

/**
 * Created by liangchunfeng on 2017/6/12.
 */

public class PayMethodAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private PayMehodViewHolder holder;
    private List<PayContent> payContents;

    public PayMethodAdapter(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void setPayMethodList(List<PayContent> payContents){
        this.payContents = payContents;
    }

    public PayMehodViewHolder getHolder(){
        return holder;
    }

    @Override
    public int getCount() {
        return payContents == null ? 0: payContents.size();
    }

    @Override
    public Object getItem(int position) {
        return payContents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            holder = new PayMehodViewHolder();
            convertView = mInflater.inflate(R.layout.activity_pay_item,parent,false);
            holder.payView = (ImageView) convertView.findViewById(R.id.payImage);
            holder.payTitle = (TextView) convertView.findViewById(R.id.payTitle);
            holder.bottomLine = (View) convertView.findViewById(R.id.view_bottom_line);
            holder.payContent = (TextView) convertView.findViewById(R.id.payContent);
            holder.arrowView = (ImageView) convertView.findViewById(R.id.payArrow);
            convertView.setTag(holder);
        }else{
            holder = (PayMehodViewHolder) convertView.getTag();
        }
        PayContent content = payContents.get(position);
        holder.payTitle.setText(content.getTitle());
        holder.payContent.setText(content.getContent());
        holder.payView.setImageResource(content.getDrawableSource());
        if(payContents.indexOf(content)==payContents.size()-1){
            holder.bottomLine.setVisibility(View.GONE);
        }else{
            holder.bottomLine.setVisibility(View.VISIBLE);
        }

//        holder.textView.setText(content.getName());
//        holder.payView.setImageDrawable();
        return convertView;
    }

    public class PayMehodViewHolder{
        ImageView payView;
        TextView payTitle;
        TextView payContent;
        ImageView arrowView;
        View bottomLine;
    }
}
