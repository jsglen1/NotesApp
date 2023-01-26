package com.example.notesapp.addTasks.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun taskDao():TaskDao
}