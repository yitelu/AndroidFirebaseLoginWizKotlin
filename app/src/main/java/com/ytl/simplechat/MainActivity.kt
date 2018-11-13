package com.ytl.simplechat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference




class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        if(mAuth.currentUser != null){
            logIn()

        }
    }

    fun goClicked(view: View){
        //check if we can log in the user
        mAuth.signInWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    // Sign up the user
                    mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString()).addOnCompleteListener(this) { task ->

                        if(task.isSuccessful){
                            //FirebaseDatabase.getInstance().getReference().setValue("users").(task.result?.user?.uid.toString()).child("email").setValue(emailEditText?.text.toString())


                            val database = FirebaseDatabase.getInstance()
                            val myRef = database.getReference("users")


                            myRef.child(task.result?.user?.uid.toString())
                            myRef.child("email")
                            myRef.setValue(emailEditText?.text.toString())

                            logIn()
                        } else {
                            Toast.makeText(this, "Login Failed. Try Again", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                // ...
            }


        //Sign up the user
    }

    fun logIn(){
        //move to next activity

        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)


    }
}
