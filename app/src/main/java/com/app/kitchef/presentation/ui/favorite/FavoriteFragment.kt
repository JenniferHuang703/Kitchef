package com.app.kitchef.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kitchef.R
import com.app.kitchef.data.db.entity.recipeModel.Recipe
import com.app.kitchef.databinding.FragmentFavoriteBinding
import com.app.kitchef.presentation.ui.base.ScopeFragment
import com.app.kitchef.presentation.ui.recipeDetail.RecipeDetailViewModel
import com.app.kitchef.presentation.ui.recipeDetail.RecipeDetailViewModelFactory
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FavoriteFragment : ScopeFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: RecipeDetailViewModelFactory by instance()
    private lateinit var viewModel: RecipeDetailViewModel
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
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(RecipeDetailViewModel::class.java)

        bindUI()
//        handleRecyclerView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        rv = view.findViewById(R.id.recyclerview)
        emptyStateMessage = view.findViewById(R.id.emptyStateMessage)
        emptyStateBtn = view.findViewById(R.id.searchBtn)

        val searchBtn = view.findViewById<Button>(R.id.searchBtn)
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
                val directions = FavoriteFragmentDirections.actionNavFavoriteToRecipeDetailFragment(
                    recipeList[position]
                )
                findNavController().navigate(directions)
            }
        })
    }

    private fun bindUI() = launch{
        recipeList = ArrayList<Recipe>()
        val persistedRecipe = viewModel.recipe.await()

        persistedRecipe.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            recipeList.add(it)
            handleRecyclerView()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}