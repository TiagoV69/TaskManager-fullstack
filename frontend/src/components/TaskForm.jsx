import React, { useState, useEffect } from 'react';

/**
 * Componente formulario para crear/editar tareas
 * @param {Object} taskToEdit - Tarea a editar (null si es nueva)
 * @param {Function} onSubmit - Callback cuando se envía el formulario
 * @param {Function} onCancel - Callback para cancelar edición
 */
const TaskForm = ({ taskToEdit, onSubmit, onCancel }) => {
  
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    completed: false
  });

  const [errors, setErrors] = useState({});

  // Si hay una tarea para editar, llenar el formulario
  useEffect(() => {
    if (taskToEdit) {
      // eslint-disable-next-line react-hooks/set-state-in-effect
      setFormData({
        title: taskToEdit.title,
        description: taskToEdit.description || '',
        completed: taskToEdit.completed
      });
    }
  }, [taskToEdit]);

  // Manejar cambios en inputs
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
    // Limpiar error del campo cuando el usuario empieza a escribir
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: '' }));
    }
  };

  // Validar formulario
  const validate = () => {
    const newErrors = {};
    
    if (!formData.title.trim()) {
      newErrors.title = 'El título es obligatorio';
    } else if (formData.title.length > 255) {
      newErrors.title = 'El título no puede exceder 255 caracteres';
    }

    if (formData.description.length > 1000) {
      newErrors.description = 'La descripción no puede exceder 1000 caracteres';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Manejar envío del formulario
  const handleSubmit = (e) => {
    e.preventDefault();
    
    if (validate()) {
      onSubmit(formData);
      // Limpiar formulario si es creación (no edición)
      if (!taskToEdit) {
        setFormData({
          title: '',
          description: '',
          completed: false
        });
      }
    }
  };

  // Manejar cancelación
  const handleCancel = () => {
    setFormData({
      title: '',
      description: '',
      completed: false
    });
    setErrors({});
    onCancel();
  };

  return (
    <div className="task-form-container">
      <h2>{taskToEdit ? 'Editar Tarea' : 'Nueva Tarea'}</h2>
      
      <form onSubmit={handleSubmit} className="task-form">
        
        {/* Campo: Título */}
        <div className="form-group">
          <label htmlFor="title">
            Título <span className="required">*</span>
          </label>
          <input
            type="text"
            id="title"
            name="title"
            value={formData.title}
            onChange={handleChange}
            placeholder="Ej: Comprar leche"
            className={errors.title ? 'error' : ''}
            maxLength={255}
          />
          {errors.title && <span className="error-message">{errors.title}</span>}
        </div>

        {/* Campo: Descripción */}
        <div className="form-group">
          <label htmlFor="description">Descripción</label>
          <textarea
            id="description"
            name="description"
            value={formData.description}
            onChange={handleChange}
            placeholder="Descripción detallada (opcional)"
            rows={4}
            maxLength={1000}
          />
          {errors.description && <span className="error-message">{errors.description}</span>}
          <small className="char-count">
            {formData.description.length}/1000 caracteres
          </small>
        </div>

        {/* Campo: Completada (solo en edición) */}
        {taskToEdit && (
          <div className="form-group checkbox-group">
            <label>
              <input
                type="checkbox"
                name="completed"
                checked={formData.completed}
                onChange={handleChange}
              />
              Marcar como completada
            </label>
          </div>
        )}

        {/* Botones */}
        <div className="form-actions">
          <button type="submit" className="btn btn-primary">
            {taskToEdit ? 'Guardar Cambios' : 'Crear Tarea'}
          </button>
          {taskToEdit && (
            <button type="button" className="btn btn-secondary" onClick={handleCancel}>
              Cancelar
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default TaskForm;