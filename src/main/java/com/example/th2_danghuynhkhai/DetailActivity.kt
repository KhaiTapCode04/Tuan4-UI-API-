package com.example.th2_danghuynhkhai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskTitle = intent.getStringExtra("TASK_TITLE") ?: "No Title"
        val taskDescription = intent.getStringExtra("TASK_DESCRIPTION") ?: "No Description"

        setContent {
            DetailScreen(taskTitle, taskDescription)
        }
    }
}

@Composable
fun DetailScreen(title: String, description: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = description, fontSize = 18.sp)
    }
}