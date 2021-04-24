package com.jemy.todoapp.ui.fragments.todolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jemy.todoapp.R
import com.jemy.todoapp.data.entity.TodoEntity
import com.jemy.todoapp.ui.MainActivity
import com.jemy.todoapp.ui.fragments.todolist.adapter.TodoAdapter
import com.jemy.todoapp.utils.Constants
import com.jemy.todoapp.utils.ResourceState
import com.jemy.todoapp.utils.extension.gone
import com.jemy.todoapp.utils.extension.messageDialog
import com.jemy.todoapp.utils.extension.toastLong
import com.jemy.todoapp.utils.extension.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todo_list.*
import javax.inject.Inject

@AndroidEntryPoint
class TodoListFragment : Fragment(R.layout.fragment_todo_list) {

    @Inject
    lateinit var viewModelFactory: TodoListViewModelFactory
    private val viewModel: TodoListViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(TodoListViewModel::class.java)
    }

    private val todoAdapter by lazy { TodoAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupFloatButtonClickListener()
        getTodoList()
        setSwipeLayoutAction()
        swipeToDelete()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).supportActionBar?.title = "Todo List"
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun getTodoList() {
        viewModel.getTodoList().observe(viewLifecycleOwner) { resources ->
            when (resources.state) {
                ResourceState.LOADING -> {
                    todoSwipeLayout.isRefreshing = true
                }
                ResourceState.SUCCESS -> {
                    todoSwipeLayout.isRefreshing = false
                    resources.data?.let { todoList ->
                        if (!todoList.isNullOrEmpty()) {
                            todoRecycler.visible()
                            addFirstTodoTextView.gone()
                            setupTodoListAdapter(todoList)
                        } else {
                            todoRecycler.gone()
                            addFirstTodoTextView.visible()
                        }

                    }
                }
                ResourceState.ERROR -> {
                    todoRecycler.gone()
                    addFirstTodoTextView.visible()
                    todoSwipeLayout.isRefreshing = false
                    resources.message?.let { messageDialog(message = it)?.show() }
                }
            }
        }
    }

    private fun setupTodoListAdapter(todoList: List<TodoEntity>) {
        todoRecycler.adapter = todoAdapter
        todoAdapter.addItems(todoList)
        todoAdapter.setItemCallBack { todo ->
            val bundle = bundleOf(Constants.TODO to todo)
            view?.findNavController()
                ?.navigate(R.id.action_todoListFragment_to_addTodoFragment, bundle)
        }
    }

    private fun setSwipeLayoutAction() {
        todoSwipeLayout.setOnRefreshListener {
            todoAdapter.clear()
            getTodoList()
        }
    }

    private fun deleteTodo(todoEntity: TodoEntity) {
        viewModel.deleteTodo(todoEntity).observe(viewLifecycleOwner) { resources ->
            when (resources.state) {
                ResourceState.LOADING -> {
                    todoSwipeLayout.isRefreshing = true
                }
                ResourceState.SUCCESS -> {
                    todoSwipeLayout.isRefreshing = false
                    resources.data?.let { message ->
                        toastLong(message)
                        getTodoList()
                    }
                }
                ResourceState.ERROR -> {
                    todoRecycler.gone()
                    addFirstTodoTextView.visible()
                    todoSwipeLayout.isRefreshing = false
                    resources.message?.let { messageDialog(message = it)?.show() }
                }
            }
        }
    }

    private fun swipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTodo(todoAdapter.items[viewHolder.adapterPosition])
            }
        }).attachToRecyclerView(todoRecycler)
    }

    private fun setupFloatButtonClickListener() {
        addFloatButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_todoListFragment_to_addTodoFragment)
        }
    }

}