package com.example.th2_danghuynhkhai

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    val taskList: MutableState<List<TaskModel>> = mutableStateOf(emptyList())

    fun updateTaskList(newTasks: List<TaskModel>) {
        taskList.value = newTasks
    }
}