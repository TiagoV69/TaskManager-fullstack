import React from 'react';
import { FaEdit, FaTrash, FaCheck, FaUndo } from 'react-icons/fa';

/**
 * Componente para mostrar una tarea individual
 * @param {Object} task - Objeto de tarea
 * @param {Function} onEdit - Callback para editar
 * @param {Function} onDelete - Callback para eliminar
 * @param {Function} onToggleComplete - Callback para marcar completado/pendiente
 */
const TaskItem = ({ task, onEdit, onDelete, onToggleComplete }) => {
  
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <div className={`task-item ${task.completed ? 'completed' : ''}`}>
      <div className="task-header">
        <h3 className="task-title">
          {task.completed ? '✅' : '⬜'} {task.title}
        </h3>
        <div className="task-actions">
          <button
            className="btn-icon btn-toggle"
            onClick={() => onToggleComplete(task)}
            title={task.completed ? 'Marcar como pendiente' : 'Marcar como completada'}
          >
            {task.completed ? <FaUndo /> : <FaCheck />}
          </button>
          <button
            className="btn-icon btn-edit"
            onClick={() => onEdit(task)}
            title="Editar tarea"
          >
            <FaEdit />
          </button>
          <button
            className="btn-icon btn-delete"
            onClick={() => onDelete(task.id)}
            title="Eliminar tarea"
          >
            <FaTrash />
          </button>
        </div>
      </div>

      {task.description && (
        <p className="task-description">{task.description}</p>
      )}

      <div className="task-footer">
        <small className="task-date">
          Creada: {formatDate(task.createdAt)}
        </small>
        {task.updatedAt !== task.createdAt && (
          <small className="task-date">
            Actualizada: {formatDate(task.updatedAt)}
          </small>
        )}
      </div>
    </div>
  );
};

export default TaskItem;