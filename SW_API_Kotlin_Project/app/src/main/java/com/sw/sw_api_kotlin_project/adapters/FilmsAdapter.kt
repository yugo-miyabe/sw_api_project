package com.sw.sw_api_kotlin_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sw.sw_api_kotlin_project.R
import com.sw.sw_api_kotlin_project.data.model.Films

class FilmsAdapter(private val filmsList: List<Films>, private val onClick: (Films) -> Unit) :
    RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    class ViewHolder(private val view: View, private val onClick: (Films) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val move = view.findViewById<TextView>(R.id.move_name)
        fun bind(films: Films) {
            move.text = films.title
            view.setOnClickListener {
                onClick(films)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_films, parent, false)

        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filmsList[position])
    }

    override fun getItemCount() = filmsList.size


}