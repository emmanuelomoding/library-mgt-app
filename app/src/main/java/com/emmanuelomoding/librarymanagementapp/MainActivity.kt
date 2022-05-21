package com.emmanuelomoding.librarymanagementappimport

import android.view.View
import androidx.fragment.app.Fragment

import com.firebase.ui.database.FirebaseRecyclerOptions
import android.content.Intent
import com.emmanuelomoding.librarymanagementapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import android.widget.Toast
import com.google.firebase.database.DatabaseError
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import android.widget.FrameLayout
import com.emmanuelomoding.librarymanagementapp.Activitiesimport.SplashScreenActivity
import com.emmanuelomoding.librarymanagementapp.Activitiesimport.adminActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.emmanuelomoding.librarymanagementapp.Fragmentsimport.DashBoardFragment
import com.emmanuelomoding.librarymanagementapp.Fragmentsimport.HomeFragment
import com.emmanuelomoding.librarymanagementapp.Fragmentsimport.NotificationsFragment
import com.emmanuelomoding.librarymanagementapp.Fragmentsimport.UserProfileFragment

class MainActivity : AppCompatActivity() {
    var frameLayout: FrameLayout? = null
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Assigning framelayout resource file to show appropriate fragment using address
        frameLayout = findViewById<View?>(R.id.UserFragmentContainer) as FrameLayout
        //Assigining Bottomnavigaiton Menu
        bottomNavigationView =
            findViewById<View?>(R.id.UserBottomNavigationView) as BottomNavigationView
        val menuNav = bottomNavigationView!!.getMenu()
        //Setting the default fragment as HomeFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.UserFragmentContainer, HomeFragment()).commit()
        //Calling the bottoNavigationMethod when we click on any menu item
        bottomNavigationView!!.setOnNavigationItemSelectedListener(bottomNavigationMethod)
    }

    private val bottomNavigationMethod: BottomNavigationView.OnNavigationItemSelectedListener? =
        BottomNavigationView.OnNavigationItemSelectedListener { item -> //Assigining Fragment as Null
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.HomeMenu -> fragment = HomeFragment()
                R.id.DashBoardMenu -> fragment = DashBoardFragment()
                R.id.NotificationsMenu -> fragment = NotificationsFragment()
                R.id.ProfileMenu -> fragment = UserProfileFragment()
            }
            //Sets the selected Fragment into the Framelayout
            supportFragmentManager.beginTransaction().replace(R.id.UserFragmentContainer, fragment!!)
                .commit()
            true
        }

    override fun onStart() {
        super.onStart()
        //checking user already logged or not
        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser == null) {
            val intent = Intent(applicationContext, SplashScreenActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            //Checks for user Role and starts the appropriate activity
            val id = GoogleSignIn.getLastSignedInAccount(applicationContext)!!.getId()
            val reference =
                FirebaseDatabase.getInstance().reference.child("AllUsers").child(id!!).child("role")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value.toString() != null) {
                        val data = snapshot.value.toString()
                        Toast.makeText(applicationContext, data, Toast.LENGTH_SHORT).show()
                        if (data == "Admin") {
                            val intent = Intent(applicationContext, adminActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            //do nothing
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }
}