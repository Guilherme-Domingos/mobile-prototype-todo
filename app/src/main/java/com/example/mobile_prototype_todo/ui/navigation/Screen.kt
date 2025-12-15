package com.example.mobile_prototype_todo.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object TaskList : Screen("task_list/{userId}") {
        fun createRoute(userId: Int) = "task_list/$userId"
    }
    object TaskForm : Screen("task_form/{userId}?taskId={taskId}") {
        fun createRoute(userId: Int, taskId: Int? = null) = 
            if (taskId != null) "task_form/$userId?taskId=$taskId"
            else "task_form/$userId"
    }
}
