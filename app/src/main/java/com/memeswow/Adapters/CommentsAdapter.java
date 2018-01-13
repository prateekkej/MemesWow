package com.memeswow.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.memeswow.R;

/**
 * Created by Prateek on 1/13/2018.
 */

public class CommentsAdapter extends Adapter<CommentViewHolder> {
    Context ct;

    public CommentsAdapter(Context context) {
        super();
        ct=context;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(ct).inflate(R.layout.comment_layout,parent,false);
        return new CommentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
class CommentViewHolder extends RecyclerView.ViewHolder{


    public CommentViewHolder(View itemView) {
        super(itemView);
    }
}