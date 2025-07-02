<template>
    <div class="min-h-screen bg-gray-50 py-8 px-4">
      <h2 class="text-3xl font-bold text-teal-600 mb-6">Détail commande #{{ order?.id }}</h2>
      <div v-if="order">
        <p class="mb-4">Statut global : <strong>{{ order.status }}</strong></p>
        <ul class="space-y-4">
          <li v-for="ci in order.cocktailItems" :key="ci.id" class="bg-white p-4 rounded-lg shadow">
            <h3 class="font-semibold">{{ ci.cocktail.name }} ({{ ci.size }})</h3>
            <p>Étape : {{ ci.step }}</p>
          </li>
        </ul>
      </div>
      <p v-else class="text-gray-600">Chargement…</p>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import { api } from '../services/api'
  import type { OrderDetail } from '../types'
  
  const route    = useRoute()
  const order    = ref<OrderDetail|null>(null)
  
  async function loadDetail() {
    const id = route.params.id as string
    order.value = await api.get<OrderDetail>(`/orders/${id}`, {}, true)
  }
  
  onMounted(loadDetail)
  </script>
  