package com.taskmanager.exception;

import com.taskmanager.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;   

/**
 * Manejador global de excepciones
 * Intercepta TODAS las excepciones de la aplicación
 * y devuelve respuestas de error estandarizadas
 * 
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * Se aplica a TODOS los @RestController de la aplicación
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // EXCEPCIÓN 1: ResourceNotFoundException
    // Código HTTP: 404 Not FoundW
    /**
     * Maneja errores cuando NO se encuentra un recurso
     * Ejemplo: buscar tarea con ID que no existe
     * 
     * @param ex Excepción lanzada
     * @param request Información de la petición HTTP
     * @return ResponseEntity con ErrorResponse y código 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest request) {
        
        log.error("Resource not found: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())  // 404
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())  // "Not Found"
                .message(ex.getMessage())
                .path(extractPath(request))
                .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // EXCEPCIÓN 2: MethodArgumentNotValidException
    // Código HTTP: 400 Bad Request
    /**
     * Maneja errores de validación (cuando @Valid falla)
     * Ejemplo: enviar title vacío o completed null
     * 
     * @param ex Excepción con errores de validación
     * @param request Información de la petición HTTP
     * @return ResponseEntity con ErrorResponse y código 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        
        log.error("Validation error: {}", ex.getMessage());
        
        // Extraer errores de validación campo por campo
        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())  // 400
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())  // "Bad Request"
                .message("Errores de validación en los datos enviados")
                .path(extractPath(request))
                .validationErrors(validationErrors)
                .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // EXCEPCIÓN 3: IllegalArgumentException
    // Código HTTP: 400 Bad Request
    /**
     * Maneja errores de argumentos inválidos
     * Ejemplo: pasar un string donde se espera un número
     * 
     * @param ex Excepción lanzada
     * @param request Información de la petición HTTP
     * @return ResponseEntity con ErrorResponse y código 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            WebRequest request) {
        
        log.error("Illegal argument: {}", ex.getMessage());
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())  // 400
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())  // "Bad Request"
                .message(ex.getMessage())
                .path(extractPath(request))
                .build();
        
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // EXCEPCIÓN 4: Exception (cualquier otra)
    // Código HTTP: 500 Internal Server Error
    /**
     * Maneja CUALQUIER excepción no capturada por los handlers anteriores
     * Este es el "catch-all" para errores inesperados
     * 
     * @param ex Excepción lanzada
     * @param request Información de la petición HTTP
     * @return ResponseEntity con ErrorResponse y código 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request) {
        
        log.error("Unexpected error: ", ex);  // Log completo con stack trace
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())  // 500
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())  // "Internal Server Error"
                .message("Ocurrió un error inesperado. Por favor, contacte al administrador.")
                .path(extractPath(request))
                .build();
        
        // NO exponemos el mensaje real de la excepción al cliente (seguridad)
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // MÉTODO AUXILIAR
    /**
     * Extrae la ruta del endpoint desde la petición
     * 
     * @param request Petición HTTP
     * @return Ruta del endpoint (ej: "/api/tasks/1")
     */
    private String extractPath(WebRequest request) {
        return request.getDescription(false).replace("uri=", "");
    }
}