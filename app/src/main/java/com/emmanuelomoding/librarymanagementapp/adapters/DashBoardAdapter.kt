package com.emmanuelomoding.librarymanagementapp.Adaptersimport

import android.view.View
import android.widget.*
import com.emmanuelomoding.librarymanagementapp.modelimport.model
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.squareup.picasso.Picasso
import android.view.ViewGroup
import android.view.LayoutInflater
import com.emmanuelomoding.librarymanagementapp.R
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class DashBoardAdapter(options: FirebaseRecyclerOptions<model?>) :
    FirebaseRecyclerAdapter<model?, DashBoardAdapter.Viewholder?>(options) {
    override fun onBindViewHolder(
        holder: DashBoardAdapter.Viewholder,
        position: Int,
        model: model
    ) {


        //Setting data to android materials
        holder.bookName!!.text = "Book Name: " + model.bookName
        holder.booksCount!!.text = "Available Books: " + model.booksCount
        holder.bookLocation!!.text = "Book Location: " + model.bookLocation
        Picasso.get().load(model.imageUrl).into(holder.imageView)

        //Implementing the OnClick Listener to delete the data from the database
        holder.collectBtn!!.setOnClickListener { view ->
            //Getting user id from the gmail sing in
            val userId = GoogleSignIn.getLastSignedInAccount(view.context)!!.getId()
            //Path to the database
            val reference =
                FirebaseDatabase.getInstance().reference.child("myOrderedBooks").child(userId!!)
            reference.orderByChild("bookName").equalTo(model.bookName)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (ds in snapshot.children) {

                            //getting the parent node of the data
                            val key = ds.key

                            //removing the data from the database
                            reference.child(key!!).removeValue().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    FirebaseDatabase.getInstance().reference.child("OrderedBooks")
                                        .child(key).removeValue()
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {

                                                //Showing the Toast message to the user
                                                Toast.makeText(
                                                    view.context,
                                                    "Book Order Canceled Successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashBoardAdapter.Viewholder {

        //the data objects are inflated into the xml file single_data_item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_book_layout, parent, false)
        return Viewholder(view)
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView?
        var bookName: TextView?
        var booksCount: TextView?
        var bookLocation: TextView?
        var collectBtn: Button?

        init {


            //Assigning Address of the android materials
            imageView = itemView.findViewById<View?>(R.id.BookImage) as ImageView
            bookName = itemView.findViewById<View?>(R.id.BookNameTxt) as TextView
            booksCount = itemView.findViewById<View?>(R.id.BooksCountTxt) as TextView
            bookLocation = itemView.findViewById<View?>(R.id.BooksLocationTxt) as TextView
            collectBtn = itemView.findViewById<View?>(R.id.CollectBookBtn) as Button
            collectBtn!!.setText("Cancel Book")
        }
    }
}