package com.example.todolist

import android.app.Application
import com.example.todolist.dao.AppDatabase

class ToDoListApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}

