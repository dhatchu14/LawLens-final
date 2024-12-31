package com.law.lawlens09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.law.lawlens09.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.android.gms.tasks.OnCompleteListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var databaseReference: DatabaseReference // Add this line

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance("https://lawlens02-default-rtdb.asia-southeast1.firebasedatabase.app").reference // Initialize database reference

        FirebaseMessaging.getInstance().subscribeToTopic("web_app")
            .addOnCompleteListener(OnCompleteListener<Void> { task ->
                var msg = "Done"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
                // You can handle the success/failure here
            })

        bottomNavigationView = binding.bottomNavigation
        replaceFragment(HomeFragment())

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.bookmark -> {
                    val bookmarkFragment = BookmarkFragment(databaseReference) // Pass database reference to BookmarkFragment
                    replaceFragment(bookmarkFragment)
                }
                R.id.library -> replaceFragment(com.law.lawlens09.LibraryFragment()) // Correct import statement
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment) // Ensure the ID matches your layout
        fragmentTransaction.commit()
    }
}
