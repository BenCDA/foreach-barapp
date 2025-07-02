<!-- src/views/OrderDetail.vue -->
<template>
    <div class="min-h-screen bg-gray-50 p-4">
      <div class="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow space-y-4">
        <h1 class="text-2xl font-bold text-teal-600">Détails commande #{{ order.id }}</h1>
        <div v-for="c in order.items" :key="c.id" class="flex justify-between">
          <span>{{ c.nom }} ({{ c.taille }})</span>
          <span>{{ c.prix }} €</span>
        </div>
        <div class="flex justify-between font-semibold">
          <span>Total</span>
          <span>{{ order.total }} €</span>
        </div>
        <div class="space-x-2">
          <button
            class="px-4 py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition"
            @click="nextStatus"
            :disabled="order.statut === 'TERMINE'"
          >
            Passer à l’étape suivante
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { api } from '../services/api'
  import type { OrderDetail } from '../types'
  
  const route = useRoute()
  const router = useRouter()
  const order = ref<OrderDetail>({
    id: 0,
    items: [],
    total: 0,
    statut: ''
  })
  
  onMounted(async () => {
    const id = Number(route.params.id)
    order.value = await api.get<OrderDetail>(`/dashboard/${id}`, {}, true)
  })
  
  async function nextStatus() {
    await api.put(`/cocktail-commandes/${order.value.id}`, {}, {}, true)
    router.go(0) // recharge la page
  }
  </script>
  