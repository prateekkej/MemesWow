package com.memeswow

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.memeswow.Adapters.BasicFunctions.createToast
import com.memeswow.Adapters.UserSkeleton

class LoginActivity : AppCompatActivity() {
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var logmein:Button
    lateinit var emailstr:String
    lateinit var passwordstr:String
    lateinit  var googleSignInOptions: GoogleSignInOptions
    lateinit var client: GoogleSignInClient
    lateinit var firebaseAuth:FirebaseAuth
    lateinit var googleButton:SignInButton
    lateinit var databaseReference:FirebaseFirestore
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            var account= GoogleSignIn.getSignedInAccountFromIntent(data)
                var authCredential=GoogleAuthProvider.getCredential(account?.result?.idToken,null)
               var pd= ProgressDialog(this)
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                pd.setTitle("Signing you in...")
            pd.setCancelable(false)
                pd.show()
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener({ task: Task<AuthResult> -> kotlin.run{
                    databaseReference.collection("users").document(firebaseAuth?.uid.toString())
                            .get().addOnCompleteListener { myTask: Task<DocumentSnapshot> ->kotlin.run {
                        if(myTask.result.exists()){
                            signInTaskHandler(task,pd)
                        }else{
                            var myProfile= UserSkeleton()
                            myProfile.id=firebaseAuth.uid
                            if(firebaseAuth.currentUser?.email!=null){
                                myProfile.email= firebaseAuth.currentUser?.email
                            }
                            if(firebaseAuth.currentUser?.phoneNumber!=null){
                                myProfile.phone=firebaseAuth.currentUser?.phoneNumber.toString()
                            }
                            myProfile.fname= firebaseAuth.currentUser?.displayName
                            databaseReference.collection("users").document(firebaseAuth?.uid.toString()).set(myProfile).addOnSuccessListener {
                                signInTaskHandler(task,pd)
                            }.addOnFailureListener{ createToast("Error Occured",1,this)}

                        }


                    }  }


                     }

        })
    }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        databaseReference= FirebaseFirestore.getInstance()
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        logmein= findViewById(R.id.login)
        googleButton=findViewById(R.id.googlebutton)
        googleButton.setSize(SignInButton.SIZE_WIDE)
        googleButton.setColorScheme(SignInButton.COLOR_DARK)
        firebaseAuth= FirebaseAuth.getInstance()
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.client_id)).build()
        client = GoogleSignIn.getClient(this,googleSignInOptions)
        googleButton.setOnClickListener({
            startActivityForResult(client.signInIntent,1)
        })
        logmein.setOnClickListener({
            emailstr=email.editableText.toString().trim()
            passwordstr=password.editableText.toString().trim()
            if(!emailstr.isEmpty()&&!passwordstr.isEmpty()){
            var c=ProgressDialog(this)
            c.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            c.setTitle("Signing You in ")
            c.setCancelable(false)
            c.show()
           firebaseAuth.signInWithEmailAndPassword(emailstr,passwordstr).addOnCompleteListener({ task: Task<AuthResult> -> kotlin.run {
              signInTaskHandler(task,c)
           } })
        }})
    }
    private fun signInTaskHandler(task:Task<AuthResult>,pd:ProgressDialog){
        if(task.isSuccessful){
            pd.dismiss()
            createToast("Login Successful",1,baseContext)
            startActivity(Intent(this,Home().javaClass))
            finish()
        }else{
            pd.dismiss()
            createToast("Login failed",1,baseContext)
        }
    }
    }
