package com.law.lawlens09
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class IPCActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SectionAdapteripc
    private lateinit var databaseReference: DatabaseReference
    private lateinit var searchInput: EditText
    private lateinit var searchBtn: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ipc)

        sharedPreferences = getSharedPreferences("bookmarks", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        databaseReference = FirebaseDatabase.getInstance("https://lawlens02-default-rtdb.asia-southeast1.firebasedatabase.app")
            .reference.child("ipc")

        searchInput = findViewById(R.id.search_input)
        searchBtn = findViewById(R.id.search_btn)

        searchBtn.setOnClickListener {
            val searchText = searchInput.text.toString()
            if (searchText.isNotEmpty()) {
                search(searchText)
            } else {
                loadAllData()
            }
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString()
                if (searchText.isNotEmpty()) {
                    search(searchText)
                } else {
                    loadAllData()
                }
            }
        })

        loadAllData()
    }

    private fun loadAllData() {
        val options = FirebaseRecyclerOptions.Builder<DataModel3.Section>()
            .setQuery(databaseReference, DataModel3.Section::class.java)
            .build()

        adapter = SectionAdapteripc(options, sharedPreferences, editor)
        recyclerView.adapter = adapter
        adapter.startListening()
    }

    private fun search(query: String) {
        val queryRef = databaseReference.orderByChild("section_title")
            .startAt(query)
            .endAt(query + "\uf8ff")

        val options = FirebaseRecyclerOptions.Builder<DataModel3.Section>()
            .setQuery(queryRef, DataModel3.Section::class.java)
            .build()

        adapter = SectionAdapteripc(options, sharedPreferences, editor)
        recyclerView.adapter = adapter
        adapter.startListening()
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





class DataModel3 {
    data class Section(
        val Section: Int? = null,
        val chapter: Int? = null,
        val chapter_title: String? = null,
        val section_desc: String? = null,
        val section_title: String? = null,
        var isBookmarked: Boolean = false // Add this property

    ) {
        // Add a public no-argument constructor
        constructor() : this(null, null, null, null, null)

        companion object {
            fun fromMap(map: Map<String, Any?>): Section {
                val Section= map["Section"] as? Int
                val chapter = map["chapter"] as? Int
                val chapter_title = map["chapter_title"] as? String
                val section_desc = map["section_desc"] as? String
                val section_title = map["section_title"] as? String

                return Section(Section, chapter, chapter_title, section_desc, section_title)
            }
        }
    }
}
