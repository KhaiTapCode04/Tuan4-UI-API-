package com.example.th2_danghuynhkhai

data class Subtask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean
)

data class Attachment(
    val id: Int,
    val fileName: String,
    val fileUrl: String
)

data class Reminder(
    val id: Int,
    val time: String,
    val type: String
)

data class TaskModel(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val priority: String,
    val category: String,
    val dueDate: String,
    val createdAt: String,
    val updatedAt: String,
    val subtasks: List<Subtask>,
    val attachments: List<Attachment>,
    val reminders: List<Reminder>
)

data class ApiResponse(
    val isSuccess: Boolean,
    val message: String,
    val data: List<TaskModel>
)