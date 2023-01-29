package com.example.notesapp.addTasks.data

import com.example.notesapp.addTasks.data.dataBaseRoom.dao.TaskDao
import com.example.notesapp.addTasks.data.dataBaseRoom.entitys.TaskEntity
import com.example.notesapp.addTasks.data.fireBase.taskModel.taskModelFireBase
import com.example.notesapp.addTasks.ui.model.TaskModel
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskList: CollectionReference
) {

    val tasks: Flow<List<TaskModel>> =
        taskDao.getTasks().map { items -> items.map { TaskModel(it.id, it.task, it.selected) } }

    suspend fun addTask(taskModel: TaskModel) {
        taskDao.addTask(taskModel.toTaskEntity())
    }

    suspend fun update(taskModel: TaskModel) {
        taskDao.updateTask(taskModel.toTaskEntity())
    }

    suspend fun delete(taskModel: TaskModel) {
        taskDao.deleteTask(taskModel.toTaskEntity())
    }

    suspend fun addTaskFirebase(taskModel: TaskModel) {
        try {
            taskList.document(taskModel.toTaskFireBase().id.toString()).set(taskModel.toTaskFireBase())
        } catch (e: Exception){
            e.printStackTrace()
        }
        //taskDao.addTask(taskModel.toTaskFireBase())
    }



}

//convierte de taskModel a TaskEntity
fun TaskModel.toTaskEntity(): TaskEntity {
    return TaskEntity(this.id, this.task, this.selected)
}

//convierte de taskModel a taskModelFireBase
fun TaskModel.toTaskFireBase(): taskModelFireBase {
    return taskModelFireBase(this.id, this.task, this.selected)
}
