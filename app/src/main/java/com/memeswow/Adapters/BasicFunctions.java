package com.memeswow.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class BasicFunctions {
    public static void createToast(String a,int x,Context context){
        if(x==1) Toast.makeText(context,a,Toast.LENGTH_SHORT).show();
        else Toast.makeText(context,a,Toast.LENGTH_LONG).show();
    }
public static Intent recieveSelectedImage(Intent data){
        if(data!=null){
    Uri myMeme=data.getData();
    Intent i=new Intent("com.android.camera.action.CROP");
    i.setDataAndType(myMeme,"image/*");
    i.putExtra("crop", "true");
            return i;}else
                return null;
}

public static Intent createIntentToPickImage(){
    Intent i= new Intent();
    i.setAction(Intent.ACTION_PICK);
    i.setType("image/*");
    return i;
}

}
