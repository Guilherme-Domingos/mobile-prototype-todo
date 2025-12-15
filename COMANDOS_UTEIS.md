# 🛠️ Comandos Úteis

## 📱 Build e Execução

### Sincronizar Gradle
```bash
# Windows (PowerShell/CMD)
.\gradlew build

# Limpar build anterior
.\gradlew clean

# Limpar e reconstruir
.\gradlew clean build
```

### Executar no Dispositivo
```bash
# Instalar no dispositivo conectado
.\gradlew installDebug

# Desinstalar
.\gradlew uninstallDebug
```

### Verificar Dependências
```bash
# Listar dependências
.\gradlew app:dependencies

# Verificar versões desatualizadas
.\gradlew dependencyUpdates
```

## 🔍 Debug

### Logcat Filtrado
```bash
# Ver logs do app
adb logcat | findstr "mobile_prototype_todo"

# Ver logs do Retrofit
adb logcat | findstr "okhttp.OkHttpClient"

# Ver logs do Room
adb logcat | findstr "RoomDatabase"
```

### Limpar Dados do App
```bash
# Limpar dados e cache
adb shell pm clear com.example.mobile_prototype_todo
```

## 💾 Banco de Dados

### Acessar Banco de Dados
```bash
# Conectar ao shell do dispositivo
adb shell

# Navegar até o banco
cd /data/data/com.example.mobile_prototype_todo/databases/

# Abrir banco SQLite
sqlite3 todo_database

# Comandos SQLite úteis:
.tables                    # Listar tabelas
.schema users              # Ver estrutura da tabela users
.schema tasks              # Ver estrutura da tabela tasks
SELECT * FROM users;       # Ver todos os usuários
SELECT * FROM tasks;       # Ver todas as tarefas
.exit                      # Sair
```

### Exportar Banco de Dados
```bash
# Puxar banco para o computador
adb pull /data/data/com.example.mobile_prototype_todo/databases/todo_database ./backup.db
```

## 🌐 Testar API

### cURL (Windows PowerShell)
```powershell
# Testar endpoint da API
Invoke-WebRequest -Uri "https://jsonplaceholder.typicode.com/todos" -Method GET

# Ver apenas 5 primeiros resultados
(Invoke-RestMethod -Uri "https://jsonplaceholder.typicode.com/todos")[0..4]

# Buscar tarefas de um usuário específico
Invoke-RestMethod -Uri "https://jsonplaceholder.typicode.com/todos?userId=1"
```

## 🧪 Testes

### Executar Testes
```bash
# Testes unitários
.\gradlew test

# Testes instrumentados (precisa de emulador/dispositivo)
.\gradlew connectedAndroidTest

# Gerar relatório de cobertura
.\gradlew jacocoTestReport
```

## 📦 APK

### Gerar APK Debug
```bash
# Gerar APK de debug
.\gradlew assembleDebug

# APK será gerado em:
# app\build\outputs\apk\debug\app-debug.apk
```

### Gerar APK Release
```bash
# Gerar APK de release
.\gradlew assembleRelease

# APK será gerado em:
# app\build\outputs\apk\release\app-release-unsigned.apk
```

### Instalar APK Manualmente
```bash
# Instalar APK via adb
adb install app\build\outputs\apk\debug\app-debug.apk

# Instalar forçando atualização
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

## 🔧 Android Studio

### Atalhos Úteis
```
Ctrl + B          → Ir para definição
Ctrl + Alt + L    → Formatar código
Ctrl + Shift + F  → Buscar em todos os arquivos
Alt + Enter       → Quick fix
Ctrl + /          → Comentar/descomentar linha
Shift + F10       → Run app
Shift + F9        → Debug app
```

### Limpar Cache do Android Studio
```
File → Invalidate Caches → Invalidate and Restart
```

## 📱 Emulador

### Comandos do Emulador
```bash
# Listar emuladores disponíveis
emulator -list-avds

# Iniciar emulador específico
emulator -avd Pixel_5_API_34

# Iniciar com wipe de dados
emulator -avd Pixel_5_API_34 -wipe-data
```

### Controles do Emulador
```
Ctrl + M          → Menu
Ctrl + H          → Home
Ctrl + Backspace  → Back
F11               → Fullscreen
```

## 📊 Análise de Código

### Lint
```bash
# Executar análise Lint
.\gradlew lint

# Ver relatório:
# app\build\reports\lint-results-debug.html
```

### Detekt (Análise Estática Kotlin)
```bash
# Adicionar Detekt ao projeto
.\gradlew detekt
```

## 🗃️ Dados de Teste

### Inserir Usuário de Teste via SQL
```sql
-- No SQLite do dispositivo:
INSERT INTO users (username, password, name) 
VALUES ('admin', 'admin123', 'Administrador');

INSERT INTO users (username, password, name) 
VALUES ('teste', '123456', 'Usuário Teste');
```

### Inserir Tarefa de Teste via SQL
```sql
INSERT INTO tasks (userId, title, completed, isFromApi) 
VALUES (1, 'Tarefa de Teste', 0, 0);

INSERT INTO tasks (userId, title, completed, isFromApi) 
VALUES (1, 'Tarefa Concluída', 1, 0);
```

## 🔄 Git

### Comandos Git Úteis
```bash
# Verificar status
git status

# Adicionar todos os arquivos
git add .

# Commit
git commit -m "feat: implementa gerenciamento de tarefas completo"

# Push
git push origin main

# Ver diferenças
git diff

# Ver histórico
git log --oneline
```

## 📸 Screenshots

### Capturar Screenshot do App
```bash
# Capturar screenshot
adb shell screencap -p /sdcard/screenshot.png

# Baixar para o computador
adb pull /sdcard/screenshot.png ./screenshot.png
```

## 🐛 Troubleshooting

### Gradle Daemon Travado
```bash
.\gradlew --stop
```

### Problemas com Dependências
```bash
# Limpar cache do Gradle
.\gradlew clean --refresh-dependencies
```

### App não Instala
```bash
# Verificar se há outro app com mesmo package
adb shell pm list packages | findstr mobile_prototype_todo

# Desinstalar completamente
adb uninstall com.example.mobile_prototype_todo
```

### Emulador Lento
```
1. No AVD Manager, edite o emulador
2. Show Advanced Settings
3. Aumente RAM para 2048 MB
4. Enable Hardware Acceleration
5. Graphics: Hardware - GLES 2.0
```

## 📚 Recursos Úteis

### URLs de Referência
```
Compose:          https://developer.android.com/jetpack/compose
Room:             https://developer.android.com/training/data-storage/room
Retrofit:         https://square.github.io/retrofit/
Coroutines:       https://kotlinlang.org/docs/coroutines-overview.html
Material Design:  https://m3.material.io/
JSONPlaceholder:  https://jsonplaceholder.typicode.com/
```

### Logs Importantes
```kotlin
// Adicionar logs no código para debug:
import android.util.Log

// Tag padrão
private const val TAG = "TodoApp"

// Usar nos métodos:
Log.d(TAG, "Login realizado com sucesso")
Log.e(TAG, "Erro ao sincronizar: ${exception.message}")
Log.i(TAG, "Tarefa criada com ID: $taskId")
```

## ⚡ Performance

### Verificar Performance
```bash
# Profiler do Android Studio:
View → Tool Windows → Profiler
```

### Reduzir Tamanho do APK
```groovy
// No build.gradle.kts (app):
android {
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}
```

---

💡 **Dica**: Mantenha este arquivo aberto enquanto desenvolve!
