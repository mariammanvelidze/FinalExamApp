package com.nek0mancer.finalexamproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user.*
import androidx.recyclerview.widget.LinearLayoutManager


class UserActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var items = mutableListOf<Books>()
    private lateinit var database: DatabaseReference
    private lateinit var bookReference: DatabaseReference
    private lateinit var usernameReference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)


        booksRecyclerView.layoutManager = LinearLayoutManager(this)

        booksRecyclerView.adapter = BooksAdapter(items, this)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        bookReference = database.child("books").child(auth.uid!!)
        usernameReference = database.child("usernames").child(auth.uid!!).child("username")
        init()

        id_logout_user.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        id_change_password_user.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        databaseListener()
    }

    private fun databaseListener(){
        val bookListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items.clear()
                for( item in dataSnapshot.children){
                    items.add(Books(item.key.toString(), item.child("author").value.toString(), item.child("title").value.toString(), item.child("year").value.toString().toInt()))
                }
                booksRecyclerView.adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }

        val usernameListener = object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val username = dataSnapshot.value
                id_username_user.text = username.toString()

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message

                // ...
            }
        }
        usernameReference.addListenerForSingleValueEvent(usernameListener)
        bookReference.addValueEventListener(bookListener)
    }
    private fun init(){
        addBookButton.setOnClickListener {
            val intent = Intent(this, AddBookActivity::class.java)
            startActivity(intent)
        }
    }


}

