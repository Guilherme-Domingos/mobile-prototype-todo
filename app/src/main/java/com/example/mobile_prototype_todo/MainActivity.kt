package com.example.mobile_prototype_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.mobile_prototype_todo.data.local.AppDatabase
import com.example.mobile_prototype_todo.data.remote.RetrofitClient
import com.example.mobile_prototype_todo.data.repository.AuthRepository
import com.example.mobile_prototype_todo.data.repository.TaskRepository
import com.example.mobile_prototype_todo.ui.navigation.NavigationGraph
import com.example.mobile_prototype_todo.ui.theme.MobileprototypetodoTheme
import com.example.mobile_prototype_todo.ui.viewmodel.AuthViewModel
import com.example.mobile_prototype_todo.ui.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Inicializar Database
        val database = AppDatabase.getDatabase(applicationContext)
        
        // Inicializar Repositories
        val authRepository = AuthRepository(database.userDao())
        val taskRepository = TaskRepository(
            database.taskDao(),
            RetrofitClient.todoApiService
        )
        
        // Inicializar ViewModels
        val authViewModel = AuthViewModel(authRepository)
        val taskViewModel = TaskViewModel(taskRepository)
        
        setContent {
            MobileprototypetodoTheme {
                val navController = rememberNavController()
                NavigationGraph(
                    navController = navController,
                    authViewModel = authViewModel,
                    taskViewModel = taskViewModel
                )
            }
        }
    }
}