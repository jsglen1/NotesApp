package com.example.notesapp.addTasks.data

import android.util.Log
import com.example.notesapp.addTasks.data.dataBaseRoom.dao.TaskDao
import com.example.notesapp.addTasks.data.dataBaseRoom.entitys.TaskEntity
import com.example.notesapp.addTasks.data.fireBase.taskModel.TaskModelFireBase
import com.example.notesapp.addTasks.data.fireBase.taskModel.toUiTaskModel
import com.example.notesapp.addTasks.ui.model.TaskModel
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskList: CollectionReference,
    //private val db: FirebaseFirestore
) {


    //Room Crud

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


    //FireBase Crud

    suspend fun getTaskFireBase(): Flow<List<TaskModel>> = withContext(Dispatchers.IO)
    {

        val response: List<TaskModel> = taskList.get().await().map { document ->
            document.toObject(TaskModelFireBase::class.java).toUiTaskModel()
        }

        val flowList: Flow<List<TaskModel>> = flow {
            while (true) {
                emit(response) // Emits the result of the request to the flow
                delay(1000) // Suspends the coroutine for some time
            }
        }
        Log.i("Lista", "son [${response.map { it.task }}]")
        //Log.i("Flow","son [${flowList.collect{its -> its.map { it.task }}}]")
        flowList
    }

    val tasksApi: Flow<List<TaskModel>> = flow {
        while (true) {
            emit(getTaskFireBase()  ) // Emits the result of the request to the flow
            delay(1000) // Suspends the coroutine for some time
        }
    }



    fun addTaskFirebase(taskModel: TaskModel) {
        try {
            taskList.document(taskModel.toTaskFireBase().id.toString())
                .set(taskModel.toTaskFireBase())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteTaskFireBase(taskModel: TaskModel) {
        try {
            taskList.document(taskModel.toTaskFireBase().id.toString())
                .delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun updateTaskFireBase(taskModel: TaskModel) {
        try {
            taskList.document(taskModel.toTaskFireBase().id.toString())
                .set(taskModel.toTaskFireBase())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}

//convierte de taskModel a TaskEntity
fun TaskModel.toTaskEntity(): TaskEntity {
    return TaskEntity(this.id, this.task, this.selected)
}

//convierte de taskModel a taskModelFireBase
fun TaskModel.toTaskFireBase(): TaskModelFireBase {
    return TaskModelFireBase(this.id, this.task, this.selected)
}
