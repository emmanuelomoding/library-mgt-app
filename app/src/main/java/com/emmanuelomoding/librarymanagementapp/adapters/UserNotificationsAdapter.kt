package com.emmanuelomoding.librarymanagementapp.Adaptersimport

import android.view.View
import com.emmanuelomoding.librarymanagementapp.modelimport.model
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.FirebaseRecyclerAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import com.emmanuelomoding.librarymanagementapp.R
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

private class UserNotificationsAdapter(options: FirebaseRecyclerOptions<model?>) :
    FirebaseRecyclerAdapter<model?, UserNotificationsAdapter.Viewholder?>(options) {
    override fun onBindViewHolder(
        holder: UserNotificationsAdapter.Viewholder,
        position: Int,
        model: model
    ) {
        holder.notificationTxt!!.text = model.notification
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserNotificationsAdapter.Viewholder {

        //the data objects are inflated into the xml file single_data_item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_notifications_layout, parent, false)
        return Viewholder(view)
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    internal inner class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var notificationTxt: TextView?

        init {
            notificationTxt = itemView.findViewById<View?>(R.id.NotificationTxt) as TextView
        }
    }
}