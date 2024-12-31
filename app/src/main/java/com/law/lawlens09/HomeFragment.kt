package com.law.lawlens09

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class HomeFragment : Fragment() {
    private lateinit var MY: ImageView
    private lateinit var crpcImageView: ImageView
    private lateinit var ipcImageView: ImageView
    private lateinit var crpcTextView: TextView
    private lateinit var ipcTextView: TextView
    private lateinit var cpcTextView: TextView
    private lateinit var sosview: ImageView

    private lateinit var loginbutton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        MY = view.findViewById<ImageView>(R.id.imageView4)
        MY.setOnClickListener {
            Log.d("image view clicked", "inside image view")
            val intent = Intent(requireContext(), CPCActivity::class.java)
            startActivity(intent)
        }

        crpcImageView = view.findViewById<ImageView>(R.id.imageView6)
        crpcImageView.setOnClickListener {
            Log.d("crpc image view clicked", "inside crpc image view")
            val intent = Intent(requireContext(), CRPCActivity::class.java)
            startActivity(intent)
        }

        ipcImageView = view.findViewById<ImageView>(R.id.imageView5)
        ipcImageView.setOnClickListener {
            Log.d("ipc image view clicked", "inside ipc image view")
            val intent = Intent(requireContext(), IPCActivity::class.java)
            startActivity(intent)
        }
         sosview = view.findViewById<ImageView>(R.id.imageView10)
        sosview.setOnClickListener {
            Log.d("emergency image view clicked", "inside emergency view")
            val intent = Intent(requireContext(), EmergencyActivity::class.java)
            startActivity(intent)
        }


        // TextViews
        crpcTextView = view.findViewById<TextView>(R.id.textview7)
        crpcTextView.setOnClickListener {
            Log.d("crpc textview clicked", "inside crpc textview")
            val intent = Intent(requireContext(), CRPCActivity::class.java)
            startActivity(intent)
        }

        ipcTextView = view.findViewById<TextView>(R.id.textView1)
        ipcTextView.setOnClickListener {
            Log.d("ipc textview clicked", "inside ipc textview")
            val intent = Intent(requireContext(), IPCActivity::class.java)
            startActivity(intent)
        }

        cpcTextView = view.findViewById<TextView>(R.id.textview6)
        cpcTextView.setOnClickListener {
            Log.d("cpc textview clicked", "inside cpc textview")
            val intent = Intent(requireContext(), CPCActivity::class.java)
            startActivity(intent)
        }

        // Button
        val btnViewCourse = view.findViewById<Button>(R.id.idBtnViewCourse)
        btnViewCourse.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://doj.gov.in/acts-and-rules/"))
            startActivity(intent)
        }
        // New Show Tips Button
        val showTipsButton = view.findViewById<Button>(R.id.showTipsButton)
        showTipsButton.setOnClickListener {
            val intent = Intent(requireContext(), TipActivity::class.java)
            startActivity(intent)
        }

        return view
    }



}
