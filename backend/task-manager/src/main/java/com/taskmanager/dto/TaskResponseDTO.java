package com.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//DTO para DEVOLVER tareas al cliente
// Define qué datos exponemos en la API
@Data  // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor  // Constructor sin parámetros
@AllArgsConstructor  // Constructor con todos los parámetros
@Builder  // Patrón Builder para construir objetos fácilmente
public class TaskResponseDTO {

    private Long id;

    private String title;

    private String description;

    // Estado de completado
    private Boolean completed;

    // Fecha de creación
    private LocalDateTime createdAt;

    // Fecha de última actualización
    private LocalDateTime updatedAt;

    // NOTA: Incluimos todos los campos que queremos exponer
    // Si en el futuro la Entity tiene campos sensibles,
    // simplemente NO los agregamos aquí
}