<!-- src/views/Register.vue -->
<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="w-full max-w-md bg-white p-8 rounded-lg shadow">
      <h2 class="text-2xl font-bold mb-6 text-teal-600">Créer un compte</h2>
      <form @submit.prevent="submitRegister" class="space-y-4">
        <div>
          <label class="block text-sm font-medium mb-1">Nom</label>
          <input
            v-model="name"
            type="text"
            required
            class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
          />
        </div>
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
        <div>
          <label class="block text-sm font-medium mb-1">Rôle</label>
          <select
            v-model="role"
            class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400"
          >
            <option value="CLIENT">Client</option>
            <option value="BARMAN">Barman</option>
          </select>
        </div>
        <button
          type="submit"
          class="w-full py-2 bg-teal-600 text-white font-semibold rounded hover:bg-teal-700 transition"
        >
          S’inscrire
        </button>
      </form>
      <p class="mt-4 text-center text-sm">
        Déjà inscrit ?
        <router-link to="/login" class="text-teal-600 hover:underline">
          Connectez-vous
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

const name = ref<string>('')
const email = ref<string>('')
const password = ref<string>('')
const role = ref<'CLIENT' | 'BARMAN'>('CLIENT')
const error = ref<string | null>(null)
const router = useRouter()

async function submitRegister() {
  error.value = null
  try {
    await api.post(
      '/auth/register',
      {
        name: name.value,
        email: email.value,
        password: password.value,
        role: role.value,
      },
      {},
      false
    )
    alert('Inscription réussie ! Connectez-vous.')
    router.push('/login')
  } catch (e: any) {
    error.value = e.message || 'Erreur lors de l’inscription'
    console.error('Registration failed:', e)
  }
}
</script>
