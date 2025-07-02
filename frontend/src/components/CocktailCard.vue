<template>
    <div class="bg-white p-4 rounded-lg shadow flex flex-col">
      <h3 class="text-xl font-semibold mb-2">{{ cocktail.name }}</h3>
      <p class="text-gray-600 flex-1">{{ cocktail.description }}</p>
      <div class="mt-4 space-y-2">
        <div v-for="size in cocktail.sizes" :key="size.name" class="flex justify-between items-center">
          <span>{{ size.name }} – {{ size.price }} €</span>
          <button
            @click="addToCart(size.name)"
            class="px-3 py-1 bg-teal-600 text-white rounded hover:bg-teal-700 transition"
          >
            + Panier
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { api } from '../services/api'
  import type { Cocktail } from '../types'
  
  defineProps<{ cocktail: Cocktail }>()
  
  async function addToCart(size: string) {
    try {
      await api.post('/cart', { cocktailId: cocktail.id, size, quantity: 1 }, {}, true)
      alert('Ajouté au panier 🎉')
    } catch (e) {
      console.error('Échec ajout panier', e)
    }
  }
  </script>
  