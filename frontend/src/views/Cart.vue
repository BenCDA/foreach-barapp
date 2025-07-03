<template>
  <div class="min-h-screen bg-gray-50 p-6">
    <div class="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow space-y-6">
      <h1 class="text-3xl font-bold text-teal-600">Votre panier</h1>

      <div v-if="loading" class="text-gray-500">Chargement du panier…</div>
      <div v-else-if="items.length === 0" class="text-gray-700">
        Votre panier est vide.
      </div>

      <div v-else class="space-y-4">
        <div v-for="item in items" :key="item.id" class="flex justify-between items-center">
          <div>
            <h2 class="font-semibold">
              {{ item.cocktailName }}
            </h2>
            <p class="text-sm text-gray-600">
              Taille {{ item.sizeLabel }} × 1
            </p>
          </div>
          <span class="font-medium">{{ item.price }} €</span>
        </div>

        <div class="flex justify-between font-semibold text-lg pt-2 border-t">
          <span>Total</span>
          <span>{{ total }} €</span>
        </div>

        <button @click="placeOrder"
          class="w-full py-3 bg-teal-400 hover:bg-teal-500 text-white font-semibold rounded-lg transition mt-4"
          :disabled="items.length === 0 || placingOrder">
          Commander
        </button>

        <div v-if="errorMsg" class="text-red-500 mt-2 text-center">{{ errorMsg }}</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { api } from '../services/api'

interface CartItem {
  id: number
  cocktailName: string
  sizeLabel: string
  price: number
}

const items = ref<CartItem[]>([])
const loading = ref(true)
const placingOrder = ref(false)
const errorMsg = ref('')
const router = useRouter()

async function loadCart() {
  loading.value = true
  try {
    items.value = await api.get<CartItem[]>('/cart', {}, true)
  } catch (e) {
    errorMsg.value = 'Erreur chargement panier'
  } finally {
    loading.value = false
  }
}

const total = computed(() =>
  items.value.reduce((sum, i) => sum + (i.price || 0), 0)
)

async function placeOrder() {
  errorMsg.value = ''
  placingOrder.value = true
  try {
    // Prends le premier item du panier pour récupérer un panierId
    const panierId = items.value.length > 0 ? items.value[0].id : null
    if (!panierId) {
      errorMsg.value = 'Votre panier est vide.'
      placingOrder.value = false
      return
    }
    await api.post('/orders', { panierId }, {}, true)
    router.push('/orders')
  } catch (e: any) {
    errorMsg.value = e.message || 'Impossible de passer la commande.'
  } finally {
    placingOrder.value = false
  }
}

onMounted(loadCart)
</script>
