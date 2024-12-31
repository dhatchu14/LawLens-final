package com.law.lawlens09

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class SectionAdapter(options: FirebaseRecyclerOptions<DataModel.Section>) :
    FirebaseRecyclerAdapter<DataModel.Section, SectionAdapter.SectionViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.itemsectioncpc, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int, model: DataModel.Section) {
        holder.bind(model)
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val sectionTextView: TextView = itemView.findViewById(R.id.sectionTextView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

        fun bind(section: DataModel.Section) {
            // Bind the data to the views
            descriptionTextView.text = "Description: ${section.description}"
            sectionTextView.text = "Section: ${section.section}"
            titleTextView.text = "Title: ${section.title}"
        }
    }
}
