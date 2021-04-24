package com.jemy.todoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jemy.todoapp.data.entity.TodoEntity
import com.jemy.todoapp.data.repository.TodoListRepository
import com.jemy.todoapp.ui.fragments.todolist.TodoListViewModel
import com.jemy.todoapp.utils.Resource
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class TodoListViewModelTest :DatabaseTest(){

    @Mock
    private lateinit var viewModel: TodoListViewModel

    @Mock
    private lateinit var repository: TodoListRepository

    @Mock
    private lateinit var todoList: LiveData<Resource<List<TodoEntity>>>

    @Mock
    private lateinit var observer: Observer<in Resource<List<TodoEntity>>>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = spy(TodoListViewModel(repository))
        todoList = viewModel.getTodoList()
    }

    @Test
    fun testGetTodoList() {
        assertNotNull(viewModel.getTodoList())
        viewModel.getTodoList().observeForever(observer)
        verify(observer).onChanged(null)
        viewModel.getTodoList()
        verify(observer).onChanged(todoList.value)
    }


}