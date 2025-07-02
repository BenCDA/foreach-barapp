<!-- src/components/CocktailCard.vue -->
<template>
    <div class="bg-white p-4 rounded-lg shadow flex flex-col">
      <img
        v-if="cocktail.imageUrl"
        :src="cocktail.imageUrl"
        :alt="cocktail.name"
        class="w-full h-32 object-cover rounded mb-4"
      />
      <h3 class="text-lg font-semibold mb-2">{{ cocktail.name }}</h3>
      <p class="text-sm text-gray-600 mb-4">{{ cocktail.description }}</p>
      <button
        @click="$emit('add')"
        class="mt-auto bg-teal-600 text-white py-2 rounded hover:bg-teal-700 transition"
      >
        Voir les détails
      </button>
    </div>
  </template>
  
  <script lang="ts" setup>
  import type { Cocktail } from '../types'
  defineProps<{ cocktail: Cocktail }>()
  </script>
  