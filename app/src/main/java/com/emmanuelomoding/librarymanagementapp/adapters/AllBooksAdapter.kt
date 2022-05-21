package com.emmanuelomoding.librarymanagementapp.adapters

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
import android.view.View
import android.widget.*
import com.emmanuelomoding.librarymanagementapp.modelimport.model
import java.util.HashMap

class AllBooksAdapter(options: FirebaseRecyclerOptions<model?>) :
    FirebaseRecyclerAdapter<model?, AllBooksAdapter.Viewholder?>(options) {
    override fun onBindViewHolder(holder: Viewholder, position: Int, model: model) {


        //Setting data to android materials
        holder.bookName!!.setText("Book Name: " + model().bookName)
        holder.booksCount!!.setText("Available Books: " + model().booksCount)
        holder.bookLocation!!.setText("Book Location: " + model().bookLocation)
        Picasso.get().load(model.imageUrl).into(holder.imageView)


        //Implementing OnClickListener
        holder.collectBtn!!.setOnClickListener(View.OnClickListener { view ->
            val bookLocation = model.bookLocation
            val bookName = model.bookName
            val booksCount = model.booksCount
            val imageUrl = model.imageUrl
            val userId = GoogleSignIn.getLastSignedInAccount(view.context)!!.getId()
            FirebaseDatabase.getInstance().reference.child("AllUsers").child(userId!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        //Getting user data using Model Class
                        val model1 = snapshot.getValue(
                            model::class.java
                        )
                        val name = model1!!.getName()
                        val city = model1!!.getCity()
                        val phoneNumber = model1!!.getPhoneNumber()
                        val address = model1!!.getAddress()
                        val pincode = model1!!.getPincode()
                        val userDetails = HashMap<String?, Any?>()
                        userDetails["name"] = name
                        userDetails["city"] = city
                        userDetails["phoneNumber"] = phoneNumber
                        userDetails["address"] = address
                        userDetails["pincode"] = pincode
                        userDetails["bookLocation"] = bookLocation
                        userDetails["bookName"] = bookName
                        userDetails["booksCount"] = booksCount
                        userDetails["imageUrl"] = imageUrl
                        userDetails["userId"] = userId
                        val push = FirebaseDatabase.getInstance().reference.child("OrderedBooks")
                            .push().key
                        FirebaseDatabase.getInstance().reference.child("OrderedBooks").child(push!!)
                            .updateChildren(userDetails)
                            .addOnSuccessListener {
                                FirebaseDatabase.getInstance().reference.child("myOrderedBooks")
                                    .child(userId).child(push!!)
                                    .updateChildren(userDetails)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            view.context,
                                            "Book Ordered Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }


                        //Toast message
                        Toast.makeText(view.context, "Book Ordered", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {

        //the data objects are inflated into the xml file single_data_item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_book_layout, parent, false)
        return Viewholder(view)
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    internal inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        }
    }
}