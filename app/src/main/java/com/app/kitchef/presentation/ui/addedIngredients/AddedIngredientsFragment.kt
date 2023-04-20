package com.app.kitchef.presentation.ui.addedIngredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kitchef.R
import com.app.kitchef.domain.model.Ingredient
import com.app.kitchef.presentation.ui.home.AddIngredientsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddedIngredientsFragment : Fragment() {
    private val viewModel by viewModel<AddIngredientsViewModel>()

    private var addedIngredientList = ArrayList<Ingredient>()
    private val args by navArgs<AddedIngredientsFragmentArgs>()
    private lateinit var rv: RecyclerView
    private var navController: NavController? = null
    private lateinit var addedIngredientAdapter: AddedIngredientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_added_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        if (addedIngredientList.isEmpty())
            addedIngredientList.addAll(args.addedIngredientList)

        handleRecyclerView(view)
        onClickListeners(view)
    }

    private fun onClickListeners(view:View) {
        val ingredientList = ArrayList<String>()
        val searchForRecipeBtn = view.findViewById<Button>(R.id.proceedToRecipeBtn)
        searchForRecipeBtn.setOnClickListener{
            addedIngredientList.forEach {
                ingredientList.add(it.title)
            }
            val directions =
                AddedIngredientsFragmentDirections.actionAddedIngredientsFragmentToRecommendedRecipesFragment(
                    ingredientList.toTypedArray()
                )
            findNavController().navigate(directions)
        }
    }

    private fun handleRecyclerView(view: View) {
        rv = view.findViewById(R.id.recyclerview)
        addedIngredientAdapter = AddedIngredientsAdapter(addedIngredientList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = addedIngredientAdapter

        addedIngredientAdapter.setOnClickListener(object : AddedIngredientsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                addedIngredientList.remove(addedIngredientList[position])
                addedIngredientAdapter.notifyItemRemoved(position)
                viewModel.modifyIngredientList(addedIngredientList)
            }
        })
    }
}