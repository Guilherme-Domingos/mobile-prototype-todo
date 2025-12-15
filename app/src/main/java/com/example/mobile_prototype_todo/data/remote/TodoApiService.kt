package com.example.mobile_prototype_todo.data.remote

import com.example.mobile_prototype_todo.data.remote.model.TodoApiResponse
import retrofit2.http.GET

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<TodoApiResponse>
    
    @GET("todos?userId=1")
    suspend fun getTodosByUser(): List<TodoApiResponse>
}
