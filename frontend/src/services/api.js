// src/services/api.js

const API_BASE_URL = 'http://localhost:8080/api'; // *** CHANGEMENT ICI : AJOUT DE /api ***

/**
 * Fonction utilitaire pour faire des requêtes API avec ou sans token d'authentification.
 * Gère l'ajout automatique de l'en-tête Authorization et la gestion des erreurs.
 *
 * @param {string} endpoint - Le chemin de l'endpoint API (ex: '/auth/login', '/cart').
 * @param {object} options - Les options de la requête Fetch (method, headers, body, etc.).
 * @param {boolean} requiresAuth - Indique si la requête nécessite un token d'authentification.
 */
async function callApi(endpoint, options = {}, requiresAuth = false) {
  const url = `${API_BASE_URL}${endpoint}`;
  const headers = {
    'Content-Type': 'application/json',
    ...options.headers, // Permet d'écraser ou d'ajouter d'autres headers
  };

  if (requiresAuth) {
    const token = localStorage.getItem('jwt');
    if (!token) {
      // Si le token est requis mais non présent
      // Pour une application réelle, tu voudrais probablement rediriger ici vers la page de login
      // ou émettre un événement global pour gérer la déconnexion.
      // Pour l'instant, nous lançons une erreur.
      throw new Error('No authentication token found. Please log in.');
    }
    headers['Authorization'] = `Bearer ${token}`;
  }

  try {
    const response = await fetch(url, {
      ...options,
      headers,
    });

    // Gestion des réponses non-OK (erreurs HTTP 4xx, 5xx)
    if (!response.ok) {
      let errorData = { message: response.statusText };
      try {
        // Tente de parser la réponse JSON pour des messages d'erreur détaillés
        errorData = await response.json();
      } catch (e) {
        // Si la réponse n'est pas JSON, utilise le statut texte
      }

      // Spécifique pour les 401 (Unauthorized) ou 403 (Forbidden):
      if (response.status === 401 || response.status === 403) {
        console.error(`Authentication/Authorization Error (${response.status}):`, errorData.message);
        // Lance une erreur plus spécifique pour que le composant puisse la gérer
        throw new Error(`Auth Error: ${errorData.message || response.statusText}`);
      }
      // Pour les autres erreurs HTTP
      throw new Error(errorData.message || `API Error: ${response.status} - ${response.statusText}`);
    }

    // Retourne la réponse JSON si elle est parsable, sinon la réponse brute (pour 204 No Content par exemple)
    const contentType = response.headers.get("content-type");
    if (contentType && contentType.includes("application/json")) {
      return await response.json();
    }
    return response; // Retourne la réponse si pas de JSON (ex: pour un succès sans contenu)

  } catch (error) {
    console.error('API call error:', error);
    throw error; // Relance l'erreur pour que le composant appelant puisse la gérer
  }
}

// Exporte les fonctions pour les différentes méthodes HTTP, avec gestion du token
export const api = {
  get: (endpoint, options = {}, requiresAuth = false) => callApi(endpoint, { method: 'GET', ...options }, requiresAuth),
  post: (endpoint, data, options = {}, requiresAuth = false) => callApi(endpoint, { method: 'POST', body: JSON.stringify(data), ...options }, requiresAuth),
  put: (endpoint, data, options = {}, requiresAuth = false) => callApi(endpoint, { method: 'PUT', body: JSON.stringify(data), ...options }, requiresAuth),
  delete: (endpoint, options = {}, requiresAuth = false) => callApi(endpoint, { method: 'DELETE', ...options }, requiresAuth),
  patch: (endpoint, data, options = {}, requiresAuth = false) => callApi(endpoint, { method: 'PATCH', body: JSON.stringify(data), ...options }, requiresAuth),
};