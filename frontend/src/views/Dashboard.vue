<!-- src/views/Dashboard.vue -->
<template>
    <div class="min-h-screen bg-gray-50 p-4">
      <div class="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow space-y-4">
        <h1 class="text-2xl font-bold text-teal-600">Commandes en cours</h1>
        <div v-for="o in orders" :key="o.id" class="p-4 border rounded hover:shadow cursor-pointer" @click="goDetail(o.id)">
          <div class="flex justify-between">
            <span>Commande #{{ o.id }}</span>
            <span class="capitalize">{{ o.statut.toLowerCase() }}</span>
          </div>
        </div>
        <p v-if="!orders.length">Aucune commande dans ce statut.</p>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { api } from '../services/api'
  import type { OrderSummary } from '../types'
  import { useRouter } from 'vue-router'
  
  const orders = ref<OrderSummary[]>([])
  const router = useRouter()
  
  onMounted(async () => {
    orders.value = await api.get<OrderSummary[]>('/dashboard', {}, true)
  })
  
  function goDetail(id: number) {
    router.push(`/dashboard/${id}`)
  }
  </script>
  