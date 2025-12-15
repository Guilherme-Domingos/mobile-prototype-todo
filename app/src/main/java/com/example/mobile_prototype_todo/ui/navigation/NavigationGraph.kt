package com.example.mobile_prototype_todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mobile_prototype_todo.ui.screen.LoginScreen
import com.example.mobile_prototype_todo.ui.screen.RegisterScreen
import com.example.mobile_prototype_todo.ui.screen.TaskFormScreen
import com.example.mobile_prototype_todo.ui.screen.TaskListScreen
import com.example.mobile_prototype_todo.ui.viewmodel.AuthViewModel
import com.example.mobile_prototype_todo.ui.viewmodel.TaskViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    taskViewModel: TaskViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = { userId ->
                    navController.navigate(Screen.TaskList.createRoute(userId)) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onRegisterSuccess = { userId ->
                    navController.navigate(Screen.TaskList.createRoute(userId)) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = Screen.TaskList.route,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            TaskListScreen(
                userId = userId,
                viewModel = taskViewModel,
                onNavigateToCreateTask = {
                    navController.navigate(Screen.TaskForm.createRoute(userId))
                },
                onNavigateToEditTask = { taskId ->
                    navController.navigate(Screen.TaskForm.createRoute(userId, taskId))
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        
        composable(
            route = Screen.TaskForm.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("taskId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val taskId = backStackEntry.arguments?.getInt("taskId")?.takeIf { it != -1 }
            
            TaskFormScreen(
                userId = userId,
                taskId = taskId,
                viewModel = taskViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
