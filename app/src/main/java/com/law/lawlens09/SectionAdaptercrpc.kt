package com.law.lawlens09

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class SectionAdaptercrpc(options: FirebaseRecyclerOptions<DataModel1.Section1>) :
    FirebaseRecyclerAdapter<DataModel1.Section1, SectionAdaptercrpc.SectionViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int, model: DataModel1.Section1) {
        holder.bind(model)
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sectionTitleTextView: TextView = itemView.findViewById(R.id.sectionTitleTextView)
        private val sectionDescTextView: TextView = itemView.findViewById(R.id.sectionDescTextView)
        private val chapterTextView: TextView = itemView.findViewById(R.id.chapterTextView)
        private val sectionTextView: TextView = itemView.findViewById(R.id.sectionTextView)

        fun bind(section: DataModel1.Section1) {
            sectionTitleTextView.text = section.section_title
            sectionDescTextView.text = section.section_desc
            chapterTextView.text = "Chapter: ${section.chapter}"
            sectionTextView.text = "Section: ${section.section}"
        }
    }
}
