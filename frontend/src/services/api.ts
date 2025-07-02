const API_BASE_URL = 'http://localhost:8080/api'

/**
 * Fonction utilitaire pour faire des requêtes API avec ou sans token d'authentification.
 * @param endpoint     Chemin de l'endpoint (ex: '/auth/login')
 * @param options      Options de la requête (method, headers, body, etc.)
 * @param requiresAuth Si vrai, ajoute le Bearer token automatiquement
 */
async function callApi<T>(
  endpoint: string,
  options: RequestInit = {},
  requiresAuth = false
): Promise<T> {
  const url = `${API_BASE_URL}${endpoint}`
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...options.headers as Record<string, string>,
  }

  if (requiresAuth) {
    const token = localStorage.getItem('jwt')
    if (!token) {
      throw new Error('No authentication token found. Please log in.')
    }
    headers.Authorization = `Bearer ${token}`
  
    console.log("🔐 Token utilisé:", token)
  }
  
  // Ajout temporaire pour debug complet :
  console.log("📡 Requête API envoyée :", {
    url,
    method: options.method,
    headers,
    body: options.body
  })
  

  const response = await fetch(url, {
    ...options,
    headers,
  })

  if (!response.ok) {
    let message = response.statusText
    try {
      const err = await response.json()
      message = err.message || JSON.stringify(err)
    } catch {
      // pas de JSON dans la réponse
    }
    if (response.status === 401 || response.status === 403) {
      throw new Error(`Auth Error: ${message}`)
    }
    throw new Error(`API Error (${response.status}): ${message}`)
  }

  const contentType = response.headers.get('content-type') || ''
  if (contentType.includes('application/json')) {
    return (await response.json()) as T
  }
  // @ts-ignore
  return response as unknown as T
}

export const api = {
  get: <T = unknown>(
    endpoint: string,
    options: RequestInit = {},
    requiresAuth = false
  ) => callApi<T>(endpoint, { method: 'GET', ...options }, requiresAuth),

  post: <T = unknown, B = unknown>(
    endpoint: string,
    body: B,
    options: RequestInit = {},
    requiresAuth = false
  ) => callApi<T>(endpoint, { method: 'POST', body: JSON.stringify(body), ...options }, requiresAuth),

  put: <T = unknown, B = unknown>(
    endpoint: string,
    body: B,
    options: RequestInit = {},
    requiresAuth = false
  ) => callApi<T>(endpoint, { method: 'PUT', body: JSON.stringify(body), ...options }, requiresAuth),

  delete: <T = unknown>(
    endpoint: string,
    options: RequestInit = {},
    requiresAuth = false
  ) => callApi<T>(endpoint, { method: 'DELETE', ...options }, requiresAuth),

  patch: <T = unknown, B = unknown>(
    endpoint: string,
    body: B,
    options: RequestInit = {},
    requiresAuth = false
  ) => callApi<T>(endpoint, { method: 'PATCH', body: JSON.stringify(body), ...options }, requiresAuth),
}
