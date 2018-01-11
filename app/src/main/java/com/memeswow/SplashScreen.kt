package com.memeswow

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.memeswow.Adapters.BasicFunctions.createToast

class SplashScreen : AppCompatActivity() {
 private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        firebaseAuth= FirebaseAuth.getInstance()
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission_group.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            this.requestPermissions(kotlin.arrayOf(android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
        }



    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var flag= false
        if(requestCode==1&& grantResults.isNotEmpty()){
            grantResults
                    .filter { it !=PackageManager.PERMISSION_GRANTED }
                    .forEach {flag=true
                        createToast("Permission Not Granted. Please grant all the permissions. Restart the app for permission pop up again.",1,this)
                    }

        if(flag){finish()}else{if(firebaseAuth?.currentUser!=null){
            startActivity(Intent(this,Home().javaClass))

            finish()
        }else{
            startActivity(Intent(this,LoginActivity().javaClass))
            finish()
        }}
        }
    }
}
