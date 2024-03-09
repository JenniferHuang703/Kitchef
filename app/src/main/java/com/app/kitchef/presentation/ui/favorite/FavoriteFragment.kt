package com.app.kitchef.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.kitchef.R
import com.app.kitchef.databinding.FragmentFavoriteBinding
import com.app.kitchef.domain.model.FavoriteRecipe
import com.app.kitchef.presentation.ui.base.ScopeFragment
import com.app.kitchef.presentation.ui.recipeDetail.RecipeDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : ScopeFragment() {

    private val viewModel by viewModel<RecipeDetailViewModel>()
    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var favoriteAdapter: FavoriteAdapter

    private val binding get() = _binding!!
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

//        handleRecyclerView()
        observeData()
        handleClickListener()
    }

    private fun handleRecyclerView(recipeList: List<FavoriteRecipe>) {
        favoriteAdapter = FavoriteAdapter(recipeList)
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        binding.recyclerview.adapter = favoriteAdapter

        favoriteAdapter.setOnClickListener(object :
            FavoriteAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val action = FavoriteFragmentDirections.actionNavFavoriteToRecipeDetailFragment()
                action.recipeId = recipeList[position].dishId
                findNavController().navigate(action)
            }
        })
    }

    private fun handleClickListener() {
        binding.searchBtn.setOnClickListener {
            navController?.navigate(R.id.homeFragment)
        }
    }

    private fun observeData() {
        viewModel.favoriteMediatorLiveData.observe(viewLifecycleOwner) { favoriteRecipeList ->
            favoriteRecipeList?.let {
//                favoriteAdapter.submitList(it)

                if(it.isNotEmpty()) {
                    binding.recyclerview.visibility = View.VISIBLE
                    binding.emptyStateMessage.visibility = View.GONE
                    binding.searchBtn.visibility = View.GONE
                    handleRecyclerView(it)
                } else {
                    binding.emptyStateMessage.visibility = View.VISIBLE
                    binding.searchBtn.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}