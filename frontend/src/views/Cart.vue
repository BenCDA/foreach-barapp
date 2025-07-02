<template>
    <div class="min-h-screen bg-gray-50 flex flex-col items-center justify-start py-8 px-4">
      <h2 class="text-3xl font-bold text-teal-600 mb-6">Votre panier</h2>
      <div class="w-full max-w-md">
        <div v-if="items.length">
          <div v-for="item in items" :key="item.id"
               class="bg-white p-4 rounded-lg shadow mb-4 flex justify-between">
            <div>
              <h3 class="font-semibold">{{ item.cocktail.name }} ({{ item.size }})</h3>
              <p>Quantité : {{ item.quantity }}</p>
            </div>
            <button @click="remove(item.id)" class="text-red-500 hover:underline">
              Supprimer
            </button>
          </div>
          <button @click="placeOrder"
                  class="w-full py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition">
            Passer la commande
          </button>
        </div>
        <p v-else class="text-gray-600 text-center">Votre panier est vide.</p>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { api } from '../services/api'
  import type { CartItem } from '../types'
  import { useRouter } from 'vue-router'
  
  const items  = ref<CartItem[]>([])
  const router = useRouter()
  
  async function loadCart() {
    items.value = await api.get<CartItem[]>('/cart', {}, true)
  }
  
  async function remove(id: number) {
    await api.delete(`/cart/${id}`, {}, true)
    loadCart()
  }
  
  async function placeOrder() {
    await api.post('/orders', {}, {}, true)
    router.push('/orders')
  }
  
  onMounted(loadCart)
  </script>
  