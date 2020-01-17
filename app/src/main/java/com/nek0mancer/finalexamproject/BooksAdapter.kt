package com.nek0mancer.finalexamproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.books_list_item.view.*


class BooksAdapter(private val items: MutableList<Books>, private val context: Context) : RecyclerView.Adapter<ViewHolder>(){

    private var database = FirebaseDatabase.getInstance().reference
    private var auth = FirebaseAuth.getInstance()
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.author.text = items[position].author
        holder.title.text = items[position].title
        holder.year.text = items[position].year.toString()
        holder.removeButton.setOnClickListener {
            println(items[position].bookId)
            database.child("books").child(auth.uid!!).child(items[position].bookId).removeValue()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.books_list_item, parent, false))
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val author: TextView = view.authorTextView
    val title: TextView = view.titleTextView
    val year: TextView = view.yearTextView
    val removeButton: Button = view.removeButton
}

