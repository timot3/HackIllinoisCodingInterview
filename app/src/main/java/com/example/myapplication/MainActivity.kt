package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val exampleList = generateDummyList(500)

        val view : RecyclerView = findViewById(R.id.recycler_view)
        makeRequest(view)
    }
    private fun generateList(arr: JSONArray, recycler_view: RecyclerView) {
        val list = ArrayList<Element>()
        for (i in 0 until arr.length()) {
            val event : JSONObject = arr.get(i) as JSONObject
            Log.d("EVENT" , event.toString())
            val time = (event.get("startTime") as Number).toLong()
//            val dt = Date(time)

            val dt = DateTimeFormatter.ISO_INSTANT
                .format(java.time.Instant.ofEpochSecond(time))

            val item = Element(event.get("name") as String, event.get("description") as String, dt)
            list += item
        }
        recycler_view.adapter = ElementAdapter(list)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }
    fun makeRequest(view: RecyclerView) {
        Log.d("IN FUNCTION makeRequest", "HERE")
        var jsonArr : JSONArray = JSONArray()

        val list = arrayListOf<Element>()

        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, "https://api.hackillinois.org/event/", null,
            Response.Listener { response ->
                Log.d("API CALL", response.toString())
//                textView.text = response.toString()

                jsonArr = response.getJSONArray("events")
                generateList(jsonArr, view)
            },
            Response.ErrorListener { error ->
//                textView.text = "That didn't work!"
                Log.e("ERROR", "That didn't work!")
            }
        )
//        viewText.text = txt
        queue.add(jsonObjectRequest)
    }
}
/*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url : String = "https://api.hackillinois.org/event/"
//        val textView: TextView = findViewById<TextView>(R.id.text_view)

//        val obj : JSONObject = makeRequest(url, textView)




    }


}*/