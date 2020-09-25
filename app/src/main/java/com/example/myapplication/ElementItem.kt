package com.example.myapplication
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.element.view.*
class ElementAdapter(private val elementList: List<Element>) :
    RecyclerView.Adapter<ElementAdapter.ElementViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.element,
            parent, false)
        return ElementViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val currentItem = elementList[position]
//        holder.imageView.setImageResource(currentItem.)
        holder.title.text = currentItem.title
        holder.desc.text = currentItem.description
        holder.time.text = currentItem.date.toString()
    }
    override fun getItemCount() = elementList.size
    class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.image_view
        val title: TextView = itemView.event_title
        val desc: TextView = itemView.event_description
        val time: TextView = itemView.event_time
    }
}
