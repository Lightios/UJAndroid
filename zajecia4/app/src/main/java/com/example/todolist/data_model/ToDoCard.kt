package com.example.todolist.data_model

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoItem(
    @PrimaryKey val id: Int,

    @NonNull
    @ColumnInfo(name = "name")
    val name: String,

    @NonNull
    @ColumnInfo(name = "description")
    val description: String,

    @NonNull
    @ColumnInfo(name = "date")
    val date: String,

    @NonNull
    @ColumnInfo(name = "status")
    val status: String
)


class ToDoViewModel : ViewModel() {

}