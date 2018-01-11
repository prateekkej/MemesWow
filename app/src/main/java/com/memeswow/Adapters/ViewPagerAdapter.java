package com.memeswow.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.memeswow.Fragments.ImagesFragment;
import com.memeswow.Fragments.VideosFragment;

/**
 * Created by Prateek on 1/10/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    VideosFragment videosFragment;
    ImagesFragment imagesFragment;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        imagesFragment = new ImagesFragment();
        videosFragment = new VideosFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return "Image Feed";
            case 1:
                return "Videos Feed";
        }    return null;}

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return imagesFragment;
            case 1:
                return videosFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
