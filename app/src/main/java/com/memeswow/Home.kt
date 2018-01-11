package com.memeswow

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.ActionBar
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.memeswow.Adapters.BasicFunctions
import com.memeswow.Adapters.BasicFunctions.*
import com.memeswow.Adapters.MediaGridAdapter
import com.memeswow.Adapters.UserSkeleton

class Home : AppCompatActivity() {
    private var dataBaseReference= FirebaseFirestore.getInstance();
    private lateinit var actionBar: ActionBar
    private lateinit  var logo:ImageView
    private lateinit var title: TextView
    private lateinit var cameraIcon: ImageView
    private lateinit var mediaGrid:GridView
    private lateinit var name:TextView
    private lateinit var about:TextView
    private lateinit var editProfileButton:Button
    private var count=7
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var liveFeedButton:Button
    private lateinit var mediaGridAdapter: MediaGridAdapter
    private lateinit var uploadMyMeme: Button
    private val IMAGE_PICK_REQUEST=1
    private val IMAGE_CROP_REQUEST=2
    private lateinit var myProfile:UserSkeleton
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home)
            firebaseAuth= FirebaseAuth.getInstance()
            initializeViews()
            clicklisteners()
            mediaGridAdapter= MediaGridAdapter(this)
            mediaGrid.adapter=mediaGridAdapter
            val width=window.decorView.width
            mediaGrid.columnWidth=width/3
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
        startActivityForResult(i,IMAGE_CROP_REQUEST)}}
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

            } } }.addOnFailureListener { e:Exception? -> kotlin.run { e?.printStackTrace() }}

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

    }


}

