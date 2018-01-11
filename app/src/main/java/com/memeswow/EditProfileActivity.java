package com.memeswow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.memeswow.Adapters.UserSkeleton;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static com.memeswow.Adapters.BasicFunctions.createIntentToPickImage;
import static com.memeswow.Adapters.BasicFunctions.createToast;
import static com.memeswow.Adapters.BasicFunctions.recieveSelectedImage;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView userImage;
    EditText name,phone,about;
    Button deleteUserButton,changePasswordButton;
    UserSkeleton myProfile;
    FirebaseFirestore databaseReference= FirebaseFirestore.getInstance();
    private StorageReference profileImageReference= FirebaseStorage.getInstance().getReference("/users_profile_images");
    private final int IMAGE_PICK_REQUEST=1;
    private final int IMAGE_CROP_REQUEST=2;
    private ByteArrayOutputStream byteArrayOutputStream;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.savebutton){
            final ProgressDialog pd= new ProgressDialog(this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setTitle("Updating your profile");
            pd.setCancelable(false);
            pd.show();
            HashMap<String,Object> update= new HashMap<>();
            update.put("fname",name.getEditableText().toString());
            update.put("phone",phone.getEditableText().toString());
            update.put("about",about.getEditableText().toString());
            databaseReference.collection("users").document(myProfile.getId()).update(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        pd.dismiss();
                        onBackPressed();
                        createToast("Profile Updated",1,EditProfileActivity.this);        }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                }
            });


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        myProfile=(UserSkeleton)getIntent().getExtras().get("myProfile");
        initializeViewsAndClickListeners();

        }

    private void initializeViewsAndClickListeners() {
        name=findViewById(R.id.editName_edit_profile);
        phone=findViewById(R.id.editPhone_edit_profile);
        about=findViewById(R.id.editAbout_edit_profile);
        userImage= findViewById(R.id.userImage_edit_profile);
        changePasswordButton= findViewById(R.id.changebutton_edit_profile);
        deleteUserButton=findViewById(R.id.delete_edit_profile);
        changePasswordButton.setOnClickListener(this);
        deleteUserButton.setOnClickListener(this);
        userImage.setOnClickListener(this);
        if(myProfile!=null){
            name.setText(myProfile.getFname());
            about.setText(myProfile.getAbout());
            phone.setText(myProfile.getPhone());
            if(!myProfile.getImgURL().isEmpty())
                Glide.with(this).load(myProfile.getImgURL())
                        .apply(new RequestOptions().circleCrop())
                        .into(userImage);        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMAGE_PICK_REQUEST){
            Intent i=recieveSelectedImage(data);
            if(i!=null){
                startActivityForResult(i,IMAGE_CROP_REQUEST);}            }
            if(requestCode==IMAGE_CROP_REQUEST){
            try {
                final Bitmap myImage = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                byteArrayOutputStream = new ByteArrayOutputStream();
                myImage.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                final ProgressDialog pd= new ProgressDialog(this);
                pd.setTitle("Uploading Image");
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setMax(100);
                pd.show();
                final long sizeofImage= byteArrayOutputStream.size();
                profileImageReference.child(myProfile.getId()+".jpg").putBytes(byteArrayOutputStream.toByteArray())
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                databaseReference.collection("users").document(myProfile.getId()).update("imgURL",taskSnapshot.getDownloadUrl().toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        pd.dismiss();
                                        Toast.makeText(EditProfileActivity.this, "Image Successfully Updated.", Toast.LENGTH_SHORT).show();
                                        Glide.with(EditProfileActivity.this).load(myImage).
                                                apply(RequestOptions.circleCropTransform()).into(userImage);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        profileImageReference.child(myProfile.getId()).delete();
                                        pd.dismiss();
                                        Toast.makeText(EditProfileActivity.this, "Error Occured. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Error Occured. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        long transferred=taskSnapshot.getBytesTransferred();
                        long prog=transferred/sizeofImage *100;
                        pd.setProgress((int)prog);
                    }
                });
            }catch(Exception e){}
            }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.changebutton_edit_profile){
            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
        }
        if(view.getId()==R.id.delete_edit_profile){
            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
        }
        if(view.getId()==R.id.userImage_edit_profile){
            PopupMenu popupMenu= new PopupMenu(this,view);
            popupMenu.inflate(R.menu.edit_image);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if(menuItem.getItemId()==R.id.changeImage){
                        startActivityForResult(createIntentToPickImage(),IMAGE_PICK_REQUEST);
                    }
                    return true;
                }
            });
        }
    }
}
