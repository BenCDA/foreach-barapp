<template>
    <div class="min-h-screen bg-gray-50 p-6">
      <div class="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow">
        <img
          v-if="cocktail.imageUrl"
          :src="cocktail.imageUrl"
          :alt="cocktail.name"
          class="w-full h-64 object-cover rounded mb-6"
        />
        <h1 class="text-3xl font-bold mb-4">{{ cocktail.name }}</h1>
        <p class="text-gray-700 mb-4">{{ cocktail.description }}</p>
  
        <div v-if="cocktail.ingredients?.length" class="mb-4">
          <h2 class="font-semibold mb-2">Ingrédients :</h2>
          <ul class="list-disc list-inside">
            <li v-for="ing in cocktail.ingredients" :key="ing.id">
              {{ ing.ingredient.name }} – {{ ing.quantite }}
            </li>
          </ul>
        </div>
  
        <div v-if="cocktail.prices?.length" class="mb-6">
          <h2 class="font-semibold mb-2">Prix :</h2>
          <div class="flex space-x-4">
            <label
              v-for="p in cocktail.prices"
              :key="p.id"
              class="flex items-center space-x-2"
            >
              <input
                type="radio"
                name="size"
                :value="p.taille.id"
                v-model="selectedSize"
                class="form-radio"
              />
              <span>{{ p.taille.libelle }} – {{ p.prix }}€</span>
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
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { api } from '../services/api'
  import type { Cocktail } from '../types'
  
  const route = useRoute()
  const router = useRouter()
  const cocktailId = Number(route.params.id)
  const cocktail = ref<Cocktail & {
    ingredients: { id: number; quantite: string; ingredient: { name: string } }[]
    prices: { id: number; prix: number; taille: { id: number; libelle: string } }[]
  }>({ id:0, name:'', description:'', imageUrl:'', ingredients:[], prices:[] })
  
  const selectedSize = ref<number|null>(null)
  
  onMounted(async () => {
    try {
      const data = await api.get(`/cocktails/${cocktailId}`, {}, true)
      cocktail.value = data as any
    } catch (e) {
      console.error('Erreur chargement cocktail', e)
    }
  })
  
  async function addToCart() {
    if (!selectedSize.value) return
    try {
      await api.post('/cart', {
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
  
  <style scoped>
  /* styles éventuels */
  </style>
  