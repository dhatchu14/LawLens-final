package com.law.lawlens09

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class EmergencyActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var contactsListView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText)
        contactsListView = findViewById(R.id.contactsListView)

        // Initialize ArrayAdapter with all contacts
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, EmergencyContacts.contacts.map { "${it.name}: ${it.phoneNumber}" })
        contactsListView.adapter = adapter

        // Set text change listener for searchEditText
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filterContacts(s.toString())
            }
        })

        // Request permissions if not granted
        if (!isPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CALL_PERMISSION
            )
        }

        // Set item click listener for contactsListView
        contactsListView.setOnItemClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)
            contact?.let { makePhoneCall(it) }
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun filterContacts(query: String) {
        val filteredContacts = ArrayList<String>()

        for (contact in EmergencyContacts.contacts) {
            if (contact.name.toLowerCase().startsWith(query.toLowerCase())) {
                filteredContacts.add("${contact.name}: ${contact.phoneNumber}")
            }
        }

        adapter.clear()
        adapter.addAll(filteredContacts)
        adapter.notifyDataSetChanged()
    }

    private fun makePhoneCall(contactInfo: String) {
        val phoneNumber = contactInfo.split(":")[1].trim()
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted
        } else {
            // Permission denied
            // Handle permission denied case, e.g., show a message or disable calling functionality
        }
    }

    companion object {
        private const val REQUEST_CALL_PERMISSION = 1
    }

    object EmergencyContacts {
        val contacts = arrayOf(
            EmergencyContact("Police", "100"),
            EmergencyContact("Fire Department", "101"),
            EmergencyContact("Ambulance", "102"),
            EmergencyContact("Emergency Hotline", "112"),
            EmergencyContact("Women Helpline", "1091"),
            EmergencyContact("Child Helpline", "1098"),
            EmergencyContact("Senior Citizen Helpline", "1291"),
            EmergencyContact("Blood Bank Helpline", "1910"),
            EmergencyContact("Traffic Police", "103"),
            EmergencyContact("Railway Police", "1512"),
            EmergencyContact("Disaster Management", "1078"),
            EmergencyContact("Electricity Complaints", "1912"),
            EmergencyContact("Gas Leakage", "1906"),
            EmergencyContact("COVID-19 Helpline", "1075"),
            EmergencyContact("National Emergency Response System", "112"),
            EmergencyContact("Animal Ambulance", "1962"),
            EmergencyContact("Poison Control", "1066"),
            EmergencyContact("Domestic Violence Hotline", "181"),
            EmergencyContact("Mental Health Hotline", "104"),
            EmergencyContact("Suicide Prevention Hotline", "022-27546669")
        )
    }

    data class EmergencyContact(val name: String, val phoneNumber: String)
}
