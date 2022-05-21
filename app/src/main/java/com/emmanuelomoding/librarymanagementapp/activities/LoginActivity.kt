package com.emmanuelomoding.librarymanagementapp.Activitiesimport

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.emmanuelomoding.librarymanagementapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    var mSignInClient: GoogleSignInClient? = null
    var firebaseAuth: FirebaseAuth? = null
    var progressBar: ProgressDialog? = null
    var signInButton: Button? = null
    var spinner: Spinner? = null
    var roles: Array<String?>? = arrayOf("Admin", "User")
    var userRole: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        //Progress bar
        progressBar = ProgressDialog(this)
        progressBar!!.setTitle("Please Wait...")
        progressBar!!.setMessage("We are setting Everything for you...")
        progressBar!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        spinner = findViewById<View?>(R.id.Spinner) as Spinner
        //Creating the ArrayAdapter instance having the country list
        val adapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //Setting the ArrayAdapter data on the Spinner
        spinner!!.setAdapter(adapter)
        spinner!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                i: Int,
                l: Long
            ) {
                userRole = roles!![i]
                Toast.makeText(applicationContext, roles!![i], Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        signInButton = findViewById<View?>(R.id.GoogleSignInBtn) as Button


        //Google Signin Options to get gmail and performa gmail login
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("123453474054-j42f04uq7rnuqqolvvf9hcpo5tmlsr4v.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mSignInClient = GoogleSignIn.getClient(applicationContext, googleSignInOptions)


        //Implementing OnClickListener to perform Login action
        signInButton!!.setOnClickListener(View.OnClickListener { //Showing all Gmails
            val intent = mSignInClient!!.getSignInIntent()
            startActivityForResult(intent, 100)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val googleSignInAccountTask = GoogleSignIn
                .getSignedInAccountFromIntent(data)
            if (googleSignInAccountTask.isSuccessful) {
                progressBar!!.show()
                try {
                    val googleSignInAccount = googleSignInAccountTask.getResult(
                        ApiException::class.java
                    )
                    if (googleSignInAccount != null) {
                        val authCredential = GoogleAuthProvider
                            .getCredential(googleSignInAccount.idToken, null)
                        firebaseAuth!!.signInWithCredential(authCredential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {

                                    //Hashmap to store the userdetails and setting it to fireabse
                                    val user_details = HashMap<String?, Any?>()

                                    //Accessing the user details from gmail
                                    val id = googleSignInAccount.id.toString()
                                    val name = googleSignInAccount.displayName.toString()
                                    val mail = googleSignInAccount.email.toString()
                                    val pic = googleSignInAccount.photoUrl.toString()

                                    //storing data in hashmap
                                    user_details["id"] = id
                                    user_details["name"] = name
                                    user_details["mail"] = mail
                                    user_details["profilepic"] = pic
                                    user_details["role"] = userRole

                                    //Adding data to firebase
                                    FirebaseDatabase.getInstance().reference.child("AllUsers")
                                        .child(id)
                                        .updateChildren(user_details)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                progressBar!!.cancel()
                                                if (userRole == "Admin") {
                                                    //navigating to the main activity after user successfully registers
                                                    val intent = Intent(
                                                        applicationContext,
                                                        adminActivity::class.java
                                                    )
                                                    //Clears older activities and tasks
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                    startActivity(intent)
                                                } else {
                                                    //navigating to the main activity after user successfully registers
                                                    val intent = Intent(
                                                        applicationContext,
                                                        UserDetailsActivity::class.java
                                                    )
                                                    //Clears older activities and tasks
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                    startActivity(intent)
                                                }
                                            }
                                        }
                                }
                            }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }
    }
}