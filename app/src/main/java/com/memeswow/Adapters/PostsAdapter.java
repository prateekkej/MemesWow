package com.memeswow.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ScaleGestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.memeswow.R;

/**
 * Created by Prateek on 1/10/2018.
 */

public class PostsAdapter extends BaseAdapter implements OnDoubleTapListener,GestureDetector.OnGestureListener {
 private Context ct;
 private int doubleTapPointer=-1;
 private ImageView imageView;
 private View tempViewForDoubleTap;
 private GestureDetectorCompat gestureDetector;
    public PostsAdapter(Context ct){
        super();
        gestureDetector= new GestureDetectorCompat(ct,this);
        this.ct=ct;

    }
    @Override
    public int getCount() {
        return 5;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final View v;
        if(view!=null){
            v=view;
        }else{            v = LayoutInflater.from(ct).inflate(R.layout.feed_post_layout,viewGroup,false);
        }
            imageView= v.findViewById(R.id.postImage);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    doubleTapPointer=i;
                    tempViewForDoubleTap=v;
                    gestureDetector.onTouchEvent(motionEvent);

                    return true;
                }
            });
            return v;
            }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.v("liked",String.valueOf(doubleTapPointer));
        ImageView like=tempViewForDoubleTap.findViewById(R.id.like);
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

