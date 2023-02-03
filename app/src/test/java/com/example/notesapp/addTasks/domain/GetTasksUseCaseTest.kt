package com.example.notesapp.addTasks.domain

import com.example.notesapp.addTasks.data.TaskRepository
import com.example.notesapp.addTasks.domain.useCase.GetTasksUseCase
import com.example.notesapp.addTasks.ui.model.TaskModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class GetTasksUseCaseTest{

    @RelaxedMockK
    private lateinit var taskRepository: TaskRepository

    lateinit var getTasksUseCase: GetTasksUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getTasksUseCase = GetTasksUseCase(taskRepository)
    }

    @Test
    fun `when the getTaskUseCase call data TaskRepository them  not null`() = runBlocking {

        val task = TaskModel(18,"funiona?",false)

        //given
        coEvery { taskRepository.addTask(task) }

        //when
        val rep = getTasksUseCase()
        //Log.i("test", rep.toString())

        //them
        //coVerify { rep.collect{ it == rep } }
        assert(rep != null)

    }

}
