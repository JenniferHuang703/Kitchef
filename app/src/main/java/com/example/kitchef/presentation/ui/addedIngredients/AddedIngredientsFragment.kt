package com.example.kitchef.presentation.ui.addedIngredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchef.R
import com.example.kitchef.domain.model.Ingredient
import com.example.kitchef.presentation.ui.home.AddIngredientViewModelFactory
import com.example.kitchef.presentation.ui.home.AddIngredientsViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class AddedIngredientsFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: AddIngredientViewModelFactory by instance()

    private lateinit var viewModel: AddIngredientsViewModel

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AddIngredientsViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        addedIngredientList.addAll(args.addedIngredientList)

        handleRecyclerView(view)
        onClickListeners(view)
    }

    private fun onClickListeners(view:View) {
        val proceedToRecipeBtn = view.findViewById<Button>(R.id.proceedToRecipeBtn)
        proceedToRecipeBtn.setOnClickListener{
            val directions =
                AddedIngredientsFragmentDirections.actionAddedIngredientsFragmentToRecommendedRecipesFragment(
                    addedIngredientList.toTypedArray()
                )
            findNavController().navigate(directions)
        }
    }

    private fun handleRecyclerView(view: View) {
        rv = view.findViewById(R.id.recyclerview)
        addedIngredientAdapter = AddedIngredientsAdapter(addedIngredientList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = addedIngredientAdapter

        addedIngredientAdapter.setOnClickListener(object : AddedIngredientsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                addedIngredientList.remove(addedIngredientList[position])
                addedIngredientAdapter.notifyItemRemoved(position)
                viewModel.modifyIngredientList(addedIngredientList)
            }
        })
    }
}