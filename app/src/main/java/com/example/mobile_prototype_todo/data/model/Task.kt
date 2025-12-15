package com.example.mobile_prototype_todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val title: String,
    val completed: Boolean = false,
    val isFromApi: Boolean = false // Para diferenciar tarefas da API das locais
)
