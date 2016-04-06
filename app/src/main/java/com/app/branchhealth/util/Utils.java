package com.app.branchhealth.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by eReFeRHa on 25/3/16.
 */
public class Utils {

    public static void setListViewHeightBasedOnChildren(ListView listView){
        setListViewHeightBasedOnChildren(listView, false);
    }

    public static ViewGroup.LayoutParams getListViewParamsBasedOnChildren(ListView listView, boolean isSameHeight){
        ListAdapter listAdapter = listView.getAdapter();
        if(listAdapter == null){
            // pre-condition
            return listView.getLayoutParams();
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for(int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            if(isSameHeight){
                totalHeight += (listAdapter.getCount() * listItem.getMeasuredHeight());
                break;
            }
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        return params;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView, boolean isSameHeight){
        listView.setLayoutParams(getListViewParamsBasedOnChildren(listView, isSameHeight));
        listView.requestLayout();
    }
}
