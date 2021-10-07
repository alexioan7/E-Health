package com.alexandros.e_health.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alexandros.e_health.R


class BottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.alexandros.e_health.R.layout.activity_bottom_navigation)

//        nav_view.setOnItemSelectedListener(){
//            when(it.itemId){
//                R.id.navigation_personalinfo -> setCurrentFragment(PersonalinfoFragment())
//            }
//        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_bottom_navigation, fragment )
            commit()
        }
}