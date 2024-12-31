package com.law.lawlens09

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

class CRPCActivity : AppCompatActivity() {
    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter2: SectionAdaptercrpc
    private lateinit var databaseReference2: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crpc)

        recyclerView2 = findViewById(R.id.recyclerView)
        recyclerView2.layoutManager = LinearLayoutManager(this)
        databaseReference2 = FirebaseDatabase.getInstance("https://lawlens01-default-rtdb.asia-southeast1.firebasedatabase.app/").reference.child("crpc")

         val options = FirebaseRecyclerOptions.Builder<DataModel1.Section1>()
            .setQuery(databaseReference2, DataModel1.Section1::class.java)
            .build()

        adapter2 = SectionAdaptercrpc(options)
        recyclerView2.adapter = adapter2
    }

    override fun onStart() {
        super.onStart()
        adapter2.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter2.stopListening()
    }
}

class DataModel1 {
    data class Section1(
        val chapter: Int? = null,
        val section: Int? = null,
        val section_desc: String? = null,
        val section_title: String? = null
    ) {
        // Add a public no-argument constructor
        constructor() : this(null, null, null, null)

        companion object {
            fun fromMap(map: Map<String, Any?>): Section1 {
                val chapter = (map["chapter"] as? Long)?.toInt() // Firebase returns Long for Integers
                val section = (map["section"] as? Long)?.toInt()
                val section_desc = map["section_desc"] as? String
                val section_title = map["section_title"] as? String
                return Section1(chapter, section, section_desc, section_title)
            }
        }
    }
}
