package com.example.notesapp.addTasks.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.addTasks.domain.useCase.AddTaskUseCase
import com.example.notesapp.addTasks.domain.useCase.DeleteTaskUseCase
import com.example.notesapp.addTasks.domain.useCase.GetTasksUseCase
import com.example.notesapp.addTasks.domain.useCase.UpdateTaskUseCase
import com.example.notesapp.addTasks.domain.useCaseApi.AddTaskApiUseCase
import com.example.notesapp.addTasks.domain.useCaseApi.DeleteTaskApiUseCase
import com.example.notesapp.addTasks.domain.useCaseApi.GetTasksApiUseCase
import com.example.notesapp.addTasks.domain.useCaseApi.UpdateTaskApiUseCase
import com.example.notesapp.addTasks.ui.TaskUiState.*
import com.example.notesapp.addTasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase,
    private val addTaskApiUseCase: AddTaskApiUseCase,
    private val deleteTaskApiUseCase: DeleteTaskApiUseCase,
    private val updateTaskApiUseCase: UpdateTaskApiUseCase,
    getTasksApiUseCase: GetTasksApiUseCase
) : ViewModel() {

    val uiState: StateFlow<TaskUiState> =
        getTasksApiUseCase().map(::Success)
            .catch { Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), Loading)




    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog


    fun onDialogClose() {
        _showDialog.value = false
    }

    fun onTaskCreated(text: String) {
        Log.i("glen", text)
        viewModelScope.launch {
            //addTaskUseCase(TaskModel(task = text))
            addTaskApiUseCase(TaskModel(task = text)) // a??ade a firebase new line
        }
        onDialogClose()
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        viewModelScope.launch {
            //updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
            updateTaskApiUseCase(taskModel.copy(selected = !taskModel.selected))

        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        viewModelScope.launch {
            //deleteTaskUseCase(taskModel)
            deleteTaskApiUseCase(taskModel)

        }

    }
}