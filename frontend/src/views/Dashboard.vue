<template>
  <div class="min-h-screen bg-gray-50 p-4">
    <div class="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow space-y-4">
      <h1 class="text-2xl font-bold text-teal-600">Commandes en cours</h1>
      <div
        v-for="o in orders"
        :key="o.id"
        class="p-4 border rounded hover:shadow cursor-pointer flex justify-between items-center"
        @click="goDetail(o.id)"
      >
        <div>
          <span class="font-semibold">Commande <span class="text-teal-500">#{{ o.id }}</span></span>
        </div>
        <span class="capitalize text-gray-700">{{ formatStatus(o.status) }}</span>
      </div>
      <p v-if="!loading && orders.length === 0" class="text-gray-500">
        Aucune commande à traiter.
      </p>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '../services/api'

// On définit un type résumé
interface OrderSummary {
  id: number
  status: 'COMMANDEE' | 'EN_PREPARATION' | 'TERMINEE'
}

const orders = ref<OrderSummary[]>([])
const loading = ref(true)
const router = useRouter()

onMounted(async () => {
  try {
    // Appel de l'endpoint barman
    orders.value = await api.get<OrderSummary[]>('/orders/to-treat', {}, true)
  } catch (e) {
    console.error('Erreur chargement des commandes', e)
  } finally {
    loading.value = false
  }
})

function goDetail(id: number) {
  router.push(`/dashboard/${id}`)
}

function formatStatus(s: OrderSummary['status']) {
  return {
    COMMANDEE: 'Commandée',
    EN_PREPARATION: 'En préparation',
    TERMINEE: 'Terminée'
  }[s] || s
}
</script>
