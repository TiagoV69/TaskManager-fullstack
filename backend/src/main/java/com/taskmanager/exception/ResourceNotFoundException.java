package com.taskmanager.exception;

 // Excepcion personalizada para cuando no se encuentra un recurso
public class ResourceNotFoundException extends RuntimeException {


    // constructor para mensajes personalizados
    public ResourceNotFoundException(String message) {
        super(message);
    }


    // constructor para mensajes personalizados con causa
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}