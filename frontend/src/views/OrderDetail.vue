<template>
  <div class="min-h-screen bg-gray-50 p-6 flex flex-col items-center">
    <div class="max-w-2xl w-full bg-white p-6 rounded-lg shadow space-y-8">
      <h1 class="text-2xl font-bold text-teal-600">Suivi de votre commande</h1>
      <OrderProgressBar :status="order?.status" />

      <div v-if="order">
        <div class="mb-4 text-gray-700">
          <p>Numéro de commande : <span class="font-semibold">{{ order.orderId }}</span></p>
          <p>Date : {{ formatDate(order.orderDate) }}</p>
          <p>Status : <span class="font-semibold">{{ statusLabel(order.status) }}</span></p>
        </div>

        <div>
          <h2 class="font-bold mb-2">Cocktails commandés :</h2>
          <ul class="space-y-2">
            <li v-for="c in order.cocktails" :key="c.id" class="border p-2 rounded flex justify-between items-center">
              <span>{{ c.cocktailName }} ({{ c.sizeLabel }})</span>
              <span class="text-xs bg-gray-100 px-2 py-1 rounded">{{ stepLabel(c.step) }}</span>
            </li>
          </ul>
        </div>
      </div>
      <div v-else class="text-gray-500">Chargement...</div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { api } from '../services/api'

// Barre d'avancement
const OrderProgressBar = defineAsyncComponent(() => import('../components/OrderProgressBar.vue'))

const route = useRoute()
const orderId = Number(route.params.id)
const order = ref<any>(null)

function statusLabel(status: string) {
  return {
    'COMMANDEE': 'Commandée',
    'EN_PREPARATION': 'En préparation',
    'TERMINEE': 'Terminée'
  }[status] || status
}

function stepLabel(step: string) {
  return {
    'PREPARATION': 'Préparation',
    'ASSEMBLAGE': 'Assemblage',
    'DRESSAGE': 'Dressage',
    'TERMINE': 'Terminé'
  }[step] || step
}

function formatDate(dateStr: string) {
  return new Date(dateStr).toLocaleString('fr-FR')
}

onMounted(async () => {
  order.value = await api.get(`/orders/${orderId}`, {}, true)
})
</script>
