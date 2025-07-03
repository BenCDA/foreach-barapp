<template>
  <div class="min-h-screen bg-gray-50 py-8 px-4">
    <h1 class="text-4xl font-bold text-teal-600 mb-6">Nos Cocktails</h1>

    <!-- Filtre par catégorie -->
    <div class="mb-6 flex items-center gap-4">
      <label for="category-filter" class="font-medium text-gray-700">Filtrer par catégorie :</label>
      <select
        id="category-filter"
        v-model="selectedCategory"
        class="border border-gray-300 rounded-md px-4 py-2 focus:outline-none focus:ring-2 focus:ring-teal-400"
      >
        <option :value="null">Toutes</option>
        <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
      </select>
    </div>

    <!-- Liste des cocktails filtrés -->
    <div v-if="filteredCocktails.length" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
      <div
        v-for="c in filteredCocktails"
        :key="c.id"
        class="bg-white rounded-lg shadow hover:shadow-lg transition p-4 flex flex-col"
      >
        <img :src="c.imageUrl" alt="" class="h-40 w-full object-cover rounded-md mb-4" />
        <h2 class="text-xl font-semibold mb-2">{{ c.name }}</h2>
        <p class="text-gray-600 flex-1">{{ c.description }}</p>
        <router-link
          :to="`/cocktails/${c.id}`"
          class="mt-4 inline-block bg-teal-600 text-white px-4 py-2 rounded hover:bg-teal-700"
        >
          Voir
        </router-link>
      </div>
    </div>

    <!-- Message si aucun cocktail -->
    <p v-else class="text-center text-gray-500 mt-12 text-lg">
      Aucun cocktail disponible pour cette catégorie.
    </p>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted } from 'vue'
import { api } from '../services/api'

interface Cocktail {
  id: number
  name: string
  description: string
  imageUrl: string
  category: { id: number; name: string } | null
}
interface Category {
  id: number
  name: string
}

const allCocktails = ref<Cocktail[]>([])
const categories     = ref<Category[]>([])
const selectedCategory = ref<number | null>(null)

// listes filtrées en computed
const filteredCocktails = computed(() =>
  selectedCategory.value != null
    ? allCocktails.value.filter(c => c.category?.id === selectedCategory.value)
    : allCocktails.value
)

async function fetchData() {
  // Charge toutes les catégories et cocktails
  categories.value   = await api.get<Category[]>('/categories', {}, true)
  allCocktails.value = await api.get<Cocktail[]>('/cocktails', {}, true)
}

onMounted(fetchData)
</script>

<style scoped>
/* Styles spécifiques si besoin */
</style>
