package com.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO para respuestas de error estandarizadas
 * Se usa en todas las excepciones de la API
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // Solo incluye campos no nulos en el JSON
public class ErrorResponse {

    // Timestamp del error
    private LocalDateTime timestamp;

    //Código HTTP (404, 400, 500, etc.)
    private int status;

    // Descripción corta del error (Not Found, Bad Request, etc.)
    private String error;

    /// Mensaje detallado del error
    private String message;

    // Ruta de la petición que causó el error
    private String path;

    
    // Errores de validación (solo para código 400)
    // Map: nombre del campo → mensaje de error
    // Ejemplo: {"title": "El título es obligatorio"} 
    private Map<String, String> validationErrors;

    
     // Constructor para errores simples (sin validaciones)
    public ErrorResponse(LocalDateTime timestamp, int status, String error, 
                        String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}