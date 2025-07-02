<!-- src/views/Login.vue -->
<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 p-4">
    <div class="w-full max-w-md bg-white p-8 rounded-lg shadow">
      <h2 class="text-2xl font-bold mb-6 text-teal-600">Connexion</h2>
      <form @submit.prevent="submitLogin" class="space-y-4">
        <div>
          <label class="block text-sm font-medium mb-1">Email</label>
          <input
            v-model="email"
            type="email"
            required
            class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
          />
        </div>
        <div>
          <label class="block text-sm font-medium mb-1">Mot de passe</label>
          <input
            v-model="password"
            type="password"
            required
            class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
          />
        </div>
        <button
          type="submit"
          class="w-full py-2 bg-teal-600 text-white font-semibold rounded hover:bg-teal-700 transition"
        >
          Se connecter
        </button>
      </form>
      <p class="mt-4 text-center text-sm">
        Pas encore de compte ?
        <router-link to="/register" class="text-teal-600 hover:underline">
          Inscrivez-vous
        </router-link>
      </p>
      <p v-if="error" class="mt-2 text-red-500 text-sm">{{ error }}</p>
    </div>
  </div>
</template>

// src/views/Login.vue
<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '../services/api'

const email    = ref<string>('')
const password = ref<string>('')
const error    = ref<string | null>(null)
const router   = useRouter()

// helper pour décoder le payload d'un JWT
function parseJwt<T = any>(token: string): T {
  const base64Url = token.split('.')[1]
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
  const jsonPayload = decodeURIComponent(
    atob(base64)
      .split('')
      .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
      .join('')
  )
  return JSON.parse(jsonPayload)
}

interface LoginResponse { token: string }

async function submitLogin() {
  error.value = null
  try {
    const { token } = await api.post<LoginResponse>(
      '/auth/login',
      { email: email.value, password: password.value },
      {},
      false
    )

    // 1) on stocke le token
    localStorage.setItem('jwt', token)

    // 2) on décode et extrait le role
    const { role } = parseJwt<{ role: string }>(token)
    localStorage.setItem('role', role)

    // 3) debug
    console.log('Connecté', 'role:', role, 'token:', token)

    // 4) on redirige
    router.push('/')
  } catch (e: any) {
    error.value = e.message || 'Erreur lors de la connexion'
  }
}
</script>

