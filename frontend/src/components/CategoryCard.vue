<template>
    <div
      class="bg-white rounded-lg shadow hover:shadow-md transition p-4 cursor-pointer flex flex-col items-center"
      @click="$emit('click')"
    >
      <!-- Si tu as une image pour la catégorie, tu peux l’ajouter ici -->
      <div class="text-xl font-semibold text-gray-800">{{ category.nom }}</div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { defineProps } from 'vue'
  import type { Category } from '../types'
  
  const props = defineProps<{
    category: Category
  }>()
  </script>
  