<template>
  <div class="min-h-screen bg-gray-50 p-4">
    <div class="max-w-4xl mx-auto bg-white p-6 rounded-lg shadow space-y-4">
      <h1 class="text-2xl font-bold text-teal-600">Commandes en cours</h1>
      <div
        v-for="o in orders"
        :key="o.orderId"
        class="p-4 border rounded hover:shadow flex justify-between items-center"
      >
        <div @click="goDetail(o.orderId)" class="cursor-pointer">
          <span class="font-semibold">Commande <span class="text-teal-500">#{{ o.orderId }}</span></span>
        </div>
        <div class="flex items-center space-x-3">
          <select
            class="border rounded px-2 py-1 text-sm"
            v-model="o.status"
            @change="updateStatus(o)"
          >
            <option value="COMMANDEE">Commandée</option>
            <option value="EN_PREPARATION">En préparation</option>
            <option value="TERMINEE">Terminée</option>
          </select>
          <button
            class="text-sm text-teal-600 hover:underline"
            @click="goDetail(o.orderId)"
          >
            Mettre à jour
          </button>
        </div>
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
  orderId: number
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

function goDetail(orderId: number) {
  router.push(`/dashboard/${orderId}`)
}

function formatStatus(s: OrderSummary['status']) {
  return {
    COMMANDEE: 'Commandée',
    EN_PREPARATION: 'En préparation',
    TERMINEE: 'Terminée'
  }[s] || s
}

async function updateStatus(o: OrderSummary) {
  try {
    await api.patch(`/orders/${o.orderId}/status`, { statut: o.status }, {}, true)
  } catch (e) {
    console.error('Erreur mise à jour statut', e)
  }
}
</script>