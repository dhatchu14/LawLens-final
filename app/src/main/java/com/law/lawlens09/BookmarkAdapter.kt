package com.law.lawlens09

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.law.lawlens09.R

class BookmarkAdapter(private val bookmarkedSections: List<DataModel3.Section>) : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>() {

    class BookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sectionTextView: TextView = itemView.findViewById(R.id.sectionTextView)
        private val chapterTextView: TextView = itemView.findViewById(R.id.chapterTextView)
        private val chapterTitleTextView: TextView = itemView.findViewById(R.id.chapterTitleTextView)
        private val sectionDescTextView: TextView = itemView.findViewById(R.id.sectionDescTextView)
        private val sectionTitleTextView: TextView = itemView.findViewById(R.id.sectionTitleTextView)

        fun bind(section: DataModel3.Section) {
            sectionTextView.text = "Section: ${section.Section}"
            chapterTextView.text = "Chapter: ${section.chapter}"
            chapterTitleTextView.text = "Chapter Title: ${section.chapter_title}"
            sectionDescTextView.text = "Description: ${section.section_desc}"
            sectionTitleTextView.text = "Title: ${section.section_title}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bookmark, parent, false)
        return BookmarkViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(bookmarkedSections[position])
    }

    override fun getItemCount(): Int {
        return bookmarkedSections.size
    }
}
