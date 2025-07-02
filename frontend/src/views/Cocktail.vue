<!-- src/views/Cocktail.vue -->
<template>
    <div class="min-h-screen bg-gray-50 p-4">
      <div class="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow">
        <h1 class="text-2xl font-bold text-teal-600 mb-4">{{ cocktail.nom }}</h1>
        <img :src="cocktail.imageUrl" alt="" class="w-full h-64 object-cover rounded mb-4" />
        <p class="mb-4">{{ cocktail.description }}</p>
        <div class="space-y-4">
          <div v-for="t in sizes" :key="t.id" class="flex justify-between items-center">
            <span>{{ t.libelle }} – {{ t.prix }} €</span>
            <button
              class="px-4 py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition"
              @click="addToCart(t.id)"
            >
              Ajouter
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { api } from '../services/api'
  import type { CocktailDetail, SizePrice } from '../types'
  
  const route = useRoute()
  const router = useRouter()
  
  const cocktail = ref<CocktailDetail>({ id:0, nom:'', description:'', imageUrl:'', ingredients:[] })
  const sizes = ref<SizePrice[]>([])
  
  onMounted(async () => {
    const id = Number(route.params.id)
    cocktail.value = await api.get<CocktailDetail>(`/cocktails/${id}`, {}, true)
    sizes.value    = await api.get<SizePrice[]>(`/cocktails/${id}/sizes`, {}, true)
  })
  
  async function addToCart(sizeId: number) {
    await api.post('/cart', { cocktailId: cocktail.value.id, sizeId }, {}, true)
    router.push('/cart')
  }
  </script>
  