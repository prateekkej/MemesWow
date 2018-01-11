package com.memeswow

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.memeswow.Adapters.BasicFunctions
import com.memeswow.Adapters.BasicFunctions.*
import com.memeswow.Adapters.MediaGridAdapter
import com.memeswow.Adapters.PostSkeleton
import com.memeswow.Adapters.UserSkeleton

class Home : AppCompatActivity() {
    private var dataBaseReference= FirebaseFirestore.getInstance();
    private lateinit var actionBar: ActionBar
    private lateinit  var logo:ImageView
    private lateinit var title: TextView
    private lateinit var cameraIcon: ImageView
    private lateinit var mediaGrid:RecyclerView
    private lateinit var name:TextView
    private lateinit var about:TextView
    private lateinit var userImage:ImageView
    private lateinit var editProfileButton:Button
    private var count=7
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var liveFeedButton:Button
    private lateinit var mediaGridAdapter: MediaGridAdapter
    private lateinit var uploadMyMeme: Button
    private val IMAGE_PICK_REQUEST=1
    private val IMAGE_CROP_REQUEST=2
    private lateinit var myPostsList:ArrayList<PostSkeleton>
    private lateinit var myProfile:UserSkeleton
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)
            firebaseAuth= FirebaseAuth.getInstance()
            initializeViews()
            clicklisteners()
            myPostsList= ArrayList()
            mediaGridAdapter= MediaGridAdapter(this)

        }

        private fun clicklisteners(){
            cameraIcon?.setOnClickListener({ createToast("Coming Soon",1,baseContext)})
            title?.setOnClickListener({ count--
                if(count<1){
                    firebaseAuth.signOut()
                    startActivity(Intent(this,LoginActivity().javaClass))
                    finish()}
                else{
                    createToast("You are "+count+ "taps away from a magic.",1,baseContext)
                }})
            logo.setOnClickListener({this.onBackPressed()})
            editProfileButton.setOnClickListener {
                var i = Intent(this, EditProfileActivity().javaClass)
                try {
                    i.putExtra("myProfile", myProfile)
                    startActivity(i)
                } catch (e: Exception) {
                    createToast("Please Wait for profile to load.", 2, this)
                }
            }
            liveFeedButton.setOnClickListener({startActivity(Intent(this,LiveFeedActivity().javaClass))})
            uploadMyMeme.setOnClickListener({
                startActivityForResult(createIntentToPickImage(),IMAGE_PICK_REQUEST)
            })
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if(requestCode==IMAGE_PICK_REQUEST){
                var i=recieveSelectedImage(data)
                if(i==null){
                           startActivity(Intent(this,NewMemeActivity().javaClass));
                }else{
                    AlertDialog.Builder(this).setTitle("Do you want to crop the image?")
                        .setPositiveButton("Yes!",({dialogInterface: DialogInterface?, t: Int -> kotlin.run {startActivityForResult(i,IMAGE_CROP_REQUEST)  } }))
                        .setNegativeButton("No.Use as it is",{dialogInterface: DialogInterface?, i: Int -> kotlin.run{
                            val i= Intent(this,NewMemeActivity().javaClass)
                            i.putExtra("imageURI",data?.data.toString())
                            startActivity(i)

                        } }).show()
            }}
            if(requestCode==IMAGE_CROP_REQUEST){
                val myperfectMeme=data?.data
                if(myperfectMeme!=null){
                    val i= Intent(this,NewMemeActivity().javaClass)
                    i.putExtra("imageURI",myperfectMeme.toString())
                    startActivity(i)
                }
            }
        }

        override fun onResume() {
            super.onResume()
            var myDocument=dataBaseReference.collection("users").document(firebaseAuth?.uid.toString())
            myDocument.get().addOnSuccessListener { documentSnapshot: DocumentSnapshot? ->kotlin.run{
                if(documentSnapshot!!.exists()){
                    myProfile=documentSnapshot.toObject(UserSkeleton().javaClass)
                    name.text=myProfile.fname
                    about.text= myProfile.about
                    if(!myProfile.imgURL.isEmpty())
                    Glide.with(this).load(myProfile.imgURL)
                            .apply(RequestOptions().circleCrop())
                            .into(userImage)

                } } }.addOnFailureListener { e:Exception? -> kotlin.run { e?.printStackTrace() }}
            var posts=dataBaseReference.collection("posts").document(firebaseAuth.uid.toString()).collection("posts").limit(25).get();
            posts.addOnCompleteListener { task: Task<QuerySnapshot> -> kotlin.run {
                myPostsList.clear()
                for (snapshot in task.result.documents ) {
                    myPostsList.add(snapshot.toObject(PostSkeleton().javaClass))
                }
                mediaGridAdapter.postSkeletons=myPostsList
                mediaGrid.layoutManager=GridLayoutManager(this,3);
                mediaGrid.adapter=mediaGridAdapter

            }  }

        }
        private fun initializeViews(){
                actionBar = supportActionBar as ActionBar
                actionBar?.setDisplayShowCustomEnabled(true)
                actionBar?.setCustomView(R.layout.home_bar)
                cameraIcon = findViewById(R.id.actionCamera)
                title = findViewById(R.id.titlehomebar)
                logo=findViewById(R.id.actionlogo)
                mediaGrid= findViewById(R.id.mycollectiongrid)
                name= findViewById(R.id.name)
                about= findViewById(R.id.about)
                editProfileButton= findViewById(R.id.editprofile)
                liveFeedButton= findViewById(R.id.livefeed)
                uploadMyMeme=findViewById(R.id.uploadmymeme)
                userImage= findViewById(R.id.userImage_home)

        }


}

