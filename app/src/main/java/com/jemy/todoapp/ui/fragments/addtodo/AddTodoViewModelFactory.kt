package com.jemy.todoapp.ui.fragments.addtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jemy.todoapp.data.repository.AddTodoRepository
import javax.inject.Inject

class AddTodoViewModelFactory @Inject constructor(
    private val repository: AddTodoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTodoViewModel::class.java)) {
            return AddTodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name need ${AddTodoViewModel::class.java.simpleName} instance")
    }
}