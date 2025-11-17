# 📋 Task Manager - Full Stack Application

Aplicación web full stack para gestión de tareas construida con **Spring Boot**, **React**, y **PostgreSQL**.

![Tech Stack](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

## 🚀 Características

- ✅ CRUD completo de tareas
- ✅ API REST con Spring Boot
- ✅ Frontend moderno con React
- ✅ Base de datos PostgreSQL
- ✅ Dockerización completa
- ✅ Arquitectura en capas
- ✅ Validación de datos
- ✅ Manejo de errores centralizado
- ✅ Diseño responsive

## 🛠️ Tecnologías

### Backend
- Java 17
- Spring Boot 3.2
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

### Frontend
- React 18
- Vite
- Axios
- React Icons
- CSS3

### DevOps
- Docker
- Docker Compose

## 📁 Estructura del Proyecto
```
task-manager-fullstack/
├── backend/           # Spring Boot API
│   ├── src/
│   ├── Dockerfile
│   └── docker-compose.yml
├── frontend/          # React UI
│   ├── src/
│   └── package.json
└── README.md
```

## 🚀 Cómo Ejecutar

### Prerequisitos
- Docker Desktop
- Node.js 18+
- Java 17+ (opcional, si no usas Docker)

### Opción 1: Con Docker (Recomendado)
```bash
# 1. Clonar el repositorio
git clone https://github.com/TU_USUARIO/task-manager-fullstack.git
cd task-manager-fullstack

# 2. Levantar backend con Docker
cd backend
docker-compose up -d

# 3. Levantar frontend
cd ../frontend
npm install
npm run dev
```

### Opción 2: Sin Docker
```bash
# Backend
cd backend
./mvnw spring-boot:run

# Frontend (en otra terminal)
cd frontend
npm install
npm run dev
```

## 🌐 URLs

- **Frontend:** http://localhost:5173
- **Backend API:** http://localhost:8080/api/tasks
- **Health Check:** http://localhost:8080/api/tasks/health

## 🏗️ Arquitectura
```
Frontend (React) → API REST (Spring Boot) → PostgreSQL
```

## 📝 API Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/tasks` | Obtener todas las tareas |
| GET | `/api/tasks/{id}` | Obtener tarea por ID |
| POST | `/api/tasks` | Crear nueva tarea |
| PUT | `/api/tasks/{id}` | Actualizar tarea |
| DELETE | `/api/tasks/{id}` | Eliminar tarea |

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add: AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.

## 👤 Autor

**Santiago Varela**
- Email: santyvarela.03@gmail.com
- GitHub: [@Tiagov69](https://github.com/TiagoV69)
- LinkedIn: [Santiago Varela](www.linkedin.com/in/tiagoov)

## 🙏 Agradecimientos

Proyecto creado como parte del aprendizaje de desarrollo Full Stack.

---

⭐️ Si te gusta este proyecto, dale una estrella en GitHub porfavor :D!