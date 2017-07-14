package com.comba.someonefund.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.comba.someonefund.R;
import com.comba.someonefund.beans.FindResultBean;
import com.comba.someonefund.beans.SearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by chenhailin on 2017/7/12.
 */

public class SearchResultReAdapter extends RecyclerView.Adapter<SearchResultReAdapter.SearchResultViewHolder> {

private int reSourceId;
    private ArrayList<FindResultBean.ValueEntity> dataResource;
    public SearchResultReAdapter(int resourceId ,ArrayList<FindResultBean.ValueEntity> dataResource){
        this.reSourceId=resourceId;
        this.dataResource=dataResource;

    }



    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(reSourceId, null);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder holder, int position) {
        final FindResultBean.ValueEntity data=dataResource.get(position);
        holder.textViews.get(0).setText(data.getName());
        holder.textViews.get(1).setText(data.getPhone());
        holder.textViews.get(2).setText(data.getIndustryName());
        holder.textViews.get(3).setText(data.getWorkTypeName());
        holder.textViews.get(4).setText(data.getAddress());

        if(data.getAddressShow()==1){
            holder.textViews.get(4).setVisibility(View.VISIBLE);
        }else{
            holder.textViews.get(4).setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneClickListener!=null){
                    phoneClickListener.call(data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataResource.size();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        @BindViews({R.id.tv_Name,R.id.tv_phone,R.id.tv_Profession,R.id.tv_workType,R.id.tv_local})
        List<TextView> textViews;

        @BindView(R.id.rel_Item)
        RelativeLayout itemView;


        public SearchResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setData(ArrayList<FindResultBean.ValueEntity> dataSources){
        this.dataResource=dataSources;
        this.notifyDataSetChanged();
    }

    public void setPhoneClickListener (PhoneClickListenr phoneClickListener){
        this.phoneClickListener=phoneClickListener;
    }

    public PhoneClickListenr phoneClickListener;

}
