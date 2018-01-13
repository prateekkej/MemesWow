package com.memeswow;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.MimeTypeFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.memeswow.Adapters.PostSkeleton;
import com.memeswow.Adapters.UserSkeleton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;

import static android.provider.MediaStore.Images.Media.getBitmap;
import static android.provider.MediaStore.MediaColumns.MIME_TYPE;
import static com.memeswow.Adapters.BasicFunctions.createToast;

public class NewMemeActivity extends AppCompatActivity implements View.OnClickListener {
ImageView newmeme;
Uri imageURI;
TextView prob;
Button uploadMeme,changeMeme;
UserSkeleton myProfile;
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
            myProfile= (UserSkeleton) getIntent().getExtras().get("myProfile");
            if(imageURI==null ||imageURI.toString().isEmpty()){
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
                long s;
                try{s=getContentResolver().openFileDescriptor(imageURI,"r").getStatSize();}catch(Exception e){s=0;}
                final long size=s;
                boolean temp=true;
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                String ext=imageURI.toString().substring(imageURI.toString().lastIndexOf(".")+1);
                if(!ext.equals("gif")){
                    try{
                Bitmap img= MediaStore.Images.Media.getBitmap(getContentResolver(),imageURI);
                img.compress(Bitmap.CompressFormat.JPEG,60,baos);}catch (Exception e){}
                temp=false;
                }
                final boolean isGif= temp;
                final ProgressDialog pd= new ProgressDialog(this);
                pd.setTitle("Uploading new Meme");
                pd.setCancelable(false);
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setProgress(0);
                pd.show();
                PostSkeleton post= new PostSkeleton();
                post.setPosterID(firebaseAuth.getUid());
                post.setTimestamp(System.currentTimeMillis());
                post.setPosterName(myProfile.getFname());
                myPostsCollection.add(post).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(final DocumentReference documentReference) {
                           if(isGif){
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
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                pd.setProgress((int)((100.0 *taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount()));
                            }
                        });}else{myPostsReference.child(documentReference.getId()+".jpg").putBytes(baos.toByteArray())
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
                                   }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                       @Override
                                       public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                           pd.setProgress((int)((100.0 *taskSnapshot.getBytesTransferred())/size));
                                       }
                                   });
                           }
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
