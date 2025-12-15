package com.example.mobile_prototype_todo.data.remote.model

data class TodoApiResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)
