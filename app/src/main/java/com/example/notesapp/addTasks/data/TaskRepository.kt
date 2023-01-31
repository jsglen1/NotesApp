package com.example.notesapp.addTasks.data

import com.example.notesapp.addTasks.data.fireBase.ModelTask
import com.example.notesapp.addTasks.data.fireBase.toTaskModel
import com.example.notesapp.addTasks.ui.model.TaskModel
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskList: CollectionReference
) {

    fun addTaskFirebase(taskModel: TaskModel) {
        try {
            taskList.document(taskModel.toTaskFireBase().id.toString())
                .set(taskModel.toTaskFireBase())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val tasksList = runBlocking {
        taskList.get().await().map { document ->
            (document.toObject(ModelTask::class.java)).toTaskModel()
        }
    }

}

//convierte de taskModel a ModelTask firebase
fun TaskModel.toTaskFireBase(): ModelTask {
    return ModelTask(this.id, this.task, this.selected)
}


