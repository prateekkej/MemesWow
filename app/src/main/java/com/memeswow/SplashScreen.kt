package com.memeswow

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
 private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        firebaseAuth= FirebaseAuth.getInstance()
        if(firebaseAuth?.currentUser!=null){
            startActivity(Intent(this,Home().javaClass))

            finish()
        }else{
            startActivity(Intent(this,LoginActivity().javaClass))
            finish()
        }


    }
}
