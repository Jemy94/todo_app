package com.jemy.todoapp.data.repository

import com.jemy.todoapp.data.entity.TodoEntity
import com.jemy.todoapp.data.room.TodoDao
import javax.inject.Inject

class TodoListRepository @Inject constructor(
    private val todoDoa: TodoDao
) {

    suspend fun getTodoList(): List<TodoEntity> = todoDoa.getTodoList()

    suspend fun deleteTodo(todoEntity: TodoEntity) = todoDoa.deleteTodo(todoEntity)
}