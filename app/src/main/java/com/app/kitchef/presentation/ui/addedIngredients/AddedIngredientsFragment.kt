package com.app.kitchef.presentation.ui.addedIngredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kitchef.databinding.FragmentAddedIngredientsBinding
import com.app.kitchef.domain.model.Ingredient
import com.app.kitchef.presentation.ui.home.AddIngredientsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddedIngredientsFragment : Fragment() {
    private val viewModel by viewModel<AddIngredientsViewModel>()

    private lateinit var rv: RecyclerView
    private var navController: NavController? = null
    private lateinit var addedIngredientAdapter: AddedIngredientsAdapter
    private var _binding: FragmentAddedIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddedIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        getIngredientList()
        onClickListeners()
    }

    private fun getIngredientList() {
        viewModel.ingredientMediatorLiveData.observe(viewLifecycleOwner) { ingredientList ->
            ingredientList?.let {
                if (it.isNotEmpty()) {
                    handleRecyclerView(it)
                } else {

                }
            }
        }
    }

    private fun onClickListeners() {
        binding.proceedToRecipeBtn.setOnClickListener {
            val directions =
                AddedIngredientsFragmentDirections.actionAddedIngredientsFragmentToRecommendedRecipesFragment()
            findNavController().navigate(directions)
        }

        binding.topAppBar.toolbar.title = "Ingredients List"

        binding.topAppBar.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun handleRecyclerView(ingredientList: List<Ingredient>) {
        rv = binding.recyclerview
        addedIngredientAdapter = AddedIngredientsAdapter(ingredientList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = addedIngredientAdapter

        addedIngredientAdapter.setOnClickListener(object : AddedIngredientsAdapter.OnClickListener {
            override fun onRemoveItemClick(ingredient: Ingredient, position: Int) {
                viewModel.toggleRemoveIngredientToList(ingredient.id)
                addedIngredientAdapter.notifyItemRemoved(position)
            }
        })
    }
}