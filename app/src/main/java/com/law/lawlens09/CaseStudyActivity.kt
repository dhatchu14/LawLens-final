package com.law.lawlens09


// CaseStudyActivity.kt
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class CaseStudyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_study)

        makeApiRequest()
    }

    private fun makeApiRequest() {
        val apiKey = "579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b"
        val format = "xml"
        val offset = 0 // You can adjust this for pagination
        val limit = 10 // Adjust this to control the number of records returned

        val url = "https://api.example.com/resource/293f8abf-c0b4-47b2-9bf7-41d5a84deb2c" +
                "?api-key=$apiKey" +
                "&format=$format" +
                "&offset=$offset" +
                "&limit=$limit"

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                println(responseBody)
                // Process the XML response here
            }
        })
    }
}
