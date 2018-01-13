package com.memeswow;

import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.memeswow.Adapters.CommentsAdapter;

import org.w3c.dom.Comment;

public class PostDetailsActivity extends AppCompatActivity {
    ImageView postImageBig;
    int height,width;
    RecyclerView commentsList;
    CommentsAdapter commentsAdapter;
    LinearLayoutManager llm;
    Button like,share;
AppBarLayout appBarLayout;
    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        like= findViewById(R.id.like);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        commentsList= findViewById(R.id.commentslist);
        commentsAdapter= new CommentsAdapter(this);
        commentsList.setAdapter(commentsAdapter);
        commentsList.setLayoutManager(llm);
        share= findViewById(R.id.share);
        appBarLayout= findViewById(R.id.appbar);
        appBarLayout.setMinimumHeight((int)(height*0.6));
        Uri imgURL=Uri.parse((String)getIntent().getExtras().get("imgURL"));
        Display view=getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics= new DisplayMetrics();
        view.getMetrics(displayMetrics);
        height=displayMetrics.heightPixels;width=displayMetrics.widthPixels;
        Toast.makeText(this,"Height:" + String.valueOf(height)+" Width:"+String.valueOf(width),Toast.LENGTH_LONG).show();
        postImageBig= findViewById(R.id.poat_image_big);
        postImageBig.setMinimumHeight((int)(height *0.4));
        postImageBig.setMaxHeight((int)(height * 0.6));
        if(imgURL!=null)
            if(!imgURL.toString().isEmpty())
                try {
                    Glide.with(getApplicationContext()).load(imgURL).apply(RequestOptions.centerInsideTransform()).into(postImageBig);

                }catch(Exception e){}

    }
}
