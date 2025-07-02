<template>
    <div class="min-h-screen bg-gray-50 py-8 px-4">
      <h2 class="text-3xl font-bold text-teal-600 mb-6">{{ categoryName }}</h2>
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        <CocktailCard
          v-for="c in cocktails"
          :key="c.id"
          :cocktail="c"
        />
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import { api } from '../services/api'
  import type { Cocktail } from '../types'
  import CocktailCard from '../components/CocktailCard.vue'
  
  const route        = useRoute()
  const cocktails    = ref<Cocktail[]>([])
  const categoryName = ref<string>('')
  
  async function fetchCategory() {
    const id = route.params.id as string
    const cat = await api.get<{ name: string; cocktails: Cocktail[] }>(
      `/categories/${id}`, {}, false
    )
    categoryName.value = cat.name
    cocktails.value    = cat.cocktails
  }
  
  onMounted(fetchCategory)
  </script>
  