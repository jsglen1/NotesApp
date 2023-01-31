package com.example.notesapp.addTasks.data.fireBase

import com.example.notesapp.addTasks.ui.model.TaskModel

data class ModelTask(
    val id: Int,
    val task: String,
    var selected: Boolean
)
{
    constructor(): this(id=0,task = "",selected = false)
}




//convierte de ModelTask a TaskModel UI
fun ModelTask.toTaskModel(): TaskModel {
    return TaskModel(this.id, this.task, this.selected)
}