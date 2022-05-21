package com.emmanuelomoding.librarymanagementapp.Activitiesimport

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.emmanuelomoding.librarymanagementapp.Fragmentsimport.AddBooksFragment
import com.emmanuelomoding.librarymanagementapp.Fragmentsimport.AdminDashBoardFragment
import com.emmanuelomoding.librarymanagementapp.Fragmentsimport.AdminProfileFragment
import com.emmanuelomoding.librarymanagementapp.Fragmentsimport.EditBookDetailsFragment
import com.emmanuelomoding.librarymanagementapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class adminActivity : AppCompatActivity() {
    var frameLayout: FrameLayout? = null
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        //Assigning framelayout resource file to show appropriate fragment using address
        frameLayout = findViewById<View?>(R.id.AdminFragmentContainer) as FrameLayout

        //Assigining Bottomnavigaiton Menu
        bottomNavigationView =
            findViewById<View?>(R.id.AdminBottomNavigationView) as BottomNavigationView
        val menuNav = bottomNavigationView!!.getMenu()


        //Setting the default fragment as HomeFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.AdminFragmentContainer, AddBooksFragment()).commit()


        //Calling the bottoNavigationMethod when we click on any menu item
        bottomNavigationView!!.setOnNavigationItemSelectedListener(bottomNavigationMethod)
    }

    private val bottomNavigationMethod: BottomNavigationView.OnNavigationItemSelectedListener? =
        BottomNavigationView.OnNavigationItemSelectedListener { item -> //Assigining Fragment as Null
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.AddBookMenu -> fragment = AddBooksFragment()
                R.id.EditDetailsMenu -> fragment = EditBookDetailsFragment()
                R.id.DashBoardMenu -> fragment = AdminDashBoardFragment()
                R.id.AdminProfile -> fragment = AdminProfileFragment()
            }
            //Sets the selected Fragment into the Framelayout
            supportFragmentManager.beginTransaction().replace(R.id.AdminFragmentContainer, fragment!!)
                .commit()
            true
        }
}