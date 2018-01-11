package com.memeswow.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.memeswow.Adapters.PostsAdapter;
import com.memeswow.R;

/**
 * Created by Prateek on 1/10/2018.
 */

public class ImagesFragment extends Fragment {
    ListView imagesPostsListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.images_fragment,container,false);
        imagesPostsListView=v.findViewById(R.id.feed_list_images_fragment);
        PostsAdapter postsAdapter= new PostsAdapter(this.getContext());
        imagesPostsListView.setAdapter(postsAdapter);
        return v;
    }
}
