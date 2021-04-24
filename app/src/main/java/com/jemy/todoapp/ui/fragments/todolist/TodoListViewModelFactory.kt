package com.jemy.todoapp.ui.fragments.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jemy.todoapp.data.repository.TodoListRepository
import javax.inject.Inject

class TodoListViewModelFactory @Inject constructor(
    private val repository: TodoListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            return TodoListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name need ${TodoListViewModel::class.java.simpleName} instance")
    }
}