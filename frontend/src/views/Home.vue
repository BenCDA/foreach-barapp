<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-4">Nos cocktails</h1>

    <!-- Filtre par catégorie -->
    <div class="mb-6">
      <label class="mr-2">Filtrer par catégorie :</label>
      <select v-model="selectedCategory" @change="fetchCocktails"
              class="border px-2 py-1">
        <option :value="null">Toutes</option>
        <option v-for="cat in categories" :key="cat.id" :value="cat.id">
          {{ cat.name }}
        </option>
      </select>
    </div>

    <!-- Liste des cocktails -->
    <div class="grid grid-cols-3 gap-4">
      <CocktailCard
        v-for="cocktail in cocktails"
        :key="cocktail.id"
        :cocktail="cocktail"
      />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { api } from '../services/api'
import CocktailCard from '../components/CocktailCard.vue'

interface Category { id: number; name: string }
interface Cocktail { id: number; name: string; imageUrl: string }

const categories = ref<Category[]>([])
const cocktails   = ref<Cocktail[]>([])
const selectedCategory = ref<number|null>(null)

// Charger d’abord les catégories, puis les cocktails
onMounted(async () => {
  categories.value = await api.get('/categories', {}, true)
  await fetchCocktails()
})

async function fetchCocktails() {
  const url = selectedCategory.value
    ? `/cocktails?categoryId=${selectedCategory.value}`
    : '/cocktails'
  cocktails.value = await api.get(url, {}, true)
}
</script>
