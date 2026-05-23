# Hermes

Hermes es una aplicación de mensajería y comunidad tipo Discord que permite a los usuarios crear servidores, canales de texto, enviar mensajes privados y gestionar friendships.

## Características

- **Autenticación**: Registro y login de usuarios con verificación por email
- **Servidores**: Crear y administrar servidores propios
- **Canales**: Crear canales de texto dentro de servidores
- **Mensajería**: Chat en tiempo real tanto en canales de servidor como mensajes privados
- **Amistades**: Sistema de solicitudes de amistad, aceptar y rechazar
- **Invitaciones**: Invitar usuarios a unirse a servidores
- **Notificaciones**: Notificaciones en tiempo real para solicitudes de amistad, invitaciones y respuestas

## Tech Stack

- **Backend**: Spring Boot (Java 17), PostgreSQL, JWT Authentication
- **Frontend**: Angular 17+, TypeScript, SCSS

## Estructura del Proyecto

```
hermes/
├── backend/          # API REST con Spring Boot
│   └── src/main/java/maurezg7/backend/
│       ├── controller/   # Endpoints REST
│       ├── services/     # Lógica de negocio
│       ├── repository/   # Acceso a datos
│       ├── models/       # Entidades y DTOs
│       ├── security/     # Autenticación JWT
│       └── config/       # Configuración
│
└── frontend/          # Aplicación Angular
    └── src/app/
        ├── features/     # Componentes y servicios
        │   ├── auth/        # Login, register, verify
        │   ├── components/ # UI components
        │   ├── models/      # Modelos TypeScript
        │   ├── pages/       # Páginas principales
        │   └── services/    # Servicios API
        └── shared/      # Componentes compartidos
```

## Configuración

### Prerrequisitos

- Java 17+
- Node.js 18+
- PostgreSQL 14+

### Backend

1. Configurar la base de datos en `backend/src/main/resources/application.properties`
2. Ejecutar: `./mvnw spring-boot:run`

### Frontend

1. Instalar dependencias: `npm install`
2. Ejecutar: `npm start`

El frontend estará disponible en `http://localhost:4200`
El backend estará disponible en `http://localhost:8080`

## API Endpoints

### Autenticación
- POST `/api/auth/register` - Registrar usuario
- POST `/api/auth/login` - Iniciar sesión
- POST `/api/auth/verify` - Verificar email
- POST `/api/auth/forgot-password` - Solicitar recuperación
- POST `/api/auth/reset-password` - Restablecer contraseña

### Usuarios
- GET `/api/users/{id}` - Obtener usuario
- GET `/api/users/search` - Buscar usuarios
- PUT `/api/users/{id}` - Actualizar usuario

### Servidores
- GET `/api/servers/user/{userId}` - Obtener servidores del usuario
- POST `/api/servers` - Crear servidor
- DELETE `/api/servers/{name}` - Eliminar servidor

### Canales
- GET `/api/channels/server/{id}` - Obtener canales de servidor
- POST `/api/channels/server/{idServer}/user/{idUser}` - Crear canal

### Mensajes
- GET `/api/messages/chat/{chatId}` - Obtener mensajes de chatbox
- POST `/api/messages` - Enviar mensaje
- GET `/api/messages/private/{userId1}/{userId2}` - Mensajes privados

### Amistades
- GET `/api/friendships/{userId}/accepted` - Amigos aceptados
- GET `/api/friendships/{userId}/pending` - Solicitudes pendientes
- POST `/api/friendships` - Enviar solicitud
- PUT `/api/friendships/{userId}/accept` - Aceptar solicitud
- PUT `/api/friendships/{userId}/reject` - Rechazar solicitud

### Notificaciones
- GET `/api/notifications/{userId}` - Obtener notificaciones
- PUT `/api/notifications/{id}/read` - Marcar como leída

### Solicitudes de Canal
- POST `/api/channel-requests/user/{userId}/channel/{channelId}` - Solicitar acceso
- GET `/api/channel-requests/server/{serverId}/pending` - Solicitudes pendientes
- PATCH `/api/channel-requests/{id}/process` - Procesar solicitud
- PATCH `/api/channel-requests/{id}/respond` - Responder invitación

## Licencia

MIT
