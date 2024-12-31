
package com.law.lawlens09

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.law.lawlens09.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
class SectionAdapteripc(
    options: FirebaseRecyclerOptions<DataModel3.Section>,
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : FirebaseRecyclerAdapter<DataModel3.Section, SectionAdapteripc.SectionViewHolder>(options) {

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sectionTextView: TextView = itemView.findViewById(R.id.sectionTextView)
        private val chapterTextView: TextView = itemView.findViewById(R.id.chapterTextView)
        private val chapterTitleTextView: TextView = itemView.findViewById(R.id.chapterTitleTextView)
        private val sectionDescTextView: TextView = itemView.findViewById(R.id.sectionDescTextView)
        private val sectionTitleTextView: TextView = itemView.findViewById(R.id.sectionTitleTextView)
        private val bookmarkImageView: ImageView = itemView.findViewById(R.id.bookmarkImageView)

        fun bind(section: DataModel3.Section) {
            // Bind the data to the views
            sectionTextView.text = "Section: ${section.Section}"
            chapterTextView.text = "Chapter: ${section.chapter}"
            chapterTitleTextView.text = "Chapter Title: ${section.chapter_title}"
            sectionDescTextView.text = "Description: ${section.section_desc}"
            sectionTitleTextView.text = "Title: ${section.section_title}"

            // Set bookmark icon based on bookmark status
            if (section.isBookmarked) {
                bookmarkImageView.setImageResource(R.drawable.bookmarked_icon)
            } else {
                bookmarkImageView.setImageResource(R.drawable.bookmarkicon)
            }

            // Handle bookmark click event
            bookmarkImageView.setOnClickListener {
                // Toggle bookmark status
                section.isBookmarked = !section.isBookmarked
                // Update bookmark icon
                if (section.isBookmarked) {
                    bookmarkImageView.setImageResource(R.drawable.bookmarked_icon)
                    // Save bookmarked section locally
                    saveBookmarkedSection(section)
                } else {
                    bookmarkImageView.setImageResource(R.drawable.bookmarkicon)
                    // Remove bookmarked section locally
                    removeBookmarkedSection(section)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemsectionipc, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int, model: DataModel3.Section) {
        holder.bind(model)
    }

    private fun saveBookmarkedSection(section: DataModel3.Section) {
        // Save bookmarked section locally using SharedPreferences
        val key = "bookmark_${section.Section}"
        editor.putBoolean(key, true)
        editor.apply()
    }

    private fun removeBookmarkedSection(section: DataModel3.Section) {
        // Remove bookmarked section locally using SharedPreferences
        val key = "bookmark_${section.Section}"
        editor.remove(key)
        editor.apply()
    }
}
