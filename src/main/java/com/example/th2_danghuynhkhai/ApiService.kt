package com.example.th2_danghuynhkhai

import android.util.Log
import androidx.compose.runtime.MutableState
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(TaskApi::class.java)

    fun fetchTasks(taskListState: MutableState<List<TaskModel>>) {
        api.getTasks().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val body = response.body()
                if (response.isSuccessful && body?.isSuccess == true) {
                    taskListState.value = body.data
                } else {
                    handleError("Response unsuccessful: ${body?.message ?: response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                handleError("API Failure: ${t.localizedMessage}")
            }
        })
    }

    private fun handleError(message: String) {
        Log.e("API", message)
    }
}