package com.app.kitchef.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.kitchef.R
import com.app.kitchef.domain.model.Ingredient

class AddIngredientsAdapter(private val ingredientList: List<Ingredient>) :
    RecyclerView.Adapter<AddIngredientsAdapter.ViewHolder>() {

    private lateinit var onClickListener: OnClickListener
    private lateinit var context: Context

    interface OnClickListener {
        fun onAddItemClick(ingredient: Ingredient)
        fun onRemoveItemClick(ingredient: Ingredient)
    }

    fun setOnClickListener(listen: OnClickListener) {
        onClickListener = listen
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentIngredient = ingredientList[position]

        holder.ingredientTitle.text = currentIngredient.name
        Glide.with(context)
            .load(currentIngredient.image)
            .centerCrop()
            .circleCrop()
            .into(holder.ingredientImage)

        holder.addIngredientButton.setOnClickListener {
            onClickListener.onAddItemClick(currentIngredient)
            holder.addIngredientButton.visibility = View.GONE
            holder.removeIngredientButton.visibility = View.VISIBLE
        }

        holder.removeIngredientButton.setOnClickListener {
            onClickListener.onRemoveItemClick(currentIngredient)
            holder.addIngredientButton.visibility = View.VISIBLE
            holder.removeIngredientButton.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientTitle: TextView = view.findViewById(R.id.ingredientTitle)
        val ingredientImage: ImageView = view.findViewById(R.id.ingredientImage)
        val addIngredientButton: TextView = view.findViewById(R.id.addBtn)
        val removeIngredientButton: ImageView = view.findViewById(R.id.removeBtn)
    }
}