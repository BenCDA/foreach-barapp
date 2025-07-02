<template>
    <nav class="bg-white shadow px-4 py-3 flex justify-between items-center">
      <router-link to="/" class="text-xl font-bold text-teal-600">Bar'App</router-link>
      <div class="space-x-4">
        <router-link to="/" class="hover:underline">Carte</router-link>
        <router-link to="/cart" class="hover:underline">
          Panier <span v-if="cartCount">({{ cartCount }})</span>
        </router-link>
        <router-link to="/orders" class="hover:underline">Mes commandes</router-link>
        <button @click="logout" class="text-red-500 hover:underline">Déconnexion</button>
      </div>
    </nav>
  </template>
  
  <script lang="ts" setup>
  import { computed } from 'vue'
  import { useRouter } from 'vue-router'
  import { api } from '../services/api'
  import type { CartItem } from '../types'
  
  const router = useRouter()
  
  // récupère le nombre d'articles dans le panier depuis l'API
  const cartCount = computed<number>(async () => {
    try {
      const items = await api.get<CartItem[]>('/cart', {}, true)
      return items.reduce((sum, i) => sum + i.quantity, 0)
    } catch {
      return 0
    }
  })
  
  // simple logout
  function logout() {
    localStorage.clear()
    router.push('/login')
  }
  </script>
  