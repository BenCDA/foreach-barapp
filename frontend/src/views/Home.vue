<template>
    <div class="min-h-screen bg-gray-50 flex flex-col items-center py-8 px-4">
      <h1 class="text-4xl font-bold text-teal-600 mb-2 text-center">
        Bienvenue sur Bar'App
      </h1>
      <p class="text-lg text-gray-700 mb-8 text-center max-w-2xl">
        Découvrez nos catégories de cocktails et plongez dans l’univers de vos boissons préférées !
      </p>
      <div class="w-full max-w-6xl grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        <CategoryCard
          v-for="cat in categories"
          :key="cat.id"
          :category="cat"
          @click="goToCategory(cat.id)"
        />
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { api } from '../services/api'
  import type { Category } from '../types'
  import CategoryCard from '../components/CategoryCard.vue'
  
  const categories = ref<Category[]>([])
  const router     = useRouter()
  
  async function fetchCategories() {
    try {
      categories.value = await api.get<Category[]>('/categories', {}, false)
    } catch (e) {
      console.error('Impossible de charger les catégories', e)
    }
  }
  
  function goToCategory(id: number) {
    router.push(`/categories/${id}`)
  }
  
  onMounted(fetchCategories)
  </script>
  