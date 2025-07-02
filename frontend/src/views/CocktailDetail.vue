<!-- src/views/CocktailDetail.vue -->
<template>
    <div class="min-h-screen bg-gray-50 p-4 flex flex-col items-center">
      <h1 class="text-3xl font-bold mb-6 text-green-600">Détail du cocktail</h1>
      <div v-if="loading" class="text-gray-500">Chargement…</div>
      <div v-else class="w-full max-w-md bg-white p-6 rounded-lg shadow">
        <img :src="cocktail.imageUrl" alt="" class="w-full h-48 object-cover rounded" />
        <h2 class="text-2xl font-semibold mt-4">{{ cocktail.name }}</h2>
        <p class="text-gray-700 mt-2">{{ cocktail.description }}</p>
  
        <!-- Si client : affichage des tailles + bouton Ajouter au panier -->
        <div v-if="!isBarman" class="mt-4 space-y-3">
          <div
            v-for="size in sizes"
            :key="size.id"
            class="flex justify-between items-center bg-gray-100 p-3 rounded"
          >
            <span>{{ size.libelle }} ({{ size.prix }} €)</span>
            <button
              @click="addToCart(size.id)"
              class="px-3 py-1 bg-green-600 text-white rounded hover:bg-green-700 transition"
            >
              Ajouter
            </button>
          </div>
        </div>
  
        <!-- Si barman : lien édition -->
        <div v-if="isBarman" class="mt-4">
          <router-link
            :to="`/barman/cocktails/${cocktail.id}/edit`"
            class="px-4 py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition"
          >
            Modifier ce cocktail
          </router-link>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted, computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { api } from '../services/api'
  
  interface CocktailDetail {
    id: number
    name: string
    description: string
    imageUrl: string
  }
  
  interface SizePrice {
    id: number
    libelle: 'S' | 'M' | 'L'
    prix: number
  }
  
  const route = useRoute()
  const router = useRouter()
  const loading = ref(true)
  const cocktail = ref<CocktailDetail>({
    id: 0, name: '', description: '', imageUrl: ''
  })
  const sizes = ref<SizePrice[]>([])
  
  // détection rôle
  const role = localStorage.getItem('role') || ''
  const isBarman = computed(() => role.includes('ROLE_BARMAN'))
  
  onMounted(async () => {
    try {
      const id = route.params.id
      // récupère le cocktail
      cocktail.value = await api.get<CocktailDetail>(`/cocktails/${id}`, {}, false)
      // récupère les tailles et prix
      sizes.value = await api.get<SizePrice[]>(`/cocktails/${id}/sizes`, {}, false)
    } catch (e) {
      console.error(e)
    } finally {
      loading.value = false
    }
  })
  
  function addToCart(sizeId: number) {
    api.post('/cart', { cocktailId: cocktail.value.id, sizeId }, {}, true)
      .then(() => router.push('/cart'))
      .catch(err => console.error(err))
  }
  </script>
  
  <style scoped>
  </style>
  