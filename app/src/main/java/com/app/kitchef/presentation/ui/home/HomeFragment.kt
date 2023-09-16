package com.app.kitchef.presentation.ui.home

import android.content.Context
import  android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kitchef.R
import com.app.kitchef.common.StaticIngredientList
import com.app.kitchef.databinding.FragmentHomeBinding
import com.app.kitchef.domain.model.Ingredient
import com.app.kitchef.domain.utils.Resource
import com.app.kitchef.presentation.ui.MyOnFocusChangeListener
import com.app.kitchef.presentation.ui.base.ScopeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList

class HomeFragment : ScopeFragment() {

    private val viewModel by viewModel<AddIngredientsViewModel>()
    private var _binding: FragmentHomeBinding? = null
    private var navController: NavController? = null
    private lateinit var rv: RecyclerView
    private lateinit var addIngredientAdapter: AddIngredientsAdapter
    private var displayedIngredientsList = ArrayList<Ingredient>()
    private var addedIngredientList = ArrayList<Ingredient>()
    private val focusChangeListener = MyOnFocusChangeListener()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHomeTopBar()
        return binding.root
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

    private fun setHomeTopBar() {
        var lastInput = ""
        val debounceJob: Job? = null
        val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        binding.homeTopBar.searchFieldTextInput.onFocusChangeListener = focusChangeListener
        binding.homeTopBar.searchFieldTextInput.doAfterTextChanged { editable ->
            if (editable != null) {
                val newtInput = editable.toString()
                debounceJob?.cancel()
                if (lastInput != newtInput) {
                    lastInput = newtInput
                    uiScope.launch {
                        delay(500)
                        if (lastInput == newtInput) {
                            performSearch(newtInput)
                        }
                    }
                }
            }
        }
        binding.homeTopBar.searchFieldTextInput.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                textView.clearFocus()
                val inputManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(textView.windowToken, 0)
                performSearch(textView.text.toString())
                true
            } else {
                false
            }
        }
        binding.homeTopBar.searchFieldTextLayout.setEndIconOnClickListener {
            it.clearFocus()
            binding.homeTopBar.searchFieldTextInput.setText("")
            val inputManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(it.windowToken, 0)
            displayedIngredientsList.clear()
            displayedIngredientsList.addAll(StaticIngredientList.preDefinedIngredientsList)
            binding.recyclerview.adapter?.notifyDataSetChanged()
        }
    }

    private fun performSearch(query: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getSearchedIngredient(query).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        resource.data?.let{
                            Log.d("jen", it.toString())
                            displayedIngredientsList.clear()
                            displayedIngredientsList.add(Ingredient(it.id, it.name, it.image))
                        }
                        binding.recyclerview.adapter?.notifyDataSetChanged()
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun updateObserver() = launch {
        viewModel.tempIngredientsList.observe(viewLifecycleOwner, Observer { ingList ->
            if (ingList == null) return@Observer
            displayedIngredientsList.clear()
            displayedIngredientsList.addAll(ingList)
            binding.recyclerview.adapter?.notifyDataSetChanged()
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
                HomeFragmentDirections.actionNavHomeToAddedIngredientsFragment(
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
}