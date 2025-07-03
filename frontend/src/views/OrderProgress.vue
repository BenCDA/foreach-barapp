<template>
    <div class="max-w-2xl mx-auto mt-10 p-8 bg-white shadow rounded-xl space-y-8">
      <h1 class="text-2xl font-bold text-teal-600 mb-2">Suivi de votre commande</h1>
      <div v-if="order" class="space-y-4">
        <OrderProgressBar :status="order.status" />
        <div class="flex justify-between items-center">
          <span class="text-gray-700 font-semibold">Commande n°{{ order.id }}</span>
          <span class="text-gray-500">{{ formatDate(order.date) }}</span>
        </div>
        <ul>
          <li v-for="item in order.items" :key="item.id" class="py-2 border-b">
            {{ item.cocktailName }} ({{ item.sizeLabel }})
          </li>
        </ul>
      </div>
      <div v-else class="text-gray-500">Chargement...</div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import { api } from '../services/api'
  import OrderProgressBar from '../components/OrderProgressBar.vue'
  
  const route = useRoute()
  const orderId = Number(route.params.id)
  const order = ref<any>(null)
  
  function formatDate(dateStr: string) {
    const d = new Date(dateStr)
    return d.toLocaleString('fr-FR')
  }
  
  onMounted(async () => {
    try {
      order.value = await api.get(`/orders/${orderId}`, {}, true)
    } catch (e) {
      order.value = null
    }
  })
  </script>
  