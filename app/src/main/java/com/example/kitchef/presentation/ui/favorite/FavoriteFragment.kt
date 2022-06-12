package com.example.kitchef.presentation.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchef.R
import com.example.kitchef.common.StaticRecipeList
import com.example.kitchef.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var rv: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        handleRecyclerView(view)

        val searchBtn = view.findViewById<Button>(R.id.searchBtn)
        searchBtn.setOnClickListener {
            navController?.navigate(R.id.nav_home)
        }
    }

    private fun handleRecyclerView(view: View) {
        rv = view.findViewById(R.id.recyclerview)
        favoriteAdapter = FavoriteAdapter(StaticRecipeList.predefinedRecipeList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = favoriteAdapter

        favoriteAdapter.setOnClickListener(object :
            FavoriteAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}