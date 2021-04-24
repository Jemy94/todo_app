package com.jemy.todoapp.ui.fragments.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jemy.todoapp.data.entity.TodoEntity
import com.jemy.todoapp.data.repository.TodoListRepository
import com.jemy.todoapp.utils.Resource
import com.jemy.todoapp.utils.ResourceState
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class TodoListViewModel(private val repository: TodoListRepository) : ViewModel() {

    fun getTodoList() = liveData(Dispatchers.IO) {
        emit(Resource(ResourceState.LOADING, data = null))
        try {
            val todoList = repository.getTodoList()
            emit(Resource(ResourceState.SUCCESS, data = todoList))
        } catch (exception: Exception) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    data = null,
                    message = exception.message ?: "Unknown error"
                )
            )
        } catch (exception: IOException) {
            Resource(
                ResourceState.ERROR,
                data = null,
                message = exception.message ?: "Unknown error"
            )
        }
    }

    fun deleteTodo(todoEntity: TodoEntity) = liveData(Dispatchers.IO) {
        emit(Resource(ResourceState.LOADING, data = null))
        try {
            repository.deleteTodo(todoEntity)
            emit(Resource(ResourceState.SUCCESS, data = "Todo deleted successfully"))
        } catch (exception: Exception) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    data = null,
                    message = exception.message ?: "Unknown error"
                )
            )
        } catch (exception: IOException) {
            Resource(
                ResourceState.ERROR,
                data = null,
                message = exception.message ?: "Unknown error"
            )
        }
    }
}