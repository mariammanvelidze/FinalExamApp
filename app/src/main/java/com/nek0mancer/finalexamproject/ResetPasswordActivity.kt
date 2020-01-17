package com.nek0mancer.finalexamproject

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log.d
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.activity_user.*
import android.R.attr.password
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.AuthCredential
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



private lateinit var auth: FirebaseAuth
// ...
// Initialize Firebase Auth


class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)


        auth = FirebaseAuth.getInstance()
        val user: FirebaseUser? = auth.currentUser
        id_change_password_button.setOnClickListener {
            val password: String = id_current_password.text.toString()
            val newPassword1: String = id_new_password1.text.toString()
            val newPassword2: String = id_new_password2.text.toString()

            if (TextUtils.isEmpty(password)) {

                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()

            } else {
                val authCredential = EmailAuthProvider.getCredential(user?.email!!, password)
                user?.reauthenticate(authCredential)?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword1)?.addOnCompleteListener {
                                if (newPassword1 != newPassword2) {
                                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
                                } else {
                                    if (it.isSuccessful) {

                                        Toast.makeText(this, "Password changed successfully", Toast.LENGTH_LONG).show()
                                        finish()

                                    } else {

                                        Toast.makeText(this, "There's a problem", Toast.LENGTH_LONG)
                                            .show()

                                    }
                                }
                        }
                    } else{
                        Toast.makeText(this, "Current password is wrong", Toast.LENGTH_LONG)
                            .show()
                    }

                }
            }

            id_back_to_profile.setOnClickListener {
                finish()
            }


        }

    }
}