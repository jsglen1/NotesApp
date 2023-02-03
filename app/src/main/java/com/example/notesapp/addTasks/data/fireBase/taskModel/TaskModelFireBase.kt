package com.example.notesapp.addTasks.data.fireBase.taskModel

import com.example.notesapp.addTasks.ui.model.TaskModel

data class TaskModelFireBase(
    val id: Int,
    val task: String,
    var selected: Boolean
){ constructor() : this(id=0, task = "",selected = false) }


//convierte de taskModelFireBase a modelo UiTaskModel
fun TaskModelFireBase.toUiTaskModel(): TaskModel {
    return TaskModel(this.id, this.task, this.selected)
}