package com.memeswow.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;

import com.memeswow.R;

import java.util.ArrayList;

public class MediaGridAdapter extends BaseAdapter {
    Context ct;
    int[] images;
    public MediaGridAdapter(Context context){
        ct=context;
    }
    @Override
    public int getCount() {
        return 16;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView img;
        if(view==null) {
         img = new ImageView(ct);
         img.setImageResource(R.drawable.dummy);
            img.setScaleX(1f);
            img.setScaleY(1f);
            img.setPadding(8,8,8,8);
     }else{
         img=(ImageView) view;

     }
     return img;
    }
}
