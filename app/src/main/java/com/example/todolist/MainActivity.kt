package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter: ToDoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoAdapter=ToDoAdapter(mutableListOf())
        recyclerView.adapter=todoAdapter
        recyclerView.layoutManager=LinearLayoutManager(this);
        addItem.setOnClickListener {
            val inputtedItem = inputItem.text.toString()
            if (inputtedItem.isNotEmpty()) {
                val todo = ToDoItem(inputtedItem)
                todoAdapter.addTodoItem(todo)
                inputItem.text.clear();
            }
        }
        deleteItem.setOnClickListener{
            todoAdapter.deleteTodoItems();
        }
    }
}