<template>
    <div class="min-h-screen bg-gray-50 flex flex-col items-center py-8 px-4">
      <h1 class="text-4xl font-bold text-teal-600 mb-2 text-center">
        Bienvenue sur Bar'App
      </h1>
      <p class="text-lg text-gray-700 mb-8 text-center max-w-2xl">
        Découvrez nos cocktails et plongez dans vos boissons préférées !
      </p>
  
      <div v-if="loading" class="text-gray-500">Chargement des cocktails…</div>
      <div v-else-if="cocktails.length === 0" class="text-red-500">
        Aucun cocktail disponible pour le moment.
      </div>
      <div
        v-else
        class="w-full max-w-6xl grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6"
      >
        <CocktailCard
          v-for="c in cocktails"
          :key="c.id"
          :cocktail="c"
          @add-to-cart="addToCart(c.id)"
        />
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { api } from '../services/api'
  import type { Cocktail } from '../types'
  import CocktailCard from '../components/CocktailCard.vue'
  
  const cocktails = ref<Cocktail[]>([])
  const loading = ref(true)
  const router = useRouter()
  
  async function fetchCocktails() {
    try {
      cocktails.value = await api.get<Cocktail[]>('/cocktails', {}, false)
    } catch (e) {
      console.error('Erreur chargement cocktails', e)
    } finally {
      loading.value = false
    }
  }
  
  function addToCart(cocktailId: number) {
    // redirige vers le panier avec ajout automatique ou popup
    router.push('/cart')
  }
  
  onMounted(fetchCocktails)
  </script>
  
  <style scoped>
  /* Styles éventuels */
  </style>
  