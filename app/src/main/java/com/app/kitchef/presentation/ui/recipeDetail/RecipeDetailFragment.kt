package com.app.kitchef.presentation.ui.recipeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.app.kitchef.R
import com.app.kitchef.databinding.FragmentRecipeDetailBinding
import com.app.kitchef.domain.model.RecipeDetail
import com.app.kitchef.domain.utils.Resource
import com.app.kitchef.presentation.ui.base.ScopeFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RecipeDetailFragment : ScopeFragment() {

    private val viewModel by viewModel<RecipeDetailViewModel>()
    private val args by navArgs<RecipeDetailFragmentArgs>()
    private var addToFavoriteBtnIsClicked = false
    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding  get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()
    }

    private fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRecipeDetail(args.recipeId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                           setUpViews(it)
                        }
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setUpViews(recipe: RecipeDetail) {
        val recipeImage = binding.recipeImage
        val recipeLabel = binding.recipeLabel
        val cookingTime = binding.recipeDuration
        val recipeCuisineType = binding.recipeCuisineType
        val ingredientList = binding.ingredientList
        val addToFavoriteBtn = binding.addToFavoriteBtn
        val instruction = binding.instruction

        Glide.with(requireContext())
            .load(recipe.dishImageUrl)
            .centerCrop()
            .into(recipeImage)

        recipeLabel.text = recipe.dishName
        cookingTime.text = recipe.cookingTime.toString() + "min"
//        recipeCuisineType.text = recipe.cuisineType[0]
        recipeCuisineType.text = "Asian"

        var str = ""
//        recipe.ingredients.forEach {
//            if(it != recipe.ingredients.first())
//                str = "$str \n"
//            str += it.name
//        }
        ingredientList.text = str

        instruction.text = recipe.shortInstruction

        addToFavoriteBtn.setOnClickListener {
            addToFavoriteBtnIsClicked = !addToFavoriteBtnIsClicked
            if (addToFavoriteBtnIsClicked) {
                addToFavoriteBtn.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_favorite_full,
                        null
                    )
                )
//                viewModel.persistRecipe(recipe)
            } else {
                addToFavoriteBtn.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_favorite_empty,
                        null
                    )
                )
            }
        }

        binding.topAppBar.toolbar.title = recipe.dishName

        binding.topAppBar.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}