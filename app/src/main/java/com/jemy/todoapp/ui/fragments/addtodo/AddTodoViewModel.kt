package com.jemy.todoapp.ui.fragments.addtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jemy.todoapp.data.entity.TodoEntity
import com.jemy.todoapp.data.repository.AddTodoRepository
import com.jemy.todoapp.utils.Resource
import com.jemy.todoapp.utils.ResourceState
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class AddTodoViewModel(private val repository: AddTodoRepository) : ViewModel() {

    fun addTodo(title: String, body: String, time: String) = liveData(Dispatchers.IO) {
        if (title.isBlank() || body.isBlank() || time.isBlank()) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    data = null,
                    message = "Please fill the empty data"
                )
            )
        } else {
            emit(Resource(ResourceState.LOADING, data = null))
            try {
                val todo = TodoEntity(title = title, body = body, time = time)
                repository.addTodo(todo)
                emit(Resource(ResourceState.SUCCESS, data = "Todo added successfully"))
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

    fun updateTodo(todoEntity: TodoEntity) = liveData(Dispatchers.IO) {
        if (todoEntity.title.isBlank() || todoEntity.body.isBlank() || todoEntity.time.isBlank()) {
            emit(
                Resource(
                    ResourceState.ERROR,
                    data = null,
                    message = "Please fill the empty data"
                )
            )
        } else {
            emit(Resource(ResourceState.LOADING, data = null))
            try {
                repository.updateTodo(todoEntity)
                emit(Resource(ResourceState.SUCCESS, data = "Todo updated successfully"))
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
}