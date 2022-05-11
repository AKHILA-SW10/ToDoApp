package com.example.todolist
import android.content.ContentValues
import  android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class Db (context: Context) : SQLiteOpenHelper(context,"TodoDb.db",null,1){
    var tableName:String="TodoDb"
    override fun onCreate(db: SQLiteDatabase?) {
//        db!!.execSQL("DROP TABLE IF EXISTS $tableName")
        val createCmd="CREATE TABLE $tableName ( tasks text, status text, id Int)";
        db?.execSQL(createCmd);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun updateDt(task : ToDoItem):Int{
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put("status",task.isChecked.toString())
        val response=db.update("$tableName",contentValues, "id=${task.id}",null)
        db.close()
        return response;
    }

    fun insertDt(task: ToDoItem):Long{
        val db=this.writableDatabase
        val contentValues=ContentValues()
        contentValues.put("tasks",task.title)
        contentValues.put("status",task.isChecked.toString())
        contentValues.put("id",task.id)
        val success=db.insert("$tableName",null, contentValues);
        db.close()
        return success
    }

    fun deleteItem(task:ToDoItem) :Int{
        val db=this.writableDatabase;
        var result=db.delete("$tableName","id=${task.id}",null)
        db.close()
        return result
    }

    fun getData():ArrayList<ToDoItem>{
        var allTasks:ArrayList<ToDoItem> = ArrayList()
        val selectCmd="SELECT * FROM $tableName"
        var db=this.readableDatabase
        var cursor: Cursor?
        try{
            cursor=db.rawQuery(selectCmd,null);
        }catch(e: Exception){
            e.printStackTrace();
            db.execSQL(selectCmd)
            return allTasks
        }
        var tasks: String
        var status: Boolean
        if(cursor.moveToFirst()){
            do{
                var taskIndex:Int=cursor.getColumnIndex("tasks")
                var idIndex:Int=cursor.getColumnIndex("id")
                var statusIndex=cursor.getColumnIndex("status")
                if(taskIndex<0 || idIndex <0 || statusIndex<0) return allTasks

                if(cursor.getString(statusIndex).equals("false")) {
                    status=false
                }
                else{
                    status=true
                }
                allTasks.add(ToDoItem(cursor.getString(taskIndex),status,cursor.getInt(idIndex)))
            }while(cursor.moveToNext())
        }
        db.close()
        return allTasks
    }

}
