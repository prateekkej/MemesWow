package com.memeswow

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.RecyclerView
import android.view.animation.AnimationSet
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.memeswow.BasicFunctions.createToast

class Home : AppCompatActivity() {
    private lateinit var actionBar: ActionBar
    private lateinit  var logo:ImageView
    private lateinit var title: TextView
    private lateinit var cameraIcon: ImageView
    private lateinit var mediaGrid:GridView
    private var count=7
    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var mediaGridAdapter:MediaGridAdapter

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

        }
        private fun initializeViews(){
            actionBar = supportActionBar as ActionBar
            actionBar?.setDisplayShowCustomEnabled(true)
            actionBar?.setCustomView(R.layout.home_bar)
            cameraIcon = findViewById(R.id.actionCamera)
            title = findViewById(R.id.titlehomebar)
            logo=findViewById(R.id.actionlogo)
            mediaGrid= findViewById(R.id.mycollectiongrid)



}


}

