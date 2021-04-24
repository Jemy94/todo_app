package com.jemy.todoapp.data.room

import androidx.room.*
import com.jemy.todoapp.data.entity.TodoEntity

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(vararg todoEntity: TodoEntity)

    @Query("SELECT * FROM todo")
    suspend fun getTodoList(): List<TodoEntity>

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)

    @Update
    suspend fun updateTodo(todoEntity: TodoEntity)

}