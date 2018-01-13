package com.memeswow.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.memeswow.PostDetailsActivity;
import com.memeswow.R;

import java.util.ArrayList;
import java.util.List;

public class MediaGridAdapter extends RecyclerView.Adapter<MyPhotosViewHolder> {
    Context ct;
    public ArrayList<PostSkeleton> postSkeletons;
    public MediaGridAdapter(Context context){
        ct=context;
    }

    @Override
    public MyPhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ct).inflate(R.layout.my_photo_collection,parent,false);
        return new MyPhotosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyPhotosViewHolder holder, final int position) {
        if(postSkeletons!=null)
                Glide.with(ct).load(postSkeletons.get(position).getImgURL()).into(holder.imageView);
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent i=new Intent(ct.getApplicationContext(), PostDetailsActivity.class);
                i.putExtra("imgURL",postSkeletons.get(position).getImgURL());
                ct.startActivity(i);
                return true;
            }
        });

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if(postSkeletons!=null)return postSkeletons.size();
        return 0;
    }
}
class MyPhotosViewHolder extends RecyclerView.ViewHolder{

ImageView imageView;
    public MyPhotosViewHolder(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.postImage);

    }
}
