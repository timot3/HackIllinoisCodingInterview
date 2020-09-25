package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val exampleList = generateDummyList(500)
        recycler_view.adapter = ExampleAdapter(exampleList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }
    private fun generateDummyList(size: Int): List<Element> {
        val list = ArrayList<Element>()
        for (i in 0 until size) {

            val item = Element("Item $i", "Line 2", Calendar.getInstance())

            list += item
        }
        return list
    }
    fun makeRequest(url: String, textView : TextView): JSONObject {
        Log.d("IN FUNCTION makeRequest", "HERE")
        var jsonObj: JSONObject = JSONObject()

        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
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