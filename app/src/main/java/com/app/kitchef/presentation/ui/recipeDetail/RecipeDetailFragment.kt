package com.app.kitchef.presentation.ui.recipeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.app.kitchef.R
import com.app.kitchef.presentation.ui.base.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeDetailFragment : ScopeFragment() {

    private val viewModel by viewModel<RecipeDetailViewModel>()
    private val args by navArgs<RecipeDetailFragmentArgs>()
    private var addToFavoriteBtnIsClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipe = args.recipeDetail
        val recipeImage = view.findViewById<ImageView>(R.id.recipeImage)
        val recipeLabel = view.findViewById<TextView>(R.id.recipeLabel)
        val recipeCuisineType = view.findViewById<TextView>(R.id.recipeCuisineType)
        val ingredientList = view.findViewById<TextView>(R.id.ingredientList)
        val addToFavoriteBtn = view.findViewById<ImageButton>(R.id.addToFavoriteBtn)

        Glide.with(requireContext())
            .load(recipe.image)
            .centerCrop()
            .into(recipeImage)

        recipeLabel.text = recipe.label
        recipeCuisineType.text = recipe.cuisineType[0]

        var str = ""
        recipe.ingredientLines.forEach {
            if(it != recipe.ingredientLines.first())
                str = "$str \n"
            str += it
        }
        ingredientList.text = str

        addToFavoriteBtn.setOnClickListener {
            addToFavoriteBtnIsClicked = !addToFavoriteBtnIsClicked
            if(addToFavoriteBtnIsClicked) {
                addToFavoriteBtn.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_full, null))
                viewModel.persistRecipe(recipe)
            }
            else {
                addToFavoriteBtn.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite_empty, null))
            }
        }
    }

}