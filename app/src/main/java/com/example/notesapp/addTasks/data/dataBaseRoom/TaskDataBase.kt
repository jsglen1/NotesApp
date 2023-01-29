package com.example.notesapp.addTasks.data.dataBaseRoom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.addTasks.data.dataBaseRoom.dao.TaskDao
import com.example.notesapp.addTasks.data.dataBaseRoom.entitys.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}