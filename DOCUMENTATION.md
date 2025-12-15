# Todo App - Aplicativo de Gerenciamento de Tarefas

## 📱 Sobre o Projeto

Aplicativo mobile Android para gerenciamento de tarefas desenvolvido com Jetpack Compose, utilizando consumo de API pública e persistência local com Room. O app permite autenticação de usuários e operações completas de CRUD sobre tarefas.

## 🎯 Requisitos Atendidos

### Tecnologias Utilizadas
- **Kotlin** - Linguagem de programação
- **Jetpack Compose** - UI moderna e declarativa
- **Room Database** - Persistência local de dados
- **Retrofit** - Consumo de API REST
- **Coroutines & Flow** - Programação assíncrona
- **Navigation Compose** - Navegação entre telas
- **Material Design 3** - Design system

### API Utilizada
- **JSONPlaceholder** - API pública para testes
- Endpoint: https://jsonplaceholder.typicode.com/todos

## 🏗️ Arquitetura

O projeto segue os princípios de Clean Architecture com MVVM:

```
app/
├── data/
│   ├── local/
│   │   ├── dao/           # Data Access Objects
│   │   └── AppDatabase    # Configuração do Room
│   ├── model/             # Entidades (User, Task)
│   ├── remote/            # API Service e Retrofit
│   └── repository/        # Repositórios (AuthRepository, TaskRepository)
├── ui/
│   ├── navigation/        # Navegação do app
│   ├── screen/            # Telas Compose
│   ├── theme/             # Tema e estilos
│   └── viewmodel/         # ViewModels
└── MainActivity.kt        # Activity principal
```

## 📦 Funcionalidades

### 1. Autenticação (Login/Cadastro)
- ✅ Tela de login com validação
- ✅ Tela de cadastro de novos usuários
- ✅ Persistência de dados com Room (login fake local)
- ✅ Validação de campos
- ✅ Feedback de erros

### 2. Gerenciamento de Tarefas
- ✅ Listagem de tarefas do usuário
- ✅ Sincronização com API JSONPlaceholder
- ✅ Criação de novas tarefas
- ✅ Edição de tarefas existentes
- ✅ Exclusão de tarefas
- ✅ Marcar/desmarcar como concluída
- ✅ Diferenciação entre tarefas locais e da API

### 3. Operações CRUD Completas
- **Create** - Criar novas tarefas
- **Read** - Listar e visualizar tarefas
- **Update** - Editar tarefas existentes
- **Delete** - Excluir tarefas

## 📊 Entidades do Banco de Dados

### User
```kotlin
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
    val name: String
)
```

### Task
```kotlin
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,
    val title: String,
    val completed: Boolean = false,
    val isFromApi: Boolean = false
)
```

## 🚀 Como Executar

1. Clone o repositório
```bash
git clone https://github.com/Guilherme-Domingos/mobile-prototype-todo.git
```

2. Abra o projeto no Android Studio

3. Aguarde o Gradle sincronizar as dependências

4. Execute o app em um emulador ou dispositivo físico (Android 7.0+)

## 📱 Fluxo de Uso

1. **Primeiro Acesso**
   - Abra o app
   - Clique em "Não tem uma conta? Cadastre-se"
   - Preencha os dados e crie sua conta
   - Você será direcionado para a tela de tarefas

2. **Login**
   - Digite seu usuário e senha
   - Clique em "Entrar"

3. **Gerenciar Tarefas**
   - **Sincronizar**: Clique no ícone de refresh para buscar tarefas da API
   - **Criar**: Clique no botão + flutuante
   - **Editar**: Clique em uma tarefa da lista
   - **Marcar como concluída**: Use o checkbox
   - **Excluir**: Clique no ícone de lixeira

4. **Sair**
   - Clique no ícone de saída no topo da tela

## 🔧 Dependências Principais

```kotlin
// Room
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
ksp("androidx.room:room-compiler:2.6.1")

// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// Navigation
implementation("androidx.navigation:navigation-compose:2.8.0")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
```

## 🎨 Características do Design

- Interface moderna com Material Design 3
- Tema claro adaptável
- Cards para listagem de tarefas
- FAB (Floating Action Button) para ações rápidas
- Feedback visual com CircularProgressIndicator
- Diálogos de confirmação para exclusões
- Indicadores visuais para tarefas da API vs. locais

## 📝 Observações Importantes

- O login é **fake** (local com Room), não há autenticação real em servidor
- As tarefas da API são sincronizadas e armazenadas localmente
- Tarefas locais e da API coexistem no banco de dados
- A marcação `isFromApi` diferencia a origem das tarefas
- Todas as operações são assíncronas usando Coroutines

## 👨‍💻 Desenvolvimento

Desenvolvido como parte da Avaliação 3 - N2

**Aluno**: Guilherme Domingos

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.
