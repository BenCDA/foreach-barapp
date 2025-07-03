<template>
  <div class="min-h-screen bg-gray-50 p-4 flex flex-col items-center">
    <h1 class="text-3xl font-bold mb-6"
        :class="isBarman ? 'text-teal-600' : 'text-green-600'">
      {{ isBarman ? 'Commandes à traiter' : 'Mes commandes' }}
    </h1>

    <div v-if="loading" class="text-gray-500">Chargement…</div>

    <ul v-else class="w-full max-w-2xl space-y-4">
      <li
        v-for="o in orders"
        :key="o.id"
        class="bg-white rounded shadow p-4 flex flex-col md:flex-row md:justify-between md:items-center"
      >
        <div class="flex-1">
          <p><span class="font-semibold">Commande #{{ o.id }}</span></p>
          <p class="text-sm text-gray-600 mb-2">
            {{ isBarman ? o.clientEmail : formatStatus(o.status) }}
          </p>
          <!-- Progression de la commande -->
          <OrderProgressBar :status="o.status" class="mb-2" />
        </div>
        <button
          @click="() => goDetail(o.id)"
          class="self-end md:self-auto mt-3 md:mt-0 px-3 py-1 bg-teal-600 text-white rounded hover:bg-teal-700 transition text-sm"
        >
          Détails
        </button>
      </li>
    </ul>

    <div v-if="!loading && orders.length === 0" class="mt-4 text-red-500">
      Aucune commande à afficher.
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '../services/api'
import OrderProgressBar from '../components/OrderProgressBar.vue'

interface Order {
  id: number
  status: string
  clientEmail?: string
}

const router = useRouter()
const loading = ref(true)
const orders = ref<Order[]>([])

// rôle
const role = localStorage.getItem('role') || ''
const isBarman = computed(() => role.includes('ROLE_BARMAN'))

onMounted(async () => {
  try {
    if (isBarman.value) {
      orders.value = await api.get<Order[]>('/orders/to-treat', {}, true)
    } else {
      orders.value = await api.get<Order[]>('/orders/me', {}, true)
    }
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
})

function goDetail(id: number) {
  router.push(`/orders/${id}`)
}

function formatStatus(s: string) {
  // Adapte ici en fonction de tes statuts réels du back
  switch (s) {
    case 'COMMANDEE': return 'Commandée'
    case 'EN_PREPARATION': return 'En préparation'
    case 'TERMINEE': return 'Terminée'
    default: return s
  }
}
</script>
