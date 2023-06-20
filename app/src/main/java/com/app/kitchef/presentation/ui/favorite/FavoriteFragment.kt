package com.app.kitchef.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kitchef.R
import com.app.kitchef.databinding.FragmentFavoriteBinding
import com.app.kitchef.domain.model.Recipe
import com.app.kitchef.presentation.ui.base.ScopeFragment
import com.app.kitchef.presentation.ui.recipeDetail.RecipeDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : ScopeFragment() {

    private val viewModel by viewModel<RecipeDetailViewModel>()
    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var rv: RecyclerView
    private lateinit var emptyStateMessage: TextView
    private lateinit var emptyStateBtn: Button
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var recipeList: ArrayList<Recipe>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindUI()
//        handleRecyclerView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        rv = binding.recyclerview
        emptyStateMessage = binding.emptyStateMessage
        emptyStateBtn = binding.searchBtn

        val searchBtn = binding.searchBtn
        searchBtn.setOnClickListener {
            navController?.navigate(R.id.homeFragment)
        }
    }

    private fun handleRecyclerView() {
        emptyStateMessage.visibility = View.GONE
        emptyStateBtn.visibility = View.GONE
        rv.visibility = View.VISIBLE

        favoriteAdapter = FavoriteAdapter(recipeList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = favoriteAdapter

        favoriteAdapter.setOnClickListener(object :
            FavoriteAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val action = FavoriteFragmentDirections.actionNavFavoriteToRecipeDetailFragment()
                action.recipeId = recipeList[position].recipeId
                findNavController().navigate(action)
            }
        })
    }

    private fun bindUI() = launch{
        recipeList = ArrayList()
        val persistedRecipe = viewModel.recipe.await()

        persistedRecipe.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
//            recipeList.add(it)
            handleRecyclerView()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}