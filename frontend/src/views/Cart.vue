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
              {{ item.cocktail ? item.cocktail.name : item.cocktailId }}
            </h2>
            <p class="text-sm text-gray-600">
              Taille {{ item.size ? item.size.libelle : item.sizeId }} × {{ item.quantity }}
            </p>

          </div>
          <span class="font-medium">{{ item.price }}€</span>
        </div>

        <div class="flex justify-between font-semibold text-lg">
          <span>Total</span>
          <span>{{ total }}€</span>
        </div>

        <button @click="placeOrder"
          class="w-full py-3 bg-teal-400 hover:bg-teal-500 text-white font-semibold rounded-lg transition">
          Commander
        </button>

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
  quantity: number
  price: number
  cocktail: { id: number; name: string }
  size: { id: number; libelle: string }
}

const items = ref<CartItem[]>([])
const loading = ref(true)
const router = useRouter()

async function loadCart() {
  try {
    items.value = await api.get<CartItem[]>('/cart', {}, true)
  } catch (e) {
    console.error('Erreur chargement panier', e)
  } finally {
    loading.value = false
  }
}

const total = computed(() =>
  items.value.reduce((sum, i) => sum + i.price * i.quantity, 0)
)

async function placeOrder() {
  try {
    const order: any = await api.post('/orders', {}, {}, true)
    router.push(`/orders/${order.id}`)
  } catch (e) {
    console.error('Erreur création commande', e)
    alert('Impossible de passer la commande.')
  }
}

onMounted(loadCart)
</script>
  
<style scoped>
/* styles éventuels */
</style>
  