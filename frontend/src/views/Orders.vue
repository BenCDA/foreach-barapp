<template>
  <div class="min-h-screen bg-gray-50 p-4 flex flex-col items-center">
    <h1 class="text-4xl font-bold mb-10 text-green-600">Mes commandes</h1>
    <div v-if="loading" class="text-gray-500">Chargement…</div>
    <ul v-else class="w-full max-w-3xl space-y-8">
      <li
        v-for="o in orders"
        :key="o.orderId"
        class="bg-white rounded-xl shadow p-6 flex flex-col items-center"
      >
        <div class="w-full flex justify-between items-center mb-2">
          <div>
            <p class="text-2xl font-extrabold">
              Commande <span class="text-teal-500">#{{ o.orderId }}</span>
            </p>
            <p class="text-base text-gray-600 mt-1 mb-1">
              {{ formatStatus(o.status) }}
            </p>
          </div>
          <button
            @click="goDetail(o.orderId)"
            class="px-6 py-2 bg-teal-600 text-white rounded-lg hover:bg-teal-700 transition text-lg font-semibold"
          >
            Détails
          </button>
        </div>
        <OrderProgressBar :status="o.status" />
      </li>
    </ul>
    <div v-if="!loading && orders.length === 0" class="mt-8 text-red-500 text-lg">
      Aucune commande à afficher.
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '../services/api'
import OrderProgressBar from '../components/OrderProgressBar.vue'

interface Order {
  orderId: number
  status: string
}

const router = useRouter()
const loading = ref(true)
const orders = ref<Order[]>([])

onMounted(async () => {
  try {
    orders.value = await api.get<Order[]>('/orders/me', {}, true)
  } catch (e) {
    console.error('Erreur chargement commandes', e)
  } finally {
    loading.value = false
  }
})

function goDetail(orderId: number) {
  router.push(`/orders/${orderId}`)
}

function formatStatus(s: string) {
  return {
    COMMANDEE: 'Commandée',
    EN_PREPARATION: 'En préparation',
    TERMINEE: 'Terminée'
  }[s] || s
}
</script>

<style scoped>
/* si nécessaire */
</style>
