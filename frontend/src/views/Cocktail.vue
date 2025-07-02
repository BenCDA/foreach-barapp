<template>
    <div class="min-h-screen bg-gray-50 p-6">
      <div v-if="loading" class="text-gray-500">Chargement du cocktail…</div>
      <div v-else class="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow">
        <img
          v-if="cocktail.imageUrl"
          :src="cocktail.imageUrl"
          :alt="cocktail.nom"
          class="w-full h-64 object-cover rounded mb-6"
        />
        <h1 class="text-3xl font-bold mb-4">{{ cocktail.nom }}</h1>
        <p class="text-gray-700 mb-4">{{ cocktail.description }}</p>
  
        <div v-if="cocktail.ingredients.length" class="mb-4">
          <h2 class="font-semibold mb-2">Ingrédients :</h2>
          <ul class="list-disc list-inside">
            <li v-for="ing in cocktail.ingredients" :key="ing.nom">
              {{ ing.nom }} – {{ ing.quantite }}
            </li>
          </ul>
        </div>
  
        <div v-if="prices.length" class="mb-6">
          <h2 class="font-semibold mb-2">Choisissez une taille :</h2>
          <div class="flex gap-4">
            <label
              v-for="p in prices"
              :key="p.id"
              class="flex-1 flex items-center gap-2 border rounded px-4 py-2 cursor-pointer"
              :class="{ 'border-teal-600 bg-teal-100': selectedSize === p.id }"
            >
              <input
                type="radio"
                name="size"
                :value="p.id"
                v-model="selectedSize"
                class="form-radio"
              />
              <span>{{ p.libelle }} – {{ p.prix }} €</span>
            </label>
          </div>
        </div>
  
        <button
          @click="addToCart"
          :disabled="!selectedSize"
          class="w-full py-3 bg-teal-600 text-white font-semibold rounded hover:bg-teal-700 transition disabled:opacity-50"
        >
          Ajouter au panier
        </button>
      </div>
    </template>
  
  <script lang="ts" setup>
  import { ref, onMounted, computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { api } from '../services/api'
  import type { CocktailDetail, SizePrice } from '../types'
  
  const route        = useRoute()
  const router       = useRouter()
  const cocktailId   = Number(route.params.id)
  const cocktail     = ref<CocktailDetail>({
    id: 0, nom: '', imageUrl: '', description: '',
    ingredients: [], prices: []
  })
  const loading      = ref(true)
  const selectedSize = ref<number|null>(null)
  
  // On récupère d'abord le cocktail detail, incluant ses prix depuis l'API
  onMounted(async () => {
    try {
      const data = await api.get<CocktailDetail>(`/cocktails/${cocktailId}`, {}, true)
      cocktail.value = data
    } catch (e) {
      console.error('Erreur chargement cocktail', e)
    } finally {
      loading.value = false
    }
  })
  
  // on expose directement les prix dans le template
  const prices = computed<SizePrice[]>(() => cocktail.value.prices)
  
  async function addToCart() {
    if (!selectedSize.value) return
    try {
      await api.post('/cart/add', {
        cocktailId,
        sizeId: selectedSize.value,
        quantity: 1
      }, {}, true)
      router.push('/cart')
    } catch (e) {
      console.error('Erreur ajout au panier', e)
      alert('Impossible d’ajouter au panier.')
    }
  }
  </script>
  