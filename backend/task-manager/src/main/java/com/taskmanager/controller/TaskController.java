package com.taskmanager.controller;

import com.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar tareas
 * Expone endpoints HTTP para operaciones CRUD
 * 
 * Base URL: http://localhost:8080/api/tasks
 */
@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/api/tasks")  // Prefijo base para todas las rutas
@Slf4j  // Logger automático de Lombok
public class TaskController {


    // Inyección de dependencias del servicio
    private final TaskService taskService;

    // Constructor para inyección de dependencias
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        log.info("TaskController initialized");
    }


    // ENDPOINT 1: CREAR NUEVA TAREA
    // POST /api/tasks
    // Crea una nueva tarea
  
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  // Código HTTP 201
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO requestDTO) {
        log.info("POST /api/tasks - Creating task with title: {}", requestDTO.getTitle());
        
        TaskResponseDTO response = taskService.createTask(requestDTO);
        
        log.info("Task created successfully with ID: {}", response.getId());
        return response;
    }

    // ENDPOINT 2: OBTENER TODAS LAS TAREAS
    // GET /api/tasks
    // Obtiene todas las tareas o filtra por estado de completado
    
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(
            @RequestParam(required = false) Boolean completed) {
        
        log.info("GET /api/tasks - Fetching tasks (completed: {})", completed);
        
        List<TaskResponseDTO> tasks;
        
        if (completed != null) {
            // Filtrar por estado de completado
            tasks = taskService.getTasksByCompleted(completed);
            log.info("Found {} tasks with completed={}", tasks.size(), completed);
        } else {
            // Obtener todas las tareas
            tasks = taskService.getAllTasks();
            log.info("Found {} tasks in total", tasks.size());
        }
        
        return ResponseEntity.ok(tasks);  // HTTP 200 OK
    }


    // ENDPOINT 3: OBTENER TAREA POR ID
    // GET /api/tasks/{id}
    //  Obtiene una tarea específica por su ID
    
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        log.info("GET /api/tasks/{} - Fetching task", id);
        
        TaskResponseDTO task = taskService.getTaskById(id);
        
        log.info("Task found: {}", task.getTitle());
        return ResponseEntity.ok(task);  // HTTP 200 OK
    }


    // ENDPOINT 4: ACTUALIZAR TAREA
    // PUT /api/tasks/{id}
    //Actualiza una tarea existente   

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO requestDTO) {
        
        log.info("PUT /api/tasks/{} - Updating task", id);
        
        TaskResponseDTO updatedTask = taskService.updateTask(id, requestDTO);
        
        log.info("Task updated successfully: {}", updatedTask.getTitle());
        return ResponseEntity.ok(updatedTask);  // HTTP 200 OK
    }


    // ENDPOINT 5: ELIMINAR TAREA
    // DELETE /api/tasks/{id}
    //Elimina una tarea por su ID

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Código HTTP 204
    public void deleteTask(@PathVariable Long id) {
        log.info("DELETE /api/tasks/{} - Deleting task", id);
        
        taskService.deleteTask(id);
        
        log.info("Task deleted successfully with ID: {}", id);
    }


    // ENDPOINT ADICIONAL: HEALTH CHECK
    // GET /api/tasks/health
    // Endpoint simple para verificar que el controller está funcionando

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Task API is running!");
    }
}