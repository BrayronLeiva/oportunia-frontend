# TaskApp - Android CLEAN Architecture Example

## Overview

TaskApp is a demonstration project for computer science students to learn CLEAN Architecture in
Android development. This project implements a task management application using modern Android
development practices.

# Internship Connect 🔍📱

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Clean Architecture](https://img.shields.io/badge/Architecture-Clean-0175C2?style=for-the-badge&logo=jetpack-compose)
![Hilt](https://img.shields.io/badge/DI-Hilt-430098?style=for-the-badge)

Aplicación móvil que conecta estudiantes con oportunidades de pasantías y empresas con talento emergente, implementando Clean Architecture y mejores prácticas de Android moderno.

## 🚀 Características Clave

### 👨🎓 Para Estudiantes
- Perfil personalizado con habilidades y experiencia
- Búsqueda avanzada con filtros (ubicación, industria, etc.)
- Sistema de aplicación integrado
- Notificaciones en tiempo real

### 🏢 Para Empresas
- Publicación y gestión de vacantes
- Dashboard de candidatos
- Sistema de evaluación integrado

## 🏗️ Arquitectura Técnica

### Diagrama de Capas
Presentation (UI) → Domain ← Data
↑ ↑
ViewModels Use Cases
↑ ↑
Composable Repositories

text

### Stack Tecnológico
| Capa            | Tecnologías                                                                 |
|-----------------|-----------------------------------------------------------------------------|
| **Presentación** | Jetpack Compose, ViewModel, Navigation Component, Material 3               |
| **Dominio**     | Use Cases, Modelos de Dominio, Corrutinas                                  |
| **Datos**       | Retrofit, Room, DataStore, Hilt, Gson                                      |

## ⚙️ Configuración

### Requisitos
- Android Studio Flamingo (2022.2.1)+
- SDK Android API 26+
- Kotlin 1.8.20+
- Error handling verification

## Best Practices Demonstrated

- Single Responsibility Principle
- Interface segregation
- Result-based error handling
- Coroutine test practices with `TestDispatcher`
