package com.nek0mancer.finalexamproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_book.*
import org.w3c.dom.Text

class AddBookActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth

    private lateinit var author : String
    private lateinit var title : String
    private lateinit var year : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        init()
    }

    private fun init(){
        addButton.setOnClickListener{
            author = authorEditText.text.toString()
            title = titleEditText.text.toString()
            year = yearEditText.text.toString()
            if(year.isEmpty() || title.isEmpty() || author.isEmpty()){
                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Succesfully added", Toast.LENGTH_SHORT).show()
                addNewBook(author, title, year.toInt())
                finish()
            }
        }

    }

    private fun addNewBook(author: String, title: String, year: Int) {
        val key = database.child("books").child(auth.uid!!).push().key
        if (key == null) {
            println("Couldn't get push key for posts")
            return
        }

        val post = Books(key, author, title, year)
        val postValues = post.toMap()
        val userid = auth.uid
        val childUpdates = HashMap<String, Any>()
        childUpdates["/books/$userid/$key"] = postValues

        database.updateChildren(childUpdates)
    }
}
