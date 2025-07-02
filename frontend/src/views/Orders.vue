<template>
    <div class="min-h-screen bg-gray-50 py-8 px-4">
      <h2 class="text-3xl font-bold text-teal-600 mb-6">Mes commandes</h2>
      <ul class="space-y-4">
        <li
          v-for="o in orders"
          :key="o.id"
          class="bg-white p-4 rounded-lg shadow flex justify-between items-center hover:bg-gray-50 cursor-pointer"
          @click="goTo(o.id)"
        >
          <div>
            <p>Commande n°{{ o.id }} — {{ o.status }}</p>
            <p class="text-sm text-gray-500">{{ new Date(o.createdAt).toLocaleString() }}</p>
          </div>
          <span class="text-teal-600">›</span>
        </li>
      </ul>
      <p v-if="!orders.length" class="text-gray-600">Aucune commande pour le moment.</p>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { api } from '../services/api'
  import type { Order } from '../types'
  import { useRouter } from 'vue-router'
  
  const orders = ref<Order[]>([])
  const router = useRouter()
  
  async function loadOrders() {
    orders.value = await api.get<Order[]>('/orders', {}, true)
  }
  
  function goTo(id: number) {
    router.push(`/orders/${id}`)
  }
  
  onMounted(loadOrders)
  </script>
  