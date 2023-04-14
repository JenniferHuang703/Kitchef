package com.example.kitchef

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.navigation.findNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.kitchef.common.StaticIngredientList
import com.example.kitchef.databinding.ActivityMainBinding
import com.example.kitchef.presentation.ui.home.AddIngredientViewModelFactory
import com.example.kitchef.presentation.ui.home.AddIngredientsViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigation()
//        handleSearching()
    }

    private fun setUpBottomNavigation() {
        val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        NavigationUI.setupWithNavController(binding.homeBottomNavbar, navFragment.navController)

        navFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.homeFragment -> setBottomNavVisibility(View.VISIBLE)
                R.id.favoriteFragment -> setBottomNavVisibility(View.VISIBLE)
                R.id.settingFragment -> setBottomNavVisibility(View.VISIBLE)
                else -> setBottomNavVisibility(View.GONE)
            }
        }
    }

    private fun setBottomNavVisibility(visibility: Int) {
        binding.homeBottomNavbar.visibility = visibility
    }

//    private fun handleSearching() {
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                viewModel.searchIngredientOnQueryTextSubmit(query, StaticIngredientList.preDefinedIngredientsList)
//                return true
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onQueryTextChange(newText: String?): Boolean {
//                viewModel.searchIngredientOnQueryTextChange(newText, StaticIngredientList.preDefinedIngredientsList)
//                return true
//            }
//        })
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}