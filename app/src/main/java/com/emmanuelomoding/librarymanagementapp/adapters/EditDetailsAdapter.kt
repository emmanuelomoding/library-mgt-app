package com.emmanuelomoding.librarymanagementapp.Adaptersimport

import android.view.View
import android.widget.*
import com.emmanuelomoding.librarymanagementapp.modelimport.model
import com.firebase.ui.database.FirebaseRecyclerOptions
import java.util.HashMap
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.squareup.picasso.Picasso
import android.view.ViewGroup
import android.view.LayoutInflater
import com.emmanuelomoding.librarymanagementapp.R
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class EditDetailsAdapter(options: FirebaseRecyclerOptions<model?>) :
    FirebaseRecyclerAdapter<model?, EditDetailsAdapter.Viewholder?>(options) {
    override fun onBindViewHolder(
        holder: EditDetailsAdapter.Viewholder,
        position: Int,
        model: model
    ) {


        //Setting data to android materials
        holder.bookName!!.setText(model.bookName)
        holder.booksCount!!.setText(model.booksCount)
        holder.bookLocation!!.setText(model.bookLocation)
        Picasso.get().load(model.imageUrl).into(holder.imageView)
        val pushKey = model.pushKey.toString()
        val category = model.category.toString()

        //Implementing the OnClick Listener to delete the data from the database
        holder.updateDetailsBtn!!.setOnClickListener { view ->
            val bookName = holder.bookName!!.text.toString()
            val booksCount = holder.booksCount!!.text.toString()
            val bookLocation = holder.bookLocation!!.text.toString()

            //Hash map to store values
            val bookDetails = HashMap<String?, Any?>()

            //adding the data to hashmap
            bookDetails["bookName"] = bookName
            bookDetails["booksCount"] = booksCount
            bookDetails["bookLocation"] = bookLocation
            bookDetails["category"] = category
            bookDetails["pushKey"] = pushKey
            FirebaseDatabase.getInstance().reference.child("AllBooks")
                .child(category)
                .child(pushKey)
                .updateChildren(bookDetails)
                .addOnSuccessListener {
                    Toast.makeText(
                        view.context,
                        "Details Updated Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditDetailsAdapter.Viewholder {

        //the data objects are inflated into the xml file single_data_item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_edit_book_details_layout, parent, false)
        return Viewholder(view)
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView?
        var bookName: EditText?
        var booksCount: EditText?
        var bookLocation: EditText?
        var updateDetailsBtn: Button?

        init {


            //Assigning Address of the android materials
            imageView = itemView.findViewById<View?>(R.id.BookImage) as ImageView
            bookName = itemView.findViewById<View?>(R.id.BookNameTxt) as EditText
            booksCount = itemView.findViewById<View?>(R.id.BooksCountTxt) as EditText
            bookLocation = itemView.findViewById<View?>(R.id.BooksLocationTxt) as EditText
            updateDetailsBtn = itemView.findViewById<View?>(R.id.UpdateDataBtn) as Button
            updateDetailsBtn!!.setText("Update Details")
        }
    }
}