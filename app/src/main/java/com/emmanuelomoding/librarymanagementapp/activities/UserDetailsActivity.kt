package com.emmanuelomoding.librarymanagementapp.Activitiesimport

import android.view.View
import android.widget.*
import com.emmanuelomoding.librarymanagementappimport.MainActivity
import com.google.android.gms.tasks.Task
import kotlin.collections.HashMap

import com.firebase.ui.database.FirebaseRecyclerOptions
import android.content.Intent
import com.emmanuelomoding.librarymanagementapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase
import com.google.android.gms.tasks.OnCompleteListener
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class UserDetailsActivity : AppCompatActivity() {
    var userPhoneNumber: EditText? = null
    var userAddress: EditText? = null
    var userCity: EditText? = null
    var userPinCode: EditText? = null
    var addDataBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        //Assigning the address of the android materials
        userPhoneNumber = findViewById<View?>(R.id.PhoneNumberEditText) as EditText
        userAddress = findViewById<View?>(R.id.AddressEditText) as EditText
        userCity = findViewById<View?>(R.id.CityEditText) as EditText
        userPinCode = findViewById<View?>(R.id.PinCodeExitText) as EditText
        addDataBtn = findViewById<View?>(R.id.UpdateProfileBtn) as Button

        //implementing onclicklistener
        addDataBtn!!.setOnClickListener(View.OnClickListener {
            //getting test from the edit text
            val phoneNumber = userPhoneNumber!!.getText().toString().trim { it <= ' ' }
            val address = userAddress!!.getText().toString().trim { it <= ' ' }
            val city = userCity!!.getText().toString().trim { it <= ' ' }
            val pinCode = userPinCode!!.getText().toString().trim { it <= ' ' }


            //checking for the empty fields
            if (phoneNumber.isEmpty() || address.isEmpty() || city.isEmpty() || pinCode.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "Please,Fill all the Details",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                //method to add data to firebase
                addUserDetails(phoneNumber, address, city, pinCode)
            }
        })
    }

    private fun addUserDetails(
        phoneNumber: String?,
        address: String?,
        city: String?,
        pinCode: String?
    ) {

        //Getting the user id form the google signin
        val id = GoogleSignIn.getLastSignedInAccount(applicationContext)!!.getId()

        //Creating Hashmap to store data
        val userDetails = HashMap<String?, Any?>()
        userDetails["phoneNumber"] = phoneNumber
        userDetails["address"] = address
        userDetails["city"] = city
        userDetails["pincode"] = pinCode

        //Adding the user details to firebase
        FirebaseDatabase.getInstance().reference.child("AllUsers").child(id!!)
            .updateChildren(userDetails)
            .addOnCompleteListener(object : OnCompleteListener<Any?> {
                override fun onComplete(task: Task<Any?>) {
                    if (task.isSuccessful) {


                        //showing the toast message to user
                        Toast.makeText(
                            applicationContext,
                            "Details added Successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        //Changing current intent after adding the details to firebase
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            })
    }
}