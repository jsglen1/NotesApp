package com.example.notesapp.addTasks.ui

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.addTasks.domain.AddTaskToFireBaseUseCase
import com.example.notesapp.addTasks.domain.GetTasksToFireBaseUseCase
import com.example.notesapp.addTasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskToFireBaseUseCase: AddTaskToFireBaseUseCase,
    private val getTasksToFireBaseUseCase: GetTasksToFireBaseUseCase
) : ViewModel() {

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    private val _tasks = mutableStateListOf<TaskModel>()
    val tasks: List<TaskModel> = _tasks

    /*
    fun getTaskFromFireBase() {
        viewModelScope.launch {
            //_tasks.removeAll(_tasks) // libera el buffer view tareas
            //_tasks.addAll(getTasksToFireBaseUseCase())
        }
    }

     */


    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(text: String) {

        viewModelScope.launch { addTaskToFireBaseUseCase(TaskModel(task = text)) }

        _tasks.add(TaskModel(task = text))
        Log.i("glen", text)
        onDialogClose()
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBox(taskModel: TaskModel) {
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let { it.copy(selected = !it.selected) }
    }

    fun onItemRemove(taskModel: TaskModel) {
        val task = _tasks.find { it.id == taskModel.id }
        _tasks.remove(task)
    }
}