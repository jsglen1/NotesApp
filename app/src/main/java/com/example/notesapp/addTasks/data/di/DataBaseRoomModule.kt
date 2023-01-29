package com.example.notesapp.addTasks.data.di

import android.content.Context
import androidx.room.Room
import com.example.notesapp.addTasks.data.dataBaseRoom.dao.TaskDao
import com.example.notesapp.addTasks.data.dataBaseRoom.TaskDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseRoomModule {

    @Provides
    fun provideTaskDao(taskDataBase: TaskDataBase): TaskDao {
        return taskDataBase.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskDataBase(@ApplicationContext appContext: Context): TaskDataBase {
        return Room.databaseBuilder(appContext, TaskDataBase::class.java, "TaskDataBase").build()
    }

}