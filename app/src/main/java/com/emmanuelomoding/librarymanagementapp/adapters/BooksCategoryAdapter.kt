package com.emmanuelomoding.librarymanagementapp.Adaptersimport

import android.view.View
import android.widget.*
import com.emmanuelomoding.librarymanagementapp.Activitiesimport.EditBookDetailsActivity
import com.emmanuelomoding.librarymanagementapp.modelimport.model
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.squareup.picasso.Picasso
import android.content.Intent
import android.view.ViewGroup
import android.view.LayoutInflater
import com.emmanuelomoding.librarymanagementapp.R
import androidx.recyclerview.widget.RecyclerView


class BooksCategoryAdapter(options: FirebaseRecyclerOptions<model?>) :
    FirebaseRecyclerAdapter<model?, BooksCategoryAdapter.Viewholder?>(options) {
    override fun onBindViewHolder(
        holder: BooksCategoryAdapter.Viewholder,
        position: Int,
        model: model
    ) {
        val imageUrl = model.categoryImage
        val category = model.category.toString()
        Picasso.get().load(imageUrl).into(holder.imageView)
        holder.imageView!!.setOnClickListener { view ->
            val intent = Intent(view.context, EditBookDetailsActivity::class.java)
            intent.putExtra("category", category)
            view.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BooksCategoryAdapter.Viewholder {

        //the data objects are inflated into the xml file single_data_item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_category_file_admin, parent, false)
        return Viewholder(view)
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView?

        init {
            imageView = itemView.findViewById<View?>(R.id.CategoryImageAdmin) as ImageView
        }
    }
}