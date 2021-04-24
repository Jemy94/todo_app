package com.jemy.todoapp.ui.fragments.addtodo

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jemy.todoapp.R
import com.jemy.todoapp.data.entity.TodoEntity
import com.jemy.todoapp.ui.MainActivity
import com.jemy.todoapp.utils.AlarmReceiver
import com.jemy.todoapp.utils.Constants
import com.jemy.todoapp.utils.ResourceState
import com.jemy.todoapp.utils.extension.messageDialog
import com.jemy.todoapp.utils.extension.toastLong
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_todo.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddTodoFragment : Fragment(R.layout.fragment_add_todo) {

    @Inject
    lateinit var viewModelFactory: AddTodoViewModelFactory
    private val viewModel: AddTodoViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(AddTodoViewModel::class.java)
    }

    private val todo: TodoEntity? by lazy { arguments?.getParcelable(Constants.TODO) }
    private lateinit var timePicker: TimePickerDialog
    private val currentTime = Calendar.getInstance()
    private val hour = currentTime.get(Calendar.HOUR_OF_DAY)
    private val minute = currentTime.get(Calendar.MINUTE)
    private var selectedTime = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        getTodoData()
        setupSelectTimeClickListener()
        setupSaveButtonClickListener()
    }

    private fun setupToolbar() {
        (requireActivity() as MainActivity).supportActionBar?.title = "Add Todo"
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getTodoData() {
        todo?.let { todo ->
            titleEditText.setText(todo.title)
            bodyEditText.setText(todo.body)
            selectTimeTextView.text = todo.time
            selectedTime = todo.time
        }
    }

    private fun addTodo() {
        val title = titleEditText.text.toString()
        val body = bodyEditText.text.toString()
        viewModel.addTodo(title = title, body = body, time = selectedTime)
            .observe(viewLifecycleOwner) { resources ->
                when (resources.state) {
                    ResourceState.LOADING -> {
                    }
                    ResourceState.SUCCESS -> {
                        resources.data?.let { message ->
                            toastLong(message)
                            requireActivity().onBackPressed()
                        }
                    }
                    ResourceState.ERROR -> {
                        resources.message?.let { messageDialog(message = it)?.show() }
                    }
                }
            }
    }

    private fun updateTodo() {
        val title = titleEditText.text.toString()
        val body = bodyEditText.text.toString()
        todo?.let { todo ->
            todo.title = title
            todo.body = body
            todo.time = selectedTime
            viewModel.updateTodo(todo).observe(viewLifecycleOwner) { resources ->
                when (resources.state) {
                    ResourceState.LOADING -> {
                    }
                    ResourceState.SUCCESS -> {
                        resources.data?.let { message ->
                            toastLong(message)
                            requireActivity().onBackPressed()
                        }
                    }
                    ResourceState.ERROR -> {
                        resources.message?.let { messageDialog(message = it)?.show() }
                    }
                }
            }
        }
    }

    private fun setupSaveButtonClickListener() {
        saveButton.setOnClickListener {
            if (todo != null) {
                updateTodo()
            } else {
                addTodo()
            }

        }
    }

    private fun setupSelectTimeClickListener() {
        selectTimeTextView.setOnClickListener {
            timePicker = TimePickerDialog(
                requireActivity(),
                { _, hourOfDay, minute ->
                    val calender = Calendar.getInstance()
                    calender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calender.set(Calendar.MINUTE, minute)
                    calender.set(Calendar.SECOND, 0)
                    calender.add(Calendar.MINUTE, -10)
                    setupAlarm(calender)
                    selectTimeTextView.text = String.format("%d : %d", hourOfDay, minute)
                    selectedTime = String.format("%d : %d", hourOfDay, minute)
                }, hour, minute, false
            )
            timePicker.show()
        }
    }

    private fun setupAlarm(calendar: Calendar) {
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireActivity(), AlarmReceiver::class.java)
        intent.putExtra(Constants.TITLE, titleEditText.text.toString())
        intent.putExtra(Constants.BODY, bodyEditText.text.toString())
        val pendingIntent = PendingIntent.getBroadcast(requireActivity(), 1, intent, 0)
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}