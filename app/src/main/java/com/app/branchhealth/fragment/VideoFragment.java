package com.app.branchhealth.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.branchhealth.R;
import com.app.branchhealth.model.ItemNewsModel;

/**
 * Created by eReFeRHa on 5/8/15.
 */
public class VideoFragment extends Fragment implements View.OnClickListener {

    // DATA
    private ItemNewsModel itemNewsModel;

    public void setItemNewsModel(ItemNewsModel itemNewsModel) {
        this.itemNewsModel = itemNewsModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_video_play, container, false);
        rootView.findViewById(R.id.btnPlay).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnPlay:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(itemNewsModel.getUrl()), "video/mp4");
                startActivity(intent);
                break;
        }
    }
}
