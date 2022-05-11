package com.example.todolist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoAdapter (private val todoList:MutableList<ToDoItem>, private var db:Db):RecyclerView.Adapter<ToDoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View,private  var db:Db) : RecyclerView.ViewHolder(itemView){
        private val title: TextView = itemView.findViewById(R.id.todoItemTitle)
        private val chkStatus: CheckBox = itemView.findViewById(R.id.todoItemCheckBox)
        fun bind(item: ToDoItem) {
            title.text=item.title
            chkStatus.isChecked=item.isChecked;

            chkStatus.setOnClickListener{
                item.isChecked=chkStatus.isChecked;
                db.updateDt(item);
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false),db);
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position]);
    }

    override fun getItemCount(): Int {
        return todoList.size;
    }

    fun addTodoItem(newItem: ToDoItem){
        newItem.id=todoList.size
        todoList.add(newItem);
        db.insertDt(newItem)
        notifyItemInserted(todoList.size-1);
    }

    fun deleteTodoItems(){
        val tempIndexes:MutableList<ToDoItem> = mutableListOf();
        for(item in todoList){
            if(item.isChecked){
                tempIndexes.add(item)
            }
        }

        for(item in tempIndexes){
            db.deleteItem(item)
            todoList.remove(item)
        }
        notifyDataSetChanged()
    }

}

