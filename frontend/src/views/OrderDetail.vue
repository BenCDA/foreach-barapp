<template>
  <div class="min-h-screen bg-gray-50 flex flex-col items-center py-12">
    <div class="max-w-2xl w-full bg-white rounded-xl shadow-lg p-8">
      <div class="flex items-center justify-between mb-6">
        <h2 class="text-3xl font-extrabold">
          Commande <span class="text-teal-500">#{{ order?.orderId }}</span>
        </h2>
        <span v-if="order" class="text-lg font-semibold text-gray-700">
          {{ formatStatus(order.status) }}
        </span>
      </div>

      <OrderProgressBar v-if="order" :status="order.status" />

      <div v-if="order" class="mt-8 space-y-4">
        <h3 class="text-xl font-bold mb-2">Détails</h3>
        <div
          v-for="c in order.cocktails"
          :key="c.id"
          class="flex items-center justify-between border-b py-2"
        >
          <div>
            <p class="font-semibold">{{ c.cocktailName }}</p>
            <span class="text-gray-500 text-sm">Taille {{ c.sizeLabel }}</span>
          </div>
          <span class="text-teal-600 font-bold text-lg">
            {{ c.price }} €
          </span>
        </div>
        <div class="flex justify-between font-semibold mt-6 text-lg">
          <span>Total</span>
          <span>{{ total }} €</span>
        </div>
      </div>

      <div v-else class="text-gray-500">Chargement...</div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute } from 'vue-router'
import { api } from '../services/api'
import OrderProgressBar from '../components/OrderProgressBar.vue'

interface CocktailLine {
  id: number
  cocktailName: string
  sizeLabel: string
  price: number
}

interface OrderResponse {
  orderId: number
  status: string
  cocktails: CocktailLine[]
}

const route = useRoute()
const orderId = Number(route.params.id)
const order = ref<OrderResponse | null>(null)

onMounted(async () => {
  try {
    order.value = await api.get<OrderResponse>(`/orders/${orderId}`, {}, true)
  } catch (e) {
    console.error('Erreur chargement détail commande', e)
  }
})

const total = computed(() =>
  order.value
    ? order.value.cocktails.reduce((sum, c) => sum + (c.price ?? 0), 0)
    : 0
)

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
