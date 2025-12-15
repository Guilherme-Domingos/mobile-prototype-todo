package com.example.mobile_prototype_todo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_prototype_todo.data.model.User
import com.example.mobile_prototype_todo.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            if (username.isBlank() || password.isBlank()) {
                _authState.value = AuthState.Error("Preencha todos os campos")
                return@launch
            }
            
            val result = repository.login(username, password)
            result.fold(
                onSuccess = { user ->
                    _currentUser.value = user
                    _authState.value = AuthState.Success(user)
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Erro ao fazer login")
                }
            )
        }
    }
    
    fun register(username: String, password: String, name: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            if (username.isBlank() || password.isBlank() || name.isBlank()) {
                _authState.value = AuthState.Error("Preencha todos os campos")
                return@launch
            }
            
            val result = repository.register(username, password, name)
            result.fold(
                onSuccess = { user ->
                    _currentUser.value = user
                    _authState.value = AuthState.Success(user)
                },
                onFailure = { error ->
                    _authState.value = AuthState.Error(error.message ?: "Erro ao cadastrar")
                }
            )
        }
    }
    
    fun resetState() {
        _authState.value = AuthState.Idle
    }
    
    fun logout() {
        _currentUser.value = null
        _authState.value = AuthState.Idle
    }
}
