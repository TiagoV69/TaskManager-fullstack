import axios from 'axios';

/**
 * Base URL del API Backend (Spring Boot)
 * En desarrollo: localhost:8080
 * En producción: cambiarías esto por tu dominio
 */
const API_URL = 'http://localhost:8080/api/tasks';

/**
 * Servicio para comunicarse con el API de Tareas
 * Todas las operaciones CRUD están aquí
 */
const taskService = {
  
  /**
   * Obtener todas las tareas
   * GET /api/tasks
   * @returns {Promise<Array>} Lista de tareas
   */
  getAllTasks: async () => {
    try {
      const response = await axios.get(API_URL);
      return response.data;
    } catch (error) {
      console.error('Error fetching tasks:', error);
      throw error;
    }
  },

  /**
   * Obtener una tarea por ID
   * GET /api/tasks/:id
   * @param {number} id - ID de la tarea
   * @returns {Promise<Object>} Tarea encontrada
   */
  getTaskById: async (id) => {
    try {
      const response = await axios.get(`${API_URL}/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching task ${id}:`, error);
      throw error;
    }
  },

  /**
   * Crear una nueva tarea
   * POST /api/tasks
   * @param {Object} task - Datos de la tarea (title, description, completed)
   * @returns {Promise<Object>} Tarea creada
   */
  createTask: async (task) => {
    try {
      const response = await axios.post(API_URL, task);
      return response.data;
    } catch (error) {
      console.error('Error creating task:', error);
      throw error;
    }
  },

  /**
   * Actualizar una tarea existente
   * PUT /api/tasks/:id
   * @param {number} id - ID de la tarea
   * @param {Object} task - Datos actualizados
   * @returns {Promise<Object>} Tarea actualizada
   */
  updateTask: async (id, task) => {
    try {
      const response = await axios.put(`${API_URL}/${id}`, task);
      return response.data;
    } catch (error) {
      console.error(`Error updating task ${id}:`, error);
      throw error;
    }
  },

  /**
   * Eliminar una tarea
   * DELETE /api/tasks/:id
   * @param {number} id - ID de la tarea a eliminar
   * @returns {Promise<void>}
   */
  deleteTask: async (id) => {
    try {
      await axios.delete(`${API_URL}/${id}`);
    } catch (error) {
      console.error(`Error deleting task ${id}:`, error);
      throw error;
    }
  },

  /**
   * Obtener tareas filtradas por estado
   * GET /api/tasks?completed=true/false
   * @param {boolean} completed - Estado de completado
   * @returns {Promise<Array>} Lista de tareas filtradas
   */
  getTasksByCompleted: async (completed) => {
    try {
      const response = await axios.get(API_URL, {
        params: { completed }
      });
      return response.data;
    } catch (error) {
      console.error('Error fetching filtered tasks:', error);
      throw error;
    }
  }
};

export default taskService;