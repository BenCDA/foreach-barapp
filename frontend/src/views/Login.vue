<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
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

<script lang="ts" setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '../services/api'

const email = ref<string>('')
const password = ref<string>('')
const error = ref<string | null>(null)
const router = useRouter()

async function submitLogin() {
  error.value = null
  try {
    const { token } = await api.post<{ token: string }>(
      '/auth/login',
      { email: email.value, password: password.value },
      {},
      false
    )
    localStorage.setItem('jwt', token)
    router.push('/')
  } catch (e: any) {
    error.value = e.message || 'Erreur lors de la connexion'
    console.error('Login failed:', e)
  }
}
</script>
