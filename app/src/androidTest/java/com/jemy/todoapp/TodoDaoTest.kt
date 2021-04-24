package com.jemy.todoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jemy.todoapp.data.entity.TodoEntity
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class TodoDaoTest : DatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertTodoTest() {
        runBlocking {
            val todo = TodoEntity(id = 1, title = "Hello", body = "todo app", time = "03:30")
            todoDatabase.todoDao().insertTodo(todo)
            val todoSize = todoDatabase.todoDao().getTodoList().size
            assertEquals(todoSize, 1)
        }
    }

    @Test
    fun deleteTodoTest() {
        runBlocking {
            val todo = TodoEntity(id = 1, title = "Hello", body = "todo app", time = "03:30")
            todoDatabase.todoDao().insertTodo(todo)
            assertEquals(todoDatabase.todoDao().getTodoList().size, 1)
            todoDatabase.todoDao().deleteTodo(todo)
            assertEquals(todoDatabase.todoDao().getTodoList().size, 0)
        }
    }

    @Test
    fun updateTodoTest() {
        runBlocking {
            val todo = TodoEntity(id = 1, title = "Hello", body = "todo app", time = "03:30")
            todoDatabase.todoDao().insertTodo(todo)
            todo.title = "Hi"
            todoDatabase.todoDao().updateTodo(todo)
            assertEquals(todoDatabase.todoDao().getTodoList()[0].title, "Hi")
        }
    }
}