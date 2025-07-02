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
          class="bg-white rounded shadow p-4 flex justify-between items-center"
        >
          <div>
            <p><span class="font-semibold">Commande #{{ o.id }}</span></p>
            <p class="text-sm text-gray-600">
              {{ isBarman ? o.clientEmail : formatStatus(o.status) }}
            </p>
          </div>
          <button
            @click="() => goDetail(o.id)"
            class="px-3 py-1 bg-teal-600 text-white rounded hover:bg-teal-700 transition text-sm"
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
        orders.value = await api.get<Order[]>('/orders', {}, true)
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
    return {
      ORDERED: 'Commandée',
      IN_PROGRESS: 'En cours',
      DONE: 'Terminée'
    }[s] || s
  }
  </script>
  