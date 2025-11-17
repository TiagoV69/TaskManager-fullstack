package com.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO para CREAR y ACTUALIZAR tareas
// Define qué datos puede enviar el cliente
@Data  // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor  // Constructor sin parámetros
@AllArgsConstructor  // Constructor con todos los parámetros
public class TaskRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    private String description;

    @NotNull(message = "El estado de completado es obligatorio")
    private Boolean completed;

    // NOTA: NO es necesario incluir id, createdAt, updatedAt
    // porque esos campos los maneja el sistema automáticamente
}