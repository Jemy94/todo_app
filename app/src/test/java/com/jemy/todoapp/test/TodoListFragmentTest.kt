package com.jemy.todoapp.test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jemy.todoapp.data.entity.TodoEntity
import com.jemy.todoapp.ui.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(AndroidJUnit4::class)
class TodoListFragmentTest {
    @get :Rule
    val activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    val lisItemInTest = 4
    val todoList = getTodoList()

    @JvmName("getTodoList1")
    fun getTodoList(): List<TodoEntity> {
        val list = mutableListOf<TodoEntity>()
        val todo = TodoEntity(id = 1, title = "Hello", body = "todo app", time = "03:30")
        list.add(todo)
        list.add(todo)
        list.add(todo)
        list.add(todo)
        list.add(todo)
        return list
    }

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun onViewCreated() {
    }

    @Test
    fun onCreateOptionsMenu() {
    }

    @Test
    fun onOptionsItemSelected() {
    }
}