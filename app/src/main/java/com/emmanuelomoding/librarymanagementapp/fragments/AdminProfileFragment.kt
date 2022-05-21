package com.emmanuelomoding.librarymanagementapp.Fragmentsimport

import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.emmanuelomoding.librarymanagementapp.Activitiesimport.SplashScreenActivity
import de.hdodenhof.circleimageview.CircleImageView
import com.squareup.picasso.Picasso
import android.content.Intent
import android.view.ViewGroup
import android.view.LayoutInflater
import com.emmanuelomoding.librarymanagementapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class AdminProfileFragment : Fragment() {
    var imageView: CircleImageView? = null
    var userName: TextView? = null
    var signOutBtn: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_profile, container, false)
        imageView = view.findViewById<View?>(R.id.ProfilePic) as CircleImageView
        userName = view.findViewById<View?>(R.id.UserNameTxt) as TextView
        signOutBtn = view.findViewById<View?>(R.id.SignOutBtn) as Button

        //Getting user detials from GoogleSignin
        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        if (acct != null) {
            userName!!.setText(acct.displayName)
            Picasso.get().load(acct.photoUrl).into(imageView)
        }


        //implementing onClickListener to make the user signOut
        signOutBtn!!.setOnClickListener(View.OnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

            //GoogleSignInClient to access the current user
            val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            googleSignInClient.signOut().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //User Signout
                    FirebaseAuth.getInstance().signOut()

                    //Redirecting to starting Activity
                    val intent = Intent(context, SplashScreenActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                }
            }
        })
        return view
    }
}