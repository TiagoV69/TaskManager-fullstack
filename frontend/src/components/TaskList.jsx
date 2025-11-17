import React from 'react';
import TaskItem from './TaskItem';

/**
 * Componente para mostrar lista de tareas
 * @param {Array} tasks - Array de tareas
 * @param {Function} onEdit - Callback para editar tarea
 * @param {Function} onDelete - Callback para eliminar tarea
 * @param {Function} onToggleComplete - Callback para cambiar estado
 */
const TaskList = ({ tasks, onEdit, onDelete, onToggleComplete, loading }) => {
  
  if (loading) {
    return (
      <div className="loading-container">
        <div className="spinner"></div>
        <p>Cargando tareas...</p>
      </div>
    );
  }

  if (tasks.length === 0) {
    return (
      <div className="empty-state">
        <h3>📋 No hay tareas</h3>
        <p>Crea tu primera tarea para comenzar</p>
      </div>
    );
  }

  // Separar tareas completadas y pendientes
  const pendingTasks = tasks.filter(task => !task.completed);
  const completedTasks = tasks.filter(task => task.completed);

  return (
    <div className="task-list">
      
      {/* Tareas Pendientes */}
      {pendingTasks.length > 0 && (
        <div className="task-section">
          <h2 className="section-title">
            📝 Pendientes ({pendingTasks.length})
          </h2>
          <div className="task-grid">
            {pendingTasks.map(task => (
              <TaskItem
                key={task.id}
                task={task}
                onEdit={onEdit}
                onDelete={onDelete}
                onToggleComplete={onToggleComplete}
              />
            ))}
          </div>
        </div>
      )}

      {/* Tareas Completadas */}
      {completedTasks.length > 0 && (
        <div className="task-section">
          <h2 className="section-title">
            ✅ Completadas ({completedTasks.length})
          </h2>
          <div className="task-grid">
            {completedTasks.map(task => (
              <TaskItem
                key={task.id}
                task={task}
                onEdit={onEdit}
                onDelete={onDelete}
                onToggleComplete={onToggleComplete}
              />
            ))}
          </div>
        </div>
      )}
    </div>
  );
};

export default TaskList;