package com.sw.sw_api_kotlin_project.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sw.sw_api_kotlin_project.R
import com.sw.sw_api_kotlin_project.model.peple.People

class PeopleAdapter(private val peopleList: List<People>) :
    RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val fullName = view.findViewById<TextView>(R.id.full_name_text)
        private val height = view.findViewById<TextView>(R.id.height_text)
        private val mass = view.findViewById<TextView>(R.id.mass_text)

        fun bind(people: People) {
            fullName.text = people.name
            height.text = people.height
            mass.text = people.mass
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_people, parent, false)

        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(peopleList[position])
    }

    override fun getItemCount() = peopleList.size


}