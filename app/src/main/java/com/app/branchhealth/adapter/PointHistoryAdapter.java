package com.app.branchhealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.branchhealth.R;
import com.app.branchhealth.model.HistoryPointModel;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by eReFeRHa on 26/8/15.
 */
public class PointHistoryAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private ArrayList<HistoryPointModel> mapHistoryPointModel;
    private ArrayList<Integer> headerPosition;
    private LayoutInflater inflater;

    public PointHistoryAdapter(Context context, ArrayList<HistoryPointModel> mapHistoryPointModel, ArrayList<Integer> headerPosition) {
        inflater = LayoutInflater.from(context);
        this.mapHistoryPointModel = mapHistoryPointModel;
        this.headerPosition = headerPosition;
    }

    public void setMapHistoryPointModel(ArrayList<HistoryPointModel> mapHistoryPointModel) {
        this.mapHistoryPointModel = mapHistoryPointModel;
    }

    public void setHeaderPosition(ArrayList<Integer> headerPosition) {
        this.headerPosition = headerPosition;
    }

    public ArrayList<Integer> getHeaderPosition() {
        return headerPosition;
    }

    public ArrayList<HistoryPointModel> getMapHistoryPointModel() {
        return mapHistoryPointModel;
    }

    @Override
    public int getCount() {
        return mapHistoryPointModel.size();
    }

    @Override
    public HistoryPointModel getItem(int position) {
        return mapHistoryPointModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder;

        if (convertView == null) {
            holder = new ItemViewHolder();
            convertView = inflater.inflate(R.layout.item_list, null, false);
            holder.txtMessage = (TextView) convertView.findViewById(R.id.txtList);
            convertView.setTag(holder);
        } else {
            holder = (ItemViewHolder) convertView.getTag();
        }

        HistoryPointModel oTrx = getItem(position);
        holder.txtMessage.setText(oTrx.getMessage());
        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.item_header, null, false);
            holder.text = (TextView) convertView.findViewById(R.id.txtHeaderTitle);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        HistoryPointModel oTrx = getItem(position);
        holder.text.setText(oTrx.getDate());
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        int indexHeader = 0;
        for(Integer i : headerPosition){
            if(position >= i){
                indexHeader = i;
            }
        }
        return indexHeader;
    }

    private class ItemViewHolder {
        TextView txtMessage;
    }

    private class HeaderViewHolder {
        TextView text;
    }
}
