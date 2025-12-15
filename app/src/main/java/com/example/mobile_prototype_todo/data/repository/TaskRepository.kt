package com.example.mobile_prototype_todo.data.repository

import com.example.mobile_prototype_todo.data.local.dao.TaskDao
import com.example.mobile_prototype_todo.data.model.Task
import com.example.mobile_prototype_todo.data.remote.TodoApiService
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao,
    private val apiService: TodoApiService
) {
    
    fun getTasksByUser(userId: Int): Flow<List<Task>> {
        return taskDao.getTasksByUser(userId)
    }
    
    suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }
    
    suspend fun syncTasksFromApi(userId: Int): Result<Unit> {
        return try {
            val apiTasks = apiService.getTodosByUser()
            // Limpa as tarefas antigas da API
            taskDao.deleteApiTasks(userId)
            // Converte e insere as novas tarefas
            val tasks = apiTasks.map { apiTask ->
                Task(
                    id = 0, // Deixa o Room gerar o ID
                    userId = userId,
                    title = apiTask.title,
                    completed = apiTask.completed,
                    isFromApi = true
                )
            }
            taskDao.insertTasks(tasks)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun createTask(task: Task): Result<Long> {
        return try {
            val id = taskDao.insertTask(task)
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun updateTask(task: Task): Result<Unit> {
        return try {
            taskDao.updateTask(task)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun deleteTask(task: Task): Result<Unit> {
        return try {
            taskDao.deleteTask(task)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
