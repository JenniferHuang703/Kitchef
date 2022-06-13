package com.example.kitchef.presentation.ui.recommendedRecipes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kitchef.R

class RecommendedRecipesAdapter(private val recipesList: List<com.example.kitchef.data.db.entity.recipeModel.Recipe>): RecyclerView.Adapter<RecommendedRecipesAdapter.ViewHolder>() {


    private lateinit var mListener: onItemClickListener
    private lateinit var context: Context

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listen: onItemClickListener) {
        mListener = listen
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_card, parent, false)
        context = parent.context
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecipe = recipesList[position]

        holder.recipeLabel.text = currentRecipe.label
        Glide.with(context)
            .load(currentRecipe.image)
            .centerCrop()
            .into(holder.recipeImage)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val recipeLabel: TextView = view.findViewById(R.id.recipeLabel)
        val recipeImage: ImageView = view.findViewById(R.id.recipeImage)

        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}