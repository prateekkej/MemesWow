package com.memeswow.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.memeswow.Adapters.PostsAdapter;
import com.memeswow.R;

/**
 * Created by Prateek on 1/10/2018.
 */

public class VideosFragment extends Fragment {
RecyclerView videoPostsListView;
LinearLayoutManager llm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.images_fragment,container,false);
        videoPostsListView=v.findViewById(R.id.feed_list_images_fragment);
        llm= new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        PostsAdapter postsAdapter= new PostsAdapter(this.getContext());
        videoPostsListView.setAdapter(postsAdapter);
        videoPostsListView.setLayoutManager(llm);
        return v;
    }
}
