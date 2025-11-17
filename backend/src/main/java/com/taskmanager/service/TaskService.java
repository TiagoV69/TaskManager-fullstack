package com.taskmanager.service;

import com.taskmanager.dto.TaskRequestDTO;
import com.taskmanager.dto.TaskResponseDTO;
import com.taskmanager.entity.Task;
import com.taskmanager.exception.ResourceNotFoundException;
import com.taskmanager.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service  // Indica que esta clase es un servicio de Spring
@Slf4j  // Lombok genera un logger automáticamente (log)
public class TaskService {


    // Inyeccion de dependencias
    private final TaskRepository taskRepository;

    // Constructor para inyección de dependencias
    // Spring inyecta automáticamente el TaskRepository
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        log.info("TaskService initialized");
    }

    // Metodo para crear la tarea
    /**
     * Crea una nueva tarea
     * @param requestDTO Datos de la tarea a crear
     * @return TaskResponseDTO con la tarea creada (incluye ID generado)
     */
    @Transactional  // Asegura que la operación sea atómica
    public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
        log.info("Creating new task with title: {}", requestDTO.getTitle());
        
        // Convertir DTO a Entity
        Task task = convertToEntity(requestDTO);
        
        // Guardar en la base de datos
        Task savedTask = taskRepository.save(task);
        
        // Convertir Entity a DTO
        TaskResponseDTO responseDTO = convertToDTO(savedTask);
        
        log.info("Task created successfully with ID: {}", savedTask.getId());
        return responseDTO;
    }

    // Metodo para obtener todas las tareas
    /**
     * Obtiene todas las tareas
     * @return Lista de TaskResponseDTO
     */
    @Transactional(readOnly = true)  // Optimización para operaciones de solo lectura
    public List<TaskResponseDTO> getAllTasks() {
        log.info("Fetching all tasks");
        
        List<Task> tasks = taskRepository.findAll();
        
        // Convertir lista de Entity a lista de DTO usando Stream API
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Metodo para obtener una tarea por ID
    /**
     * Obtiene una tarea por su ID
     * @param id ID de la tarea
     * @return TaskResponseDTO
     * @throws ResourceNotFoundException si no se encuentra la tarea
     */
    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        log.info("Fetching task with ID: {}", id);
        
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró la tarea con ID: " + id
                ));
        
        return convertToDTO(task);
    }

    // Metodo para actualizar una tarea
    /**
     * Actualiza una tarea existente
     * @param id ID de la tarea a actualizar
     * @param requestDTO Nuevos datos de la tarea
     * @return TaskResponseDTO con la tarea actualizada
     * @throws ResourceNotFoundException si no se encuentra la tarea
     */
    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO) {
        log.info("Updating task with ID: {}", id);
        
        // Verificar que la tarea existe
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "No se encontró la tarea con ID: " + id
                ));
        
        // Actualizar los campos
        existingTask.setTitle(requestDTO.getTitle());
        existingTask.setDescription(requestDTO.getDescription());
        existingTask.setCompleted(requestDTO.getCompleted());
        
        // Guardar cambios (el @PreUpdate se ejecuta automáticamente)
        Task updatedTask = taskRepository.save(existingTask);
        
        log.info("Task updated successfully with ID: {}", id);
        return convertToDTO(updatedTask);
    }


    // Metodo para eliminar una tarea
    /**
     * Elimina una tarea por su ID
     * @param id ID de la tarea a eliminar
     * @throws ResourceNotFoundException si no se encuentra la tarea
     */
    @Transactional
    public void deleteTask(Long id) {
        log.info("Deleting task with ID: {}", id);
        
        // Verificar que la tarea existe
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                "No se encontró la tarea con ID: " + id
            );
        }
        
        // Eliminar la tarea
        taskRepository.deleteById(id);
        
        log.info("Task deleted successfully with ID: {}", id);
    }

 
    // Metodo para obtener tareas por estado
    /**
     * Obtiene tareas filtradas por estado de completado
     * @param completed true para tareas completadas, false para pendientes
     * @return Lista de TaskResponseDTO
     */
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getTasksByCompleted(Boolean completed) {
        log.info("Fetching tasks with completed status: {}", completed);
        
        List<Task> tasks = taskRepository.findByCompleted(completed);
        
        return tasks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    
    // Metodos privados para conversión entre DTO y Entity


    /**
     * Convierte TaskRequestDTO a la entidad Task
     * @param dto DTO de entrada
     * @return Entity Task
     */
    private Task convertToEntity(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        // Los campos createdAt y updatedAt se establecen automáticamente
        // por @PrePersist y @PreUpdate
        return task;
    }

    /**
     * Convierte la entidad Task a TaskResponseDTO
     * @param task Entity de la base de datos
     * @return DTO de salida
     */
    private TaskResponseDTO convertToDTO(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}