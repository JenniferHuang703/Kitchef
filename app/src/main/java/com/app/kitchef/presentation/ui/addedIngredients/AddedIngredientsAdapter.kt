package com.app.kitchef.presentation.ui.addedIngredients

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
import com.app.kitchef.presentation.ui.home.AddIngredientsAdapter

class AddedIngredientsAdapter(private val ingredientList: List<Ingredient>): RecyclerView.Adapter<AddedIngredientsAdapter.ViewHolder>()  {

    private lateinit var onClickListener: OnClickListener
    private lateinit var context: Context

    interface OnClickListener {
        fun onRemoveItemClick(ingredient: Ingredient, position: Int)
    }

    fun setOnClickListener(listen: OnClickListener) {
        onClickListener = listen
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_remove_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentIngredient = ingredientList[position]

        holder.ingredientTitle.text = currentIngredient.name
        Glide.with(context)
            .load(currentIngredient.image)
            .into( holder.ingredientImage)

        holder.removeIngredientBtn.setOnClickListener {
            onClickListener.onRemoveItemClick(currentIngredient, position)
        }
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientTitle: TextView = view.findViewById(R.id.ingredientTitle)
        val ingredientImage: ImageView = view.findViewById(R.id.ingredientImage)
        val removeIngredientBtn: ImageView = view.findViewById(R.id.removeBtn)
    }
}