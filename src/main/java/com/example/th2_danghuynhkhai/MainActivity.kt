package com.example.th2_danghuynhkhai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskApp()
        }
    }
}

@Composable
fun TaskApp(viewModel: TaskViewModel = TaskViewModel()) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                ),
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Task App",
                        style = MaterialTheme.typography.h6.copy(fontSize = 30.sp)
                    )
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { navController.popBackStack() }) {
                        Text(text = "Quay lại")
                    }
                    Button(
                        onClick = { ApiService.fetchTasks(viewModel.taskList) }
                    ) {
                        Text(text = "Load Tasks")
                    }
                }

                NavHost(navController = navController, startDestination = "taskList") {
                    composable("taskList") {
                        TaskListScreen(viewModel, navController)
                    }
                    composable("details/{taskId}") { backStackEntry ->
                        val taskId = backStackEntry.arguments?.getString("taskId")
                        val task = viewModel.taskList.value.find { it.id.toString() == taskId }
                        if (task != null) {
                            DetailScreen(task, navController)
                        } else {
                            Text("Task not found", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun TaskListScreen(viewModel: TaskViewModel, navController: NavController) {
    val taskList = viewModel.taskList.value

    if (taskList.isEmpty()) {
        EmptyView()
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(taskList) { task ->
                TaskItem(task) {
                    navController.navigate("details/${task.id}")
                }
            }
        }
    }
}

@Composable
fun DetailScreen(task: TaskModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Tiêu đề nhiệm vụ
        Text(
            text = task.title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E88E5)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Mô tả nhiệm vụ
        Text(
            text = task.description,
            fontSize = 18.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Trạng thái nhiệm vụ
        Text(
            text = "Status: ${task.status}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = if (task.status == "Completed") Color(0xFF4CAF50) else Color(0xFFE53935)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Độ ưu tiên
        Text(
            text = "Priority: ${task.priority}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = when (task.priority) {
                "High" -> Color(0xFFD32F2F)
                "Medium" -> Color(0xFFFFA000)
                else -> Color(0xFF388E3C)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Danh mục nhiệm vụ
        Text(
            text = "Category: ${task.category}",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            color = Color(0xFF673AB7) // Màu tím
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Ngày đến hạn
        Text(
            text = "Due Date: ${task.dueDate}",
            fontSize = 16.sp,
            color = Color(0xFF616161)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Ngày tạo và cập nhật
        Row {
            Text(
                text = "Created: ${task.createdAt}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Updated: ${task.updatedAt}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Danh sách công việc con
        if (task.subtasks.isNotEmpty()) {
            Text(
                text = "Subtasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF00796B) // Xanh lục đậm
            )
            Spacer(modifier = Modifier.height(8.dp))

            task.subtasks.forEach { subtask ->
                Text(
                    text = "- ${subtask.title} (✅ ${if (subtask.isCompleted) "Done" else "Pending"})",
                    fontSize = 16.sp,
                    color = if (subtask.isCompleted) Color(0xFF4CAF50) else Color(0xFFE53935) // Xanh nếu xong, đỏ nếu chưa
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Danh sách tài liệu đính kèm
        if (task.attachments.isNotEmpty()) {
            Text(
                text = "Attachments",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3F51B5) // Xanh dương đậm
            )
            Spacer(modifier = Modifier.height(8.dp))

            task.attachments.forEach { attachment ->
                Text(
                    text = "📎 ${attachment.fileName}",
                    fontSize = 16.sp,
                    color = Color(0xFF5C6BC0) // Xanh lam
                )
            }
        }
    }
}

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(160.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.no_data),
                contentDescription = "No Tasks",
                modifier = Modifier.size(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No Tasks Yet!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color.Gray
        )

        Text(
            text = "Stay productive—add something to do",
            fontSize = 16.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}