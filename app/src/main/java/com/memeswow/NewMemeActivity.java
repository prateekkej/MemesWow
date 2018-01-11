package com.memeswow;

import android.app.ProgressDialog;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.memeswow.Adapters.PostSkeleton;

import java.util.HashMap;

import static com.memeswow.Adapters.BasicFunctions.createToast;

public class NewMemeActivity extends AppCompatActivity implements View.OnClickListener {
ImageView newmeme;
Uri imageURI;
TextView prob;
Button uploadMeme,changeMeme;
HashMap<String,PostSkeleton> postsHashMap;
FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
CollectionReference  myPostsCollection= FirebaseFirestore.getInstance()
        .collection("posts").document(firebaseAuth.getUid()).collection("posts");
StorageReference myPostsReference= FirebaseStorage.getInstance().getReference("posts/"+firebaseAuth.getUid());
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
            if(imageURI==null){
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
                newmeme.setScaleType(ImageView.ScaleType.FIT_CENTER);
                uploadMeme.setOnClickListener(this);
                changeMeme.setOnClickListener(this);

                 }}
        catch(Exception e){}

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.changeit_new_meme:
                createToast("karta hu ",1,this);
                break;
            case R.id.uploadIt_new_meme:
                final ProgressDialog pd= new ProgressDialog(this);
                pd.setTitle("Uploading new Meme");
                pd.setCancelable(false);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.show();
                PostSkeleton post= new PostSkeleton();
                myPostsCollection.add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {
                        myPostsReference.child(documentReference.getId()).putFile(imageURI)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                HashMap<String,Object> te= new HashMap();
                                te.put("id",documentReference.getId());
                                te.put("imgURL",taskSnapshot.getDownloadUrl().toString());
                                documentReference.update(te).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        pd.dismiss();
                                        createToast("Posted",1,NewMemeActivity.this);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        myPostsCollection.document(documentReference.getId()).delete();
                                        pd.dismiss();
                                        createToast("Error Occured. Please try again",1,NewMemeActivity.this);
                                    }
                                });                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                        documentReference.update("id",documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
pd.dismiss();
createToast(e.toString(),1,NewMemeActivity.this);
                    }
                });



        }

            }
}
