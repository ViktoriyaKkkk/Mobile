package com.example.lesson_4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lesson_4.databinding.PersonItemBinding
import com.google.android.material.snackbar.Snackbar

class PersonsAdapter(private val activityFunctions: ActivityFunctions) : RecyclerView.Adapter<PersonsAdapter.PersonsViewHolder>() {

    var personsList = mutableListOf<Persons>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PersonItemBinding.inflate(inflater, parent, false)
        return PersonsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonsViewHolder, position: Int) {

        val person = personsList[position]

        holder.itemView.setOnClickListener{
            activityFunctions.showFormClickEvent(person.up_name)
        }

        with(holder.binding){
            upLike.setOnClickListener {
                activityFunctions.showLikeClickEvent(person.up_name)
            }
            Glide.with(upPicture.context)
                .load(person.up_picture)
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(upPicture)
            upName.text = person.up_name
            upDate.text = person.up_date
            upText.text = person.up_text
            upSex.text = person.up_sex
        }

    }

    override fun getItemCount(): Int {
        return personsList.size
    }

    class PersonsViewHolder(var binding: PersonItemBinding): RecyclerView.ViewHolder(binding.root)

}