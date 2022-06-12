package com.example.testing.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testing.domain.repository.AddIngredientRepository

class AddIngredientViewModelFactory(
    private val addIngredientRepository: AddIngredientRepository
    ) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddIngredientsViewModel(addIngredientRepository) as T
    }
}