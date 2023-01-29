package com.example.notesapp.addTasks.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.notesapp.addTasks.ui.model.TaskModel

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(initial = false)

    val uiState by produceState<TaskUiState>(
        initialValue = TaskUiState.Loading,
        key1 = lifecycle,
        key2 = taskViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            taskViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is TaskUiState.Error -> {}
        TaskUiState.Loading -> {
            CircularProgressIndicator()
        }
        is TaskUiState.Success -> {

            Box(Modifier.fillMaxSize()) {

                AddTaskDialog(
                    showDialog,
                    onDismiss = { taskViewModel.onDialogClose() },
                    onTaskAdded = { taskViewModel.onTaskCreated(it) })




                Column(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {


                    Text(
                        text = "Notas Aleatorias",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )



                    Spacer(modifier = Modifier.size(8.dp))

                    TasksList(
                        (uiState as TaskUiState.Success).tasks,
                        taskViewModel,
                        Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    FabDialog(
                        modifier = Modifier, taskViewModel
                    )

                }


            }

        }
    }


}

@Composable
fun TasksList(tasks: List<TaskModel>, taskViewModel: TaskViewModel, modifier: Modifier) {

    LazyColumn(modifier = modifier) {
        items(tasks, key = { it.id }) {
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
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
                onCheckedChange = { taskViewModel.onCheckBoxSelected(taskModel) })
        }

    }


}

@Composable
fun FabDialog(modifier: Modifier, taskViewModel: TaskViewModel) {

    Row() {

        Text(
            text = "para borrar mantener presionado ",
            modifier = Modifier.weight(1f),
            color = Color.LightGray
        )

        FloatingActionButton(
            onClick = { taskViewModel.onShowDialogClick() },
            //modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "")
        }



    }


}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }

    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {

            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(2.dp, Color.Black),
                elevation = 6.dp
            ) {


                Column(
                    Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {

                    Text(
                        text = "Añade tu tarea",
                        fontSize = 18.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        singleLine = true,
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray
                        )
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
}
