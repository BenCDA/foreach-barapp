<!-- src/components/CocktailCard.vue -->
<template>
    <div
      class="bg-white rounded-lg shadow hover:shadow-md transition overflow-hidden cursor-pointer"
      @click="$emit('click')"
    >
      <img
        :src="cocktail.imageUrl"
        alt="cocktail image"
        class="w-full h-40 object-cover"
      />
      <div class="p-4">
        <h3 class="text-lg font-semibold text-gray-800 mb-2">{{ cocktail.nom }}</h3>
        <!-- Si tu veux afficher un petit prix, ajoute-le ici -->
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { defineProps } from 'vue'
  import type { Cocktail } from '../types'
  
  const props = defineProps<{
    cocktail: Cocktail
  }>()
  </script>
  