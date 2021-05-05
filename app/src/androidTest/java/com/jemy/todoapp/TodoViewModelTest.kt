package com.jemy.todoapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jemy.todoapp.data.entity.TodoEntity
import com.jemy.todoapp.data.repository.AddTodoRepository
import com.jemy.todoapp.data.repository.TodoListRepository
import com.jemy.todoapp.data.room.TodoDatabase
import com.jemy.todoapp.ui.fragments.addtodo.AddTodoViewModel
import com.jemy.todoapp.ui.fragments.todolist.TodoListViewModel
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TodoViewModelTest : TestCase() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var todoDatabase: TodoDatabase
    private lateinit var viewModel: TodoListViewModel
    private lateinit var addTodoViewModel: AddTodoViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        todoDatabase = Room.inMemoryDatabaseBuilder(
            context, TodoDatabase::class.java
        ).allowMainThreadQueries().build()
        val repository = TodoListRepository(todoDatabase.todoDao())
        val addRepository = AddTodoRepository(todoDatabase.todoDao())
        viewModel = TodoListViewModel(repository)
        addTodoViewModel = AddTodoViewModel(addRepository)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        todoDatabase.close()
    }

    @Test
    fun testGetTodoList() {
        val list = mutableListOf<TodoEntity>()
         addTodoViewModel.addTodo(title = "Hello", body = "todo app", time = "03:30")
        viewModel.getTodoList().value?.data?.let { list.addAll(it) }
        assertNotNull(list)
    }
}





