package com.emmanuelomoding.librarymanagementapp.Fragmentsimport

import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.emmanuelomoding.librarymanagementapp.Activitiesimport.SplashScreenActivity
import com.emmanuelomoding.librarymanagementapp.modelimport.model
import com.google.android.gms.tasks.Task
import de.hdodenhof.circleimageview.CircleImageView
import java.util.HashMap
import com.squareup.picasso.Picasso
import android.content.Intent
import android.view.ViewGroup
import android.view.LayoutInflater
import com.emmanuelomoding.librarymanagementapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.android.gms.tasks.OnCompleteListener
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class UserProfileFragment : Fragment() {
    var circleImageView: CircleImageView? = null
    var userNameTxt: TextView? = null
    var phoneNumberEditTxt: EditText? = null
    var addressEditTxt: EditText? = null
    var cityNameEditTxt: EditText? = null
    var pinCodeEdittxt: EditText? = null
    var signOutBtn: Button? = null
    var updateDetailsBtn: Button? = null
    var databaseReference: DatabaseReference? = null
    var userId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)

        //Assigning all the addresses of the android materials
        circleImageView = view.findViewById<View?>(R.id.ProfileImageView) as CircleImageView
        userNameTxt = view.findViewById<View?>(R.id.UserNameTxt) as TextView
        cityNameEditTxt = view.findViewById<View?>(R.id.CityEditText) as EditText
        phoneNumberEditTxt = view.findViewById<View?>(R.id.PhoneNumberEditText) as EditText
        pinCodeEdittxt = view.findViewById<View?>(R.id.PinCodeExitText) as EditText
        addressEditTxt = view.findViewById<View?>(R.id.AddressEditText) as EditText
        updateDetailsBtn = view.findViewById<View?>(R.id.UpdateProfileBtn) as Button
        signOutBtn = view.findViewById<View?>(R.id.SignOutBtn) as Button
        databaseReference = FirebaseDatabase.getInstance().reference.child("AllUsers")
        //Getting user detials from GoogleSignin
        val acct = GoogleSignIn.getLastSignedInAccount(activity)
        if (acct != null) {
            userId = acct.id.toString()
            databaseReference!!.child(userId!!).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //Gettign the data form the firebase using model class
                    val model = snapshot.getValue(
                        model::class.java
                    )
                    //setting the data to android materials
                    Picasso.get().load(model!!.getProfilepic()).into(circleImageView)
                    userNameTxt!!.setText(model!!.getName())
                    cityNameEditTxt!!.setText(model!!.getCity())
                    phoneNumberEditTxt!!.setText(model!!.getPhoneNumber())
                    addressEditTxt!!.setText(model!!.getAddress())
                    pinCodeEdittxt!!.setText(model!!.getPincode())
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }


        //Implementing OnClick Listener to update data to firebase
        updateDetailsBtn!!.setOnClickListener(View.OnClickListener {
            //Getting the current data from the edit Text to update it to firebase
            val phoneNumber = phoneNumberEditTxt!!.getText().toString()
            val cityName = cityNameEditTxt!!.getText().toString()
            val pinCode = pinCodeEdittxt!!.getText().toString()
            val address = addressEditTxt!!.getText().toString()

            //Checking for empty fields
            if (phoneNumber.isEmpty() || cityName.isEmpty() || pinCode.isEmpty() || address.isEmpty()) {
                Toast.makeText(context, "Please,Fill Details", Toast.LENGTH_SHORT).show()
            } else {
                //calling method to update data to firebase
                updateDetails(phoneNumber, cityName, pinCode, address, userId)
            }
        })

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

    private fun updateDetails(
        phoneNumber: String?,
        cityName: String?,
        pinCode: String?,
        address: String?,
        userId: String?
    ) {


        //Storing the user details in hashmap
        val userDetails = HashMap<String?, Any?>()

        //adding the data to hashmap
        userDetails["phoneNumber"] = phoneNumber
        userDetails["cityName"] = cityName
        userDetails["pinCode"] = pinCode
        userDetails["address"] = address

        //adding the data to firebase
        databaseReference!!.child(userId!!).updateChildren(userDetails!!)
            .addOnCompleteListener(object : OnCompleteListener<Any?> {
                override fun onComplete(task: Task<Any?>) {
                    if (task.isSuccessful) {
                        //Showing the Toast message to user
                        Toast.makeText(context, "Data Updated Successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        //Showing the toast message to user
                        Toast.makeText(context, "Please,Try again Later", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }
}