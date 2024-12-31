package com.law.lawlens09

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class CPCActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SectionAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpc)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        searchEditText = findViewById(R.id.searchEditText)

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().reference.child("cpc")

        // Configure FirebaseRecyclerOptions
        val options = FirebaseRecyclerOptions.Builder<DataModel.Section>()
            .setQuery(databaseReference, DataModel.Section::class.java)
            .build()

        // Initialize the adapter
        adapter = SectionAdapter(options)
        recyclerView.adapter = adapter

        // Set up search functionality
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })
    }

    private fun filter(text: String) {
        // Log the search text
        Log.d("Search", "Searching for: $text")

        val query = databaseReference.orderByChild("description").startAt(text).endAt(text + "\uf8ff")
        // Log the generated query
        Log.d("Query", "Generated query: $query")

        val options = FirebaseRecyclerOptions.Builder<DataModel.Section>()
            .setQuery(query, DataModel.Section::class.java)
            .build()
        adapter.updateOptions(options)
    }



    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}

class DataModel {
    data class Section(
        val description: String? = null,
        val section: Int? = null,
        val title: String? = null
    ) {
        // Add a public no-argument constructor
        constructor() : this(null, null, null)

        companion object {
            fun fromMap(map: Map<String, Any?>): Section {
                val description = map["description"] as? String
                val section = map["section"] as? Int
                val title = map["title"] as? String
                return Section(description, section, title)
            }
        }
    }
}