package com.memeswow.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.memeswow.R;

import java.util.List;

/**
 * Created by Prateek on 1/10/2018.
 */

public class PostsAdapter extends RecyclerView.Adapter<MyPostViewHolder>    implements OnDoubleTapListener,GestureDetector.OnGestureListener {
 private Context ct;
 private int doubleTapPointer=-1;
    public ImageView like,comment,share,meme;
    TextView uploadedBy;
 private GestureDetectorCompat gestureDetector;
    public PostsAdapter(Context ct){
        super();
        gestureDetector= new GestureDetectorCompat(ct,this);
        this.ct=ct;

    }
    @Override
    public MyPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(ct).inflate(R.layout.feed_post_layout,parent,false);
        return new MyPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.v("liked",String.valueOf(doubleTapPointer));
        like.setImageTintList(ColorStateList.valueOf(Color.RED));
        doubleTapPointer=-1;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}

class MyPostViewHolder extends RecyclerView.ViewHolder{
public ImageView like,comment,share,meme;
TextView uploadedBy;

    public MyPostViewHolder(View itemView) {
        super(itemView);
        like=itemView.findViewById(R.id.like);
        comment= itemView.findViewById(R.id.comment);
        share= itemView.findViewById(R.id.share);
        meme= itemView.findViewById(R.id.postImage);
        uploadedBy=itemView.findViewById(R.id.uploadedby);
    }
}
