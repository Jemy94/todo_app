package com.jemy.todoapp.data.repository

import com.jemy.todoapp.data.entity.TodoEntity
import com.jemy.todoapp.data.room.TodoDao
import javax.inject.Inject

class AddTodoRepository @Inject constructor(
    private val todoDoa: TodoDao
) {

    suspend fun addTodo(todoEntity: TodoEntity) = todoDoa.insertTodo(todoEntity)

    suspend fun updateTodo(todoEntity: TodoEntity) = todoDoa.updateTodo(todoEntity)

}