package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class MainActivity : AppCompatActivity() , ElementAdapter.OnItemClickListener{

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

            val ldt =
                Instant.ofEpochMilli(time * 1000L).atZone(ZoneId.systemDefault()).toLocalDateTime()

            val newTime = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm").format(ldt)

            Log.d("DATE TIME<<<<<<<<", newTime.toString())

            val item = Element(event.get("name") as String, event.get("description") as String, newTime)
            list += item
        }
        recycler_view.adapter = ElementAdapter(list, this)
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
                Log.e("ERROR", error.toString())
            }
        )
//        viewText.text = txt
        queue.add(jsonObjectRequest)
    }
    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
//        val clickedItem = exampleList[position]
//        clickedItem.text1 = "Clicked"
//        adapter.notifyItemChanged(position)
    }
}
