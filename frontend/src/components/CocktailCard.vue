<template>
    <div class="bg-white p-4 rounded-lg shadow hover:shadow-lg transition flex flex-col">
      <img
        v-if="cocktail.imageUrl"
        :src="cocktail.imageUrl"
        :alt="cocktail.name"
        class="w-full h-32 object-cover rounded mb-4"
      />
      <h3 class="text-lg font-semibold mb-2">{{ cocktail.name }}</h3>
      <p v-if="cocktail.description" class="text-gray-600 text-sm mb-4">
        {{ cocktail.description }}
      </p>
      <div class="mt-auto space-y-2">
        <button
          @click="$emit('add-to-cart')"
          class="w-full py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition"
        >
          + Ajouter au panier
        </button>
        <router-link
          :to="`/cocktails/${cocktail.id}`"
          class="w-full block text-center py-2 bg-gray-200 text-gray-700 rounded hover:bg-gray-300 transition"
        >
          Détails
        </router-link>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import type { Cocktail } from '../types'
  defineProps<{ cocktail: Cocktail }>()
  </script>
  