package com.example.todolist.dao

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todolist.data_model.ToDoItem

@Dao
interface ToDoCardDao {
    @Query("SELECT * FROM ToDoItem")
    fun getAll(): List<ToDoItem>

//    @Query("SELECT * FROM ToDoCard WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<ToDoCard>

//    @Insert
//    fun insertAll(vararg users: ToDoCard)
//
//    @Delete
//    fun delete(user: ToDoCard)
}


@Database(entities = [ToDoItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toDoCardDao(): ToDoCardDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .allowMainThreadQueries()
                    .createFromAsset("database/database.db")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }


}


