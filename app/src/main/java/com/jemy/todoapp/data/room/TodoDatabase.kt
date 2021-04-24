package com.jemy.todoapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jemy.todoapp.data.entity.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        const val DATABASE_NAME = "todo_db"
    }
}