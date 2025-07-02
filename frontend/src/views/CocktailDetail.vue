<template>
    <div class="min-h-screen bg-gray-50 p-6">
      <div v-if="loading" class="text-center py-20 text-gray-500">
        Chargement…
      </div>
      <div v-else class="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow space-y-6">
  
        <!-- IMAGE -->
        <img
          v-if="cocktail?.imageUrl"
          :src="cocktail.imageUrl"
          :alt="cocktail.name"
          class="w-full h-64 object-cover rounded"
        />
  
        <!-- NOM / DESCRIPTION -->
        <h1 class="text-3xl font-bold">{{ cocktail.name }}</h1>
        <p class="text-gray-700">{{ cocktail.description }}</p>
  
        <!-- INGRÉDIENTS -->
        <div v-if="ingredients.length" class="space-y-2">
          <h2 class="font-semibold">Ingrédients :</h2>
          <ul class="list-disc list-inside text-gray-600">
            <li v-for="ing in ingredients" :key="ing.id">
              {{ ing.ingredient.name }} – {{ ing.quantite }}
            </li>
          </ul>
        </div>
  
        <!-- TAILLES + PRIX -->
        <div class="space-y-2">
          <h2 class="font-semibold">Choisissez une taille :</h2>
          <div class="flex flex-wrap gap-6">
            <label
              v-for="s in sizes"
              :key="s.id"
              class="flex items-center space-x-2 cursor-pointer"
            >
              <input
                type="radio"
                name="size"
                :value="s.id"
                v-model="selectedSize"
                class="form-radio h-5 w-5 text-teal-600"
                :disabled="pricesMap[s.id] == null"
              />
              <span class="select-none">
                {{ s.libelle }}
                <span v-if="pricesMap[s.id] != null">
                  — {{ pricesMap[s.id] }} €
                </span>
                <span v-else class="text-gray-400">(non dispo)</span>
              </span>
            </label>
          </div>
        </div>
  
        <!-- BOUTON AJOUT -->
        <button
          @click="addToCart"
          :disabled="selectedSize === null"
          class="w-full py-3 bg-teal-600 text-white font-semibold rounded
                 hover:bg-teal-700 transition disabled:opacity-50"
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
  import type {
    Cocktail,
    Ingredient,
    SizePrice,
    Size
  } from '../types'
  
  const route        = useRoute()
  const router       = useRouter()
  const cocktailId   = Number(route.params.id)
  
  const loading      = ref(true)
  const cocktail     = ref<Cocktail | null>(null)
  const ingredients  = ref<Ingredient[]>([])
  const sizes        = ref<Size[]>([])
  const pricesMap    = ref<Record<number, number|null>>({})
  
  // radio sélectionnée
  const selectedSize = ref<number|null>(null)
  
  onMounted(async () => {
    try {
      // 1) données de base
      cocktail.value = await api.get<Cocktail>(
        `/cocktails/${cocktailId}`, {}, true
      )
  
      // 2) ingrédients
      ingredients.value = await api.get<Ingredient[]>(
        `/cocktail-ingredients/by-cocktail/${cocktailId}`,
        {}, true
      )
  
      // 3) toutes les tailles (S, M, L)
      sizes.value = await api.get<Size[]>('/sizes', {}, true)
  
      // 4) prix existants
      const sp = await api.get<SizePrice[]>(
        `/cocktail-size-prices/by-cocktail/${cocktailId}`,
        {}, true
      )
  
      // construire map taille→prix
      pricesMap.value = {}
      sizes.value.forEach(s => pricesMap.value[s.id] = null)
      sp.forEach(p => { pricesMap.value[p.taille.id] = p.prix })
    } catch (e) {
      console.error('CocktailDetail error', e)
    } finally {
      loading.value = false
    }
  })
  
  async function addToCart() {
    if (selectedSize.value === null) return
    try {
      await api.post(
        '/cart/add',
        { cocktailId, sizeId: selectedSize.value, quantity: 1 },
        {}, true
      )
      router.push('/cart')
    } catch (err) {
      console.error('Ajout panier erreur', err)
      alert('Impossible d’ajouter au panier.')
    }
  }
  </script>
  