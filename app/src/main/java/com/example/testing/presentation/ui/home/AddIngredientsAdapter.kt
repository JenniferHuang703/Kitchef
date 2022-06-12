package com.example.testing.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testing.R
import com.example.testing.domain.model.Ingredient

class AddIngredientsAdapter(private val ingredientList: List<Ingredient>): RecyclerView.Adapter<AddIngredientsAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener
    private lateinit var context: Context

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listen: onItemClickListener) {
        mListener = listen
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        context = parent.context
        return ViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentIngredient = ingredientList[position]

        holder.ingredientTitle.text = currentIngredient.title
        Glide.with(context)
            .load(currentIngredient.image)
            .centerCrop()
            .circleCrop()
            .into(holder.ingredientImage)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    class ViewHolder(view: View, listener: onItemClickListener) : RecyclerView.ViewHolder(view) {
        val ingredientTitle: TextView = view.findViewById(R.id.ingredientTitle)
        val ingredientImage: ImageView = view.findViewById(R.id.ingredientImage)

        init {
            view.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}