package com.emmanuelomoding.librarymanagementapp.Fragmentsimport

import android.view.View
import androidx.fragment.app.Fragment
import com.emmanuelomoding.librarymanagementapp.Adaptersimport.DashBoardAdapter
import com.emmanuelomoding.librarymanagementapp.modelimport.model
import com.firebase.ui.database.FirebaseRecyclerOptions
import android.view.ViewGroup
import android.view.LayoutInflater
import com.emmanuelomoding.librarymanagementapp.R
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

class DashBoardFragment : Fragment() {
    var recyclerView: RecyclerView? = null
    var adapter: DashBoardAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)

        //Assigning the Recyclerview
        recyclerView = view.findViewById<View?>(R.id.DashBoardRecyclerView) as RecyclerView
        recyclerView!!.setLayoutManager(LinearLayoutManager(context))


        //Getting User id
        val userId = GoogleSignIn.getLastSignedInAccount(context)!!.getId()

        //Firebase Recycler Options to get the data form firebase database using model class and reference
        val options: FirebaseRecyclerOptions<model?> = FirebaseRecyclerOptions.Builder<model?>()
            .setQuery(
                FirebaseDatabase.getInstance().reference.child("myOrderedBooks").child(userId!!),
                model::class.java
            )
            .build()


        //Setting adapter to RecyclerView
        adapter = DashBoardAdapter(options)
        recyclerView!!.setAdapter(adapter)
        return view
    }

    override fun onStart() {
        super.onStart()
        //Starts listening for data from firebase when this fragment starts
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        //Stops listening for data from firebase
        adapter!!.stopListening()
    }
}