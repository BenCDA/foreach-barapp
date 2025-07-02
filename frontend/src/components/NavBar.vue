<template>
    <nav class="bg-white shadow px-4 py-3 flex justify-between items-center">
      <router-link to="/" class="text-xl font-bold text-teal-600">Bar'App</router-link>
      <div class="space-x-4">
        <router-link to="/cart" class="hover:underline">
          Panier <span v-if="cartCount">({{ cartCount }})</span>
        </router-link>
        <router-link to="/orders" class="hover:underline">Mes commandes</router-link>
        <button @click="logout" class="text-red-500 hover:underline">Déconnexion</button>
      </div>
    </nav>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { api } from '../services/api'
  
  const router    = useRouter()
  const cartCount = ref<number>(0)
  
  async function loadCartCount() {
    try {
      const items = await api.get<{ quantity: number }[]>('/cart', {}, true)
      cartCount.value = items.reduce((sum, i) => sum + i.quantity, 0)
    } catch {
      cartCount.value = 0
    }
  }
  
  onMounted(loadCartCount)
  
  function logout() {
    localStorage.clear()
    router.push('/login')
  }
  </script>
  