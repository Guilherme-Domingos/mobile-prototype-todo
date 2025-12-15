package com.example.mobile_prototype_todo.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile_prototype_todo.data.model.Task
import com.example.mobile_prototype_todo.ui.viewmodel.TaskUiState
import com.example.mobile_prototype_todo.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    userId: Int,
    taskId: Int?,
    viewModel: TaskViewModel,
    onNavigateBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var completed by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var currentTask by remember { mutableStateOf<Task?>(null) }
    val uiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    
    val isEditMode = taskId != null
    
    // Carregar a tarefa ao abrir a tela de edição
    LaunchedEffect(taskId) {
        if (taskId != null) {
            isLoading = true
            val task = viewModel.getTaskById(taskId)
            task?.let {
                currentTask = it
                title = it.title
                completed = it.completed
            }
            isLoading = false
        }
    }
    
    LaunchedEffect(uiState) {
        when (uiState) {
            is TaskUiState.Success -> {
                viewModel.resetState()
                onNavigateBack()
            }
            else -> {}
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Editar Tarefa" else "Nova Tarefa") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título da Tarefa") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = completed,
                        onCheckedChange = { completed = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tarefa concluída")
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = {
                        if (isEditMode && currentTask != null) {
                            // Modo de edição - atualizar tarefa existente
                            viewModel.updateTask(
                                currentTask!!.copy(
                                    title = title,
                                    completed = completed
                                )
                            )
                        } else {
                            // Modo de criação - criar nova tarefa
                            viewModel.createTask(userId, title, completed)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState !is TaskUiState.Loading && title.isNotBlank()
                ) {
                    if (uiState is TaskUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(if (isEditMode) "Salvar" else "Criar")
                    }
                }
                
                if (uiState is TaskUiState.Error) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = (uiState as TaskUiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
