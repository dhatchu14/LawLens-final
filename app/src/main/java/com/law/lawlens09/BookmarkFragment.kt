package com.law.lawlens09
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class BookmarkFragment(private val databaseReference: DatabaseReference) : Fragment() {

    private lateinit var bookmarksRecyclerView: RecyclerView
    private lateinit var bookmarkAdapter: BookmarkAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)
        bookmarksRecyclerView = view.findViewById(R.id.bookmarksRecyclerView)
        bookmarksRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("bookmarks", Context.MODE_PRIVATE)

        // Retrieve bookmarked sections from SharedPreferences
        val bookmarkedSections = getBookmarkedSections()

        // Set up RecyclerView adapter
        bookmarkAdapter = BookmarkAdapter(bookmarkedSections)
        bookmarksRecyclerView.adapter = bookmarkAdapter

        return view
    }
    private fun getBookmarkedSections(): List<DataModel3.Section> {
        val bookmarkedSections = mutableListOf<DataModel3.Section>()
        val sectionNumbers = mutableListOf<Int>() // Keep track of section numbers in order

        // Iterate through SharedPreferences to get bookmarked sections
        val allEntries = sharedPreferences.all
        for ((key, value) in allEntries) {
            if (value is Boolean && value) {
                // Extract section number from the key
                val sectionNumber = key.substringAfter("bookmark_").toIntOrNull()
                if (sectionNumber != null) {
                    sectionNumbers.add(sectionNumber) // Keep track of section numbers in order
                    // Query Firebase database to get complete section details
                    databaseReference.child("ipc").child(sectionNumber.toString())
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val section = snapshot.getValue(DataModel3.Section::class.java)
                                section?.let {
                                    // Add retrieved section to the list
                                    bookmarkedSections.add(it)
                                    // Notify adapter of data change
                                    bookmarkAdapter.notifyDataSetChanged()
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Handle error
                            }
                        })
                }
            }
        }

        // Sort the bookmarkedSections list based on the order of section numbers
        bookmarkedSections.sortBy { sectionNumbers.indexOf(it.Section) }

        return bookmarkedSections
    }

}
