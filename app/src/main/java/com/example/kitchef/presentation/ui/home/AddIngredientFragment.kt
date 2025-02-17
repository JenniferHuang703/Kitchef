package com.example.kitchef.presentation.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kitchef.R
import com.example.kitchef.common.StaticIngredientList
import com.example.kitchef.databinding.FragmentHomeBinding
import com.example.kitchef.domain.model.Ingredient
import com.example.kitchef.presentation.ui.base.ScopeFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import kotlin.collections.ArrayList

class AddIngredientFragment : ScopeFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: AddIngredientViewModelFactory by instance()
    private lateinit var viewModel: AddIngredientsViewModel
    private var _binding: FragmentHomeBinding? = null
    private var navController: NavController? = null
    private lateinit var rv: RecyclerView
    private lateinit var addIngredientAdapter: AddIngredientsAdapter
    private var displayedIngredientsList = ArrayList<Ingredient>()
    private var addedIngredientList = ArrayList<Ingredient>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AddIngredientsViewModel::class.java)

        updateObserver()
        bindUI()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        addIngredientItemToList()
        handleRecyclerView(view)

        val emptyStateMessage = view.findViewById<TextView>(R.id.emptyStateTV)
        emptyStateMessage.visibility = View.VISIBLE

        onClickListeners(view)
    }

    private fun bindUI() = launch{
        val currentIngredient = viewModel.ingredient.await()

        currentIngredient.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            val ing = Ingredient(it.parsed[0].food.image, it.parsed[0].food.label)
            displayedIngredientsList.clear()
            displayedIngredientsList.add(ing)
            recyclerview.adapter?.notifyDataSetChanged()
        })
    }

    private fun updateObserver() = launch {
        viewModel.tempIngredientsList.observe(viewLifecycleOwner, Observer { ingList ->
            if (ingList == null) return@Observer
            displayedIngredientsList.clear()
            displayedIngredientsList.addAll(ingList)
            recyclerview.adapter?.notifyDataSetChanged()
        })

        viewModel.modifiedIngredientList.observe(viewLifecycleOwner, Observer { newIngList ->
            if (newIngList == null) return@Observer
            addedIngredientList.clear()
            addedIngredientList.addAll(newIngList)
//            setButtonView(view)
        })
    }

    private fun onClickListeners(view: View) {
        val proceedToListBtn = view.findViewById<Button>(R.id.proceedToListBtn)
        proceedToListBtn.setOnClickListener {
            val directions =
                AddIngredientFragmentDirections.actionNavHomeToAddedIngredientsFragment(
                    addedIngredientList.toTypedArray()
                )
            findNavController().navigate(directions)
        }
    }

    private fun handleRecyclerView(view: View) {
        rv = view.findViewById(R.id.recyclerview)
        addIngredientAdapter = AddIngredientsAdapter(displayedIngredientsList)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = addIngredientAdapter

        addIngredientAdapter.setOnClickListener(object : AddIngredientsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                addedIngredientList.add(displayedIngredientsList[position])
                setButtonView(view)
            }
        })
    }

    private fun setButtonView(view: View) {
        if (addedIngredientList.isNotEmpty()) {
            view.findViewById<Button>(R.id.proceedToListBtn).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.emptyStateTV).visibility = View.GONE
        }
    }

    private fun addIngredientItemToList() {
        if (displayedIngredientsList.isEmpty())
            displayedIngredientsList.addAll(StaticIngredientList.preDefinedIngredientsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun fetchData() {
//        val apiService = IngredientApiService(ConnectivityInterceptorImpl(this.requireContext()))
//        val ingredientNetworkDataSource = IngredientNetworkDataSourceImpl(apiService)
//
//        ingredientNetworkDataSource.downloadedCurrrentIngredient.observe(viewLifecycleOwner, Observer {
//            Log.d("jen", it.parsed[0].food.label)
//            Log.d("jen", it.parsed[0].food.image)
//        })
//
//        GlobalScope.launch(Dispatchers.Main) {
//            ingredientNetworkDataSource.fetchCurrentIngredient("Carrot", "cooking")
//        }
//    }
}