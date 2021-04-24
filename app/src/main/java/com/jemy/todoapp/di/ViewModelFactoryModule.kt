package com.jemy.todoapp.di


import com.jemy.todoapp.data.repository.AddTodoRepository
import com.jemy.todoapp.data.repository.TodoListRepository
import com.jemy.todoapp.ui.fragments.addtodo.AddTodoViewModelFactory
import com.jemy.todoapp.ui.fragments.todolist.TodoListViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun getTodoListViewModelFactory(
        repository: TodoListRepository
    ): TodoListViewModelFactory = TodoListViewModelFactory(repository)

    @Provides
    @Singleton
    fun getAddTodoViewModelFactory(
        repository: AddTodoRepository
    ): AddTodoViewModelFactory = AddTodoViewModelFactory(repository)
}