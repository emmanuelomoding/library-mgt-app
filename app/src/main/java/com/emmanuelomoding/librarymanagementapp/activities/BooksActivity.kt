package com.emmanuelomoding.librarymanagementapp.Activitiesimport

import android.view.View
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.emmanuelomoding.librarymanagementapp.R
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import com.emmanuelomoding.librarymanagementapp.adapters.AllBooksAdapter
import com.emmanuelomoding.librarymanagementapp.modelimport.model

class BooksActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var adapter: AllBooksAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        val category = intent.getStringExtra("category")

        //Assigning the Recyclerview
        recyclerView = findViewById<View?>(R.id.BooksRecyclerView) as RecyclerView
        recyclerView!!.setLayoutManager(LinearLayoutManager(applicationContext))


        //Firebase Recycler Options to get the data form firebase database using model class and reference
        val options: FirebaseRecyclerOptions<model?> = FirebaseRecyclerOptions.Builder<model?>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("AllBooks").child(category!!),
                model::class.java
            )
            .build()


        //Setting adapter to RecyclerView
        adapter = AllBooksAdapter(options)
        recyclerView!!.setAdapter(adapter)
    }

    public override fun onStart() {
        super.onStart()
        //Starts listening for data from firebase when this fragment starts
        adapter!!.startListening()
    }

    public override fun onStop() {
        super.onStop()
        //Stops listening for data from firebase
        adapter!!.stopListening()
    }
}


