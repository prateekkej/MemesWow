package com.memeswow;

import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewMemeActivity extends AppCompatActivity {
ImageView newmeme;
Uri imageURI;
TextView prob;
Button uploadMeme,changeMeme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meme);
        uploadMeme= findViewById(R.id.uploadIt_new_meme);
        changeMeme= findViewById(R.id.changeit_new_meme);
        newmeme=findViewById(R.id.newmeme_new_meme);
        prob= findViewById(R.id.prob);
        try{
            imageURI = Uri.parse(getIntent().getExtras().getString("imageURI"));
            if(imageURI.equals("null")){
                prob.animate().alpha(1).setDuration(2000).setStartDelay(100).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        prob.animate().alpha(0).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                prob.setText("Just Go back.");
                                prob.animate().alpha(1).setDuration(2000).start();

                            }
                        }).start();
                    }
                });
        }else{newmeme.setImageURI(imageURI);
        prob.setVisibility(View.INVISIBLE);
                newmeme.setScaleType(ImageView.ScaleType.CENTER);

                 }}
        catch(Exception e){}



    }
}
