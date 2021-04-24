package com.jemy.todoapp.ui.fragments.todolist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jemy.todoapp.R
import com.jemy.todoapp.data.entity.TodoEntity
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter : RecyclerView.Adapter<TodoViewHolder>() {

    private var itemCallback: ((TodoEntity?) -> Unit)? = null
    var items = mutableListOf<TodoEntity>()

    fun addItems(items: List<TodoEntity>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view, itemCallback)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val popular = items[position]
        holder.bind(popular)
    }

    fun setItemCallBack(itemCallback: (TodoEntity?) -> Unit) {
        this.itemCallback = itemCallback
    }

    override fun getItemCount(): Int = items.size
}

class TodoViewHolder(
    itemView: View,
    private val itemCallback: ((TodoEntity?) -> Unit)?
) : RecyclerView.ViewHolder(itemView) {

    private var title = itemView.titleTextView
    private var body = itemView.bodyTextView
    private var time = itemView.timeTextView

    fun bind(todo: TodoEntity?) {
        itemView.setOnClickListener { itemCallback?.invoke(todo) }
        title.text = todo?.title
        body.text = todo?.body
        time.text = todo?.time
    }
}
