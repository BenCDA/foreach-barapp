<!-- src/views/Home.vue -->
<template>
    <div class="min-h-screen bg-gray-50 p-4">
      <div class="max-w-7xl mx-auto">
        <h1 class="text-2xl font-bold text-teal-600 mb-6">Nos catégories</h1>
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
          <CategoryCard
            v-for="cat in categories"
            :key="cat.id"
            :category="cat"
            @click="goToCategory(cat.id)"
          />
        </div>
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
      const data = await api.get<Category[]>('/categories', {}, true)
      categories.value = data
    } catch (err) {
      console.error('Erreur lors du chargement des catégories :', err)
    }
  }
  
  function goToCategory(id: number) {
    router.push(`/categories/${id}`)
  }
  
  onMounted(fetchCategories)
  </script>
  