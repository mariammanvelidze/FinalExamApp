package com.nek0mancer.finalexamproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth : FirebaseAuth
// ...

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = FirebaseDatabase.getInstance().reference
        id_button_register.setOnClickListener {
            val email = id_email_register.text.toString()
            val password = id_password_register.text.toString()
            val username = id_username_register.text.toString()
            auth = FirebaseAuth.getInstance()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {

                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                            database.child("usernames").child(auth.uid!!).child("username").setValue(username)

                        } else {

                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()

                        }
                    }

            }


        }
        id_already_register.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
