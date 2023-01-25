package com.example.notesapp.addTasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.notesapp.addTasks.ui.model.TaskModel

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {

    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(initial = false)

    Box(Modifier.fillMaxSize()) {
        AddTaskDialog(
            showDialog,
            onDismiss = { taskViewModel.onDialogClose() },
            onTaskAdded = { taskViewModel.onTaskCreated(it) })
        FabDialog(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp), taskViewModel
        )
        TasksList(taskViewModel)
    }
}

@Composable
fun TasksList(taskViewModel: TaskViewModel) {

    val myTask: List<TaskModel> = taskViewModel.tasks

    LazyColumn {
        items(myTask, key = { it.id }) {
            ItemTask(it, taskViewModel)
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TaskViewModel) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp).pointerInput(Unit){
                detectTapGestures (onLongPress = {
                    taskViewModel.onItemRemove(taskModel)
                })
            },
        elevation = 8.dp
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = taskModel.task,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { taskViewModel.onCheckBox(taskModel) })
        }

    }


}

@Composable
fun FabDialog(modifier: Modifier, taskViewModel: TaskViewModel) {
    FloatingActionButton(
        onClick = { taskViewModel.onShowDialogClick() },
        modifier = modifier
    ) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onTaskAdded(text)
                    text = ""
                }, Modifier.fillMaxWidth()) {
                    Text(text = "Añadir Tarea")
                }
            }
        }
    }
}
