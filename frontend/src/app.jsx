import React, { useState, useEffect } from 'react';
import taskService from './services/TaskService';
import TaskList from './components/TaskList';
import TaskForm from './components/TaskForm';
import './App.css';

function App() {
  // Estados
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [taskToEdit, setTaskToEdit] = useState(null);
  const [showForm, setShowForm] = useState(false);

  // Cargar tareas al montar el componente
  useEffect(() => {
    loadTasks();
  }, []);

  // Función para cargar todas las tareas
  const loadTasks = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await taskService.getAllTasks();
      setTasks(data);
    } catch (err) {
      setError('Error al cargar las tareas. Verifica que el backend esté corriendo.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  // Crear o actualizar tarea
  const handleSubmit = async (formData) => {
    try {
      if (taskToEdit) {
        // Actualizar tarea existente
        const updated = await taskService.updateTask(taskToEdit.id, formData);
        setTasks(tasks.map(t => t.id === taskToEdit.id ? updated : t));
        setTaskToEdit(null);
        setShowForm(false);
        alert('✅ Tarea actualizada correctamente');
      } else {
        // Crear nueva tarea
        const created = await taskService.createTask(formData);
        setTasks([created, ...tasks]);
        setShowForm(false);
        alert('✅ Tarea creada correctamente');
      }
    } catch (err) {
      alert('❌ Error al guardar la tarea');
      console.error(err);
    }
  };

  // Editar tarea
  const handleEdit = (task) => {
    setTaskToEdit(task);
    setShowForm(true);
  };

  // Eliminar tarea
  const handleDelete = async (id) => {
    if (window.confirm('¿Estás seguro de eliminar esta tarea?')) {
      try {
        await taskService.deleteTask(id);
        setTasks(tasks.filter(t => t.id !== id));
        alert('✅ Tarea eliminada correctamente');
      } catch (err) {
        alert('❌ Error al eliminar la tarea');
        console.error(err);
      }
    }
  };

  // Cambiar estado completado/pendiente
  const handleToggleComplete = async (task) => {
    try {
      const updated = await taskService.updateTask(task.id, {
        ...task,
        completed: !task.completed
      });
      setTasks(tasks.map(t => t.id === task.id ? updated : t));
    } catch (err) {
      alert('❌ Error al actualizar el estado');
      console.error(err);
    }
  };

  // Cancelar edición
  const handleCancel = () => {
    setTaskToEdit(null);
    setShowForm(false);
  };

  // Nueva tarea
  const handleNewTask = () => {
    setTaskToEdit(null);
    setShowForm(true);
  };

  return (
    <div className="app">
      
      {/* Header */}
      <header className="app-header">
        <h1>📋 Task Manager</h1>
        <p>Gestiona tus tareas de manera eficiente</p>
      </header>

      {/* Main Content */}
      <main className="app-main">
        
        {/* Botón Nueva Tarea */}
        {!showForm && (
          <div className="action-bar">
            <button className="btn btn-primary btn-new-task" onClick={handleNewTask}>
              ➕ Nueva Tarea
            </button>
            <button className="btn btn-secondary" onClick={loadTasks}>
              🔄 Recargar
            </button>
          </div>
        )}

        {/* Formulario (crear/editar) */}
        {showForm && (
          <TaskForm
            taskToEdit={taskToEdit}
            onSubmit={handleSubmit}
            onCancel={handleCancel}
          />
        )}

        {/* Mensaje de Error */}
        {error && (
          <div className="error-banner">
            ⚠️ {error}
          </div>
        )}

        {/* Lista de Tareas */}
        {!showForm && (
          <TaskList
            tasks={tasks}
            loading={loading}
            onEdit={handleEdit}
            onDelete={handleDelete}
            onToggleComplete={handleToggleComplete}
          />
        )}
      </main>

      {/* Footer */}
      <footer className="app-footer">
        <p>Creado con ❤️ usando React + Spring Boot</p>
      </footer>
    </div>
  );
}

export default App;