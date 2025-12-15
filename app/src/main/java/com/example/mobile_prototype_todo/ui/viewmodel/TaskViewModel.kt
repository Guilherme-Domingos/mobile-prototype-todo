package com.example.mobile_prototype_todo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_prototype_todo.data.model.Task
import com.example.mobile_prototype_todo.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class TaskUiState {
    object Idle : TaskUiState()
    object Loading : TaskUiState()
    object Success : TaskUiState()
    data class Error(val message: String) : TaskUiState()
}

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {
    
    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Idle)
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()
    
    private val _currentUserId = MutableStateFlow<Int?>(null)
    
    val tasks: StateFlow<List<Task>> = _currentUserId.value?.let { userId ->
        repository.getTasksByUser(userId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    } ?: MutableStateFlow(emptyList())
    
    fun setUserId(userId: Int) {
        _currentUserId.value = userId
    }
    
    fun getTasksFlow(userId: Int): StateFlow<List<Task>> {
        return repository.getTasksByUser(userId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }
    
    suspend fun getTaskById(taskId: Int): Task? {
        return repository.getTaskById(taskId)
    }
    
    fun syncTasksFromApi(userId: Int) {
        viewModelScope.launch {
            _uiState.value = TaskUiState.Loading
            val result = repository.syncTasksFromApi(userId)
            result.fold(
                onSuccess = {
                    _uiState.value = TaskUiState.Success
                },
                onFailure = { error ->
                    _uiState.value = TaskUiState.Error(error.message ?: "Erro ao sincronizar tarefas")
                }
            )
        }
    }
    
    fun createTask(userId: Int, title: String, completed: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = TaskUiState.Loading
            
            if (title.isBlank()) {
                _uiState.value = TaskUiState.Error("O título não pode estar vazio")
                return@launch
            }
            
            val task = Task(
                userId = userId,
                title = title,
                completed = completed,
                isFromApi = false
            )
            
            val result = repository.createTask(task)
            result.fold(
                onSuccess = {
                    _uiState.value = TaskUiState.Success
                },
                onFailure = { error ->
                    _uiState.value = TaskUiState.Error(error.message ?: "Erro ao criar tarefa")
                }
            )
        }
    }
    
    fun updateTask(task: Task) {
        viewModelScope.launch {
            _uiState.value = TaskUiState.Loading
            
            if (task.title.isBlank()) {
                _uiState.value = TaskUiState.Error("O título não pode estar vazio")
                return@launch
            }
            
            val result = repository.updateTask(task)
            result.fold(
                onSuccess = {
                    _uiState.value = TaskUiState.Success
                },
                onFailure = { error ->
                    _uiState.value = TaskUiState.Error(error.message ?: "Erro ao atualizar tarefa")
                }
            )
        }
    }
    
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            _uiState.value = TaskUiState.Loading
            val result = repository.deleteTask(task)
            result.fold(
                onSuccess = {
                    _uiState.value = TaskUiState.Success
                },
                onFailure = { error ->
                    _uiState.value = TaskUiState.Error(error.message ?: "Erro ao excluir tarefa")
                }
            )
        }
    }
    
    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(completed = !task.completed)
            updateTask(updatedTask)
        }
    }
    
    fun resetState() {
        _uiState.value = TaskUiState.Idle
    }
}
