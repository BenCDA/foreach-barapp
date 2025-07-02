<!-- src/views/Cart.vue -->
<template>
    <div class="min-h-screen bg-gray-50 p-4">
      <div class="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow space-y-4">
        <h1 class="text-2xl font-bold text-teal-600">Mon panier</h1>
        <div v-if="items.length">
          <div v-for="i in items" :key="i.id" class="flex justify-between">
            <span>{{ i.nom }} ({{ i.taille }})</span>
            <span>{{ i.prix }} €</span>
          </div>
          <hr />
          <div class="flex justify-between font-semibold">
            <span>Total</span>
            <span>{{ total }} €</span>
          </div>
          <button
            class="w-full mt-4 py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition"
            @click="checkout"
          >
            Valider la commande
          </button>
        </div>
        <p v-else>Votre panier est vide.</p>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted, computed } from 'vue'
  import { api } from '../services/api'
  import type { CartItem } from '../types'
  import { useRouter } from 'vue-router'
  
  const items = ref<CartItem[]>([])
  const router = useRouter()
  
  onMounted(async () => {
    items.value = await api.get<CartItem[]>('/cart', {}, true)
  })
  
  const total = computed(() => items.value.reduce((sum, i) => sum + i.prix, 0))
  
  async function checkout() {
    await api.post('/orders', {}, {}, true)
    router.push('/orders')
  }
  </script>
  