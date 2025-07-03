// src/services/api.ts

const API_BASE_URL = 'http://localhost:8080/api'

async function callApi<T>(
  endpoint: string,
  options: RequestInit = {},
  requiresAuth = false
): Promise<T> {
  const url = `${API_BASE_URL}${endpoint}`

  // 1) Construire les headers
  const headers: Record<string, string> = { ...(options.headers as any) }

  if (requiresAuth) {
    const token = localStorage.getItem('jwt')
    if (!token) throw new Error('No auth token. Please log in.')
    headers.Authorization = `Bearer ${token}`
    console.debug('🔐 Token utilisé:', token)
  }

  // Pour DELETE sans body : on retire Content-Type
  if (options.method === 'DELETE' && !options.body) {
    delete headers['Content-Type']
  } else {
    headers['Content-Type'] ||= 'application/json'
  }

  console.debug('📡 Requête API :', { url, method: options.method, headers, body: options.body })

  // 2) Lancer fetch avec credentials inclus
  const response = await fetch(url, {
    ...options,
    headers,
    credentials: 'omit'
  })
  console.log("Headers envoyés pour DELETE:", headers)


  // 3) Si erreur HTTP
  if (!response.ok) {
    let msg = response.statusText
    try {
      const errJson = await response.json()
      msg = errJson.message || JSON.stringify(errJson)
    } catch { /* pas de JSON */ }

    if (response.status === 401 || response.status === 403) {
      console.warn(`🔒 Auth Error ${response.status} sur ${endpoint}:`, msg)
      throw new Error(`Auth Error: ${msg}`)
    }
    console.error(`⚠️ API Error ${response.status} sur ${endpoint}:`, msg)
    throw new Error(`API Error (${response.status}): ${msg}`)
  }

  // 4) Si 204 No Content, on renvoie un objet vide T (pour éviter de relire response.text/json)
  if (response.status === 204) {
    return {} as T
  }

  // 5) Pour tous les autres cas, on parse le JSON si présent
  const ct = response.headers.get('content-type') || ''
  if (ct.includes('application/json')) {
    return (await response.json()) as T
  }

  // Sinon, on renvoie aussi un objet vide
  return {} as T
}

export const api = {
  get: <T = unknown>(endpoint: string, opts: RequestInit = {}, auth = false) =>
    callApi<T>(endpoint, { method: 'GET', ...opts }, auth),

  post: <T = unknown, B = unknown>(
    endpoint: string,
    body: B,
    opts: RequestInit = {},
    auth = false
  ) => callApi<T>(endpoint, { method: 'POST', body: JSON.stringify(body), ...opts }, auth),

  put: <T = unknown, B = unknown>(
    endpoint: string,
    body: B,
    opts: RequestInit = {},
    auth = false
  ) => callApi<T>(endpoint, { method: 'PUT', body: JSON.stringify(body), ...opts }, auth),

  delete: <T = unknown>(
    endpoint: string,
    options: RequestInit = {},
    requiresAuth = false
  ) => callApi<T>(endpoint, { method: 'DELETE', ...options }, requiresAuth),
  

  patch: <T = unknown, B = unknown>(
    endpoint: string,
    body: B,
    opts: RequestInit = {},
    auth = false
  ) => callApi<T>(endpoint, { method: 'PATCH', body: JSON.stringify(body), ...opts }, auth),
}
