package com.memeswow;

import android.content.Context;
import android.widget.Toast;

public class BasicFunctions {
    public static void createToast(String a,int x,Context context){
        if(x==1) Toast.makeText(context,a,Toast.LENGTH_SHORT).show();
        else Toast.makeText(context,a,Toast.LENGTH_LONG).show();
    }
}
