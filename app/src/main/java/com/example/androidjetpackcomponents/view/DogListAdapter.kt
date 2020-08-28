package com.example.androidjetpackcomponents.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.androidjetpackcomponents.R
import com.example.androidjetpackcomponents.model.DogBreed
import com.example.androidjetpackcomponents.util.getProgressDrawable
import com.example.androidjetpackcomponents.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*
import java.util.zip.Inflater

class DogListAdapter(val dogList: ArrayList<DogBreed>): RecyclerView.Adapter<DogListAdapter.dogViewHolder>() {

    fun updateDogList(newDogList : List<DogBreed>){
        dogList.clear()
        dogList.addAll(newDogList)
        notifyDataSetChanged()
    }
    class dogViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dogViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_dog,parent,false)
        return dogViewHolder(view)

    }

    override fun getItemCount(): Int = dogList.size

    override fun onBindViewHolder(holder: dogViewHolder, position: Int) {
        holder.view.name.text = dogList[position].dogBreed
        holder.view.lifespan.text = dogList[position].lifeSpan
        holder.view.setOnClickListener {
            Navigation.findNavController(it).navigate(ListFragmentDirections.actionListFragmentToDetailFragment(position))
        }
        holder.view.imageView.loadImage(dogList[position].imageUrl, getProgressDrawable( holder.view.imageView.context))
    }
}