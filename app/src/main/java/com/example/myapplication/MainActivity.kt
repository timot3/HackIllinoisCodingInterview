package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url : String = "https://api.hackillinois.org/event/"
        val textView: TextView = findViewById<TextView>(R.id.text_view)

        val obj : JSONObject = makeRequest(url, textView)




    }

    fun makeRequest(url: String, textView : TextView): JSONObject {
        Log.d("IN FUNCTION makeRequest", "HERE")
        var jsonObj: JSONObject = JSONObject()

        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.d("API CALL", response.toString())
                textView.text = response.toString()

                jsonObj = response
            },
            Response.ErrorListener { error ->
                textView.text = "That didn't work!"
            }
        )
//        viewText.text = txt
        queue.add(jsonObjectRequest)
        //Log.d("PRINT ARRAY", jsonObj.toString())
        return jsonObj
    }
}