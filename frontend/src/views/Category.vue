<template>
    <div class="min-h-screen bg-gray-50 flex flex-col items-center py-8 px-4">
      <h2 class="text-3xl font-bold text-teal-600 mb-6 text-center">
        {{ category?.name || 'Catégorie' }}
      </h2>
  
      <div
        v-if="cocktails.length"
        class="w-full max-w-6xl grid gap-6 grid-cols-1 sm:grid-cols-2 lg:grid-cols-3"
      >
        <CocktailCard
          v-for="c in cocktails"
          :key="c.id"
          :cocktail="c"
        />
      </div>
  
      <p v-else class="text-gray-600 text-center">
        Aucun cocktail disponible dans cette catégorie.
      </p>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRoute }     from 'vue-router'
  import { api }          from '../services/api'
  import type { Cocktail, Category } from '../types'
  import CocktailCard     from '../components/CocktailCard.vue'
  
  const route     = useRoute()
  const catId     = Number(route.params.id)
  const category  = ref<Category|null>(null)
  const cocktails = ref<Cocktail[]>([])
  
  async function loadCategoryAndCocktails() {
    try {
      // 1. On récupère la catégorie (pour son nom)
      category.value = await api.get<Category>(
        `/categories/${catId}`, {}, false
      )
      // 2. On récupère **tous** les cocktails
      const all = await api.get<Cocktail[]>('/cocktails', {}, false)
      // 3. On ne garde que ceux de notre catégorie
      cocktails.value = all.filter(c => c.category.id === catId)
    } catch (e) {
      console.error('Erreur de chargement', e)
    }
  }
  
  onMounted(loadCategoryAndCocktails)
  </script>
  