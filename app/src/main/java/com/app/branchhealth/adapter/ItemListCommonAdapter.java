package com.app.branchhealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.app.branchhealth.R;
import com.app.branchhealth.model.NewsModel;
import com.app.branchhealth.services.SingletonHTTPRequest;

import java.util.ArrayList;

/**
 * Created by eReFeRHa on 2/3/16.
 */
public class ItemListCommonAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<NewsModel> newsModels;

    public ItemListCommonAdapter(Context context, ArrayList<NewsModel> newsModels){
        inflater = LayoutInflater.from(context);
        this.newsModels = newsModels;
    }

    public void setNewsModels(ArrayList<NewsModel> newsModels) {
        this.newsModels = newsModels;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(newsModels != null)
            return newsModels.size();
        return 0;
    }

    @Override
    public NewsModel getItem(int i) {
        if(newsModels != null && i < newsModels.size()){
            return newsModels.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ItemViewHolder holder;

        if (convertView == null) {
            holder = new ItemViewHolder();
            convertView = inflater.inflate(R.layout.item_list_common, parent, false);
            holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);
            holder.itemImage = (NetworkImageView) convertView.findViewById(R.id.itemImage);
            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        NewsModel newsModel = getItem(i);
        if(newsModel != null){
            holder.itemTitle.setText(newsModel.getTitle());
            holder.itemImage.setDefaultImageResId(R.drawable.default_item);
            holder.itemImage.setImageUrl(newsModel.getImage(), SingletonHTTPRequest.getInstance(parent.getContext()).getImageLoader());
        }

        return convertView;
    }

    private class ItemViewHolder {
        TextView itemTitle;
        NetworkImageView itemImage;
    }
}
