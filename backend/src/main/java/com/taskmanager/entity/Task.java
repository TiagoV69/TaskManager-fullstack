package com.taskmanager.entity;

import jakarta.persistence.*;  // Importa todas las anotaciones JPA
import lombok.AllArgsConstructor;  // Genera constructor con todos los parámetros
import lombok.Data;  // Genera getters, setters, toString, equals, hashCode
import lombok.NoArgsConstructor;  // Genera constructor vacío

import java.time.LocalDateTime;  // Para manejar fechas y horas


@Entity  // Le dice a JPA: "Esta clase es una tabla"
@Table(name = "task")  // Nombre exacto de la tabla en la BD (opcional, por defecto usa "Task")
@Data  // Lombok genera getters, setters, toString, equals, hashCode automáticamente
@NoArgsConstructor  // Lombok genera constructor sin parámetros (requerido por JPA)
@AllArgsConstructor  // Lombok genera constructor con todos los parámetros
public class Task {

    @Id  // PK de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremento
    @Column(name = "id")    // Nombre de la columna en la BD
    private Long id; 

    @Column(
        name = "title",  // Nombre de la columna
        nullable = false,  // NOT NULL (el campo es obligatorio)
        length = 255  // VARCHAR(255) - máximo 255 caracteres
    )
    private String title;

    @Column(
        name = "description",
        columnDefinition = "TEXT"  // Tipo TEXT (sin límite de caracteres)
    )
    private String description;

    @Column(
        name = "completed",
        nullable = false  // NOT NULL
    )
    private Boolean completed = false;  // Por defecto, las tareas no están completadas

    // CREATED_AT (Fecha de creación)
    @Column(
        name = "created_at",
        nullable = false,
        updatable = false  // IMPORTANTE: Este campo NO se puede actualizar después de creado
    )
    private LocalDateTime createdAt;

    // UPDATED_AT (Fecha de última actualización)
    @Column(
        name = "updated_at",
        nullable = false
    )
    private LocalDateTime updatedAt;

    // Se ejecuta AUTOMÁTICAMENTE antes de guardar por primera vez
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;  // Establece fecha de creación
        this.updatedAt = now;  // Establece fecha de actualización (inicialmente igual)
    }

    // Se ejecuta AUTOMÁTICAMENTE antes de actualizar
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();  // Actualiza fecha de modificación
    }
}