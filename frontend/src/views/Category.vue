<!-- src/views/Category.vue -->
<template>
    <div class="min-h-screen bg-gray-50 p-4">
      <div class="max-w-5xl mx-auto">
        <h1 class="text-2xl font-bold mb-4 text-teal-600">{{ categoryName }}</h1>
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          <CocktailCard
            v-for="c in cocktails"
            :key="c.id"
            :cocktail="c"
            @click="goToCocktail(c.id)"
          />
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { api } from '../services/api'
  import type { Cocktail } from '../types'
  import CocktailCard from '../components/CocktailCard.vue'
  
  const route = useRoute()
  const router = useRouter()
  const cocktails = ref<Cocktail[]>([])
  const categoryName = ref<string>('')
  
  onMounted(async () => {
    const id = Number(route.params.id)
    const resp = await api.get<{ name: string; items: Cocktail[] }>(`/categories/${id}`, {}, true)
    categoryName.value = resp.name
    cocktails.value = resp.items
  })
  
  function goToCocktail(id: number) {
    router.push(`/cocktails/${id}`)
  }
  </script>
  