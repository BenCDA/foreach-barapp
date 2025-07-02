<template>
  <div class="min-h-screen bg-gray-50 py-8 px-4">
    <h1 class="text-3xl font-bold text-teal-600 mb-6 text-center">
      Ma carte de cocktails
    </h1>

    <div class="flex justify-end mb-4">
      <router-link
  to="/barman/cocktails/create"
  class="px-4 py-2 bg-teal-400 hover:bg-teal-500 text-white font-semibold rounded-lg transition"
>
  + Ajouter un cocktail
</router-link>

    </div>

    <div v-if="loading" class="text-gray-500 text-center">Chargement…</div>
    <div v-else-if="cocktails.length === 0" class="text-red-500 text-center">
      Aucun cocktail trouvé.
    </div>
    <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      <div v-for="c in cocktails" :key="c.id" class="bg-white p-4 rounded-lg shadow flex flex-col">
        <img v-if="c.imageUrl" :src="c.imageUrl" :alt="c.name" class="w-full h-32 object-cover rounded mb-4" />
        <h3 class="text-lg font-semibold mb-2">{{ c.name }}</h3>
        <p class="text-gray-600 text-sm line-clamp-3 mb-4">
          {{ c.description }}
        </p>
        <div class="mt-auto flex space-x-2">
          <router-link :to="`/barman/cocktails/${c.id}/edit`"
            class="flex-1 text-center py-2 bg-teal-400 hover:bg-teal-500 text-white font-semibold rounded-lg transition">
            Modifier
          </router-link>

          <button @click="deleteCocktail(c.id)"
            class="flex-1 text-center py-2 bg-red-500 hover:bg-red-600 text-white font-semibold rounded-lg transition">
            Supprimer
          </button>

        </div>
      </div>
    </div>
  </div>
</template>
  
<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { api } from '../services/api'
import { useRouter } from 'vue-router'

interface Cocktail {
  id: number
  name: string
  description: string
  imageUrl: string
}

const cocktails = ref<Cocktail[]>([])
const loading = ref(true)
const router = useRouter()

async function fetchCocktails() {
  try {
    cocktails.value = await api.get<Cocktail[]>('/cocktails', {}, true)
  } catch (e) {
    console.error('Erreur chargement cocktails', e)
  } finally {
    loading.value = false
  }
}

async function deleteCocktail(id: number) {
  if (!confirm('Confirmez-vous la suppression de ce cocktail ?')) return
  try {
    await api.delete(`/cocktails/${id}`, {}, true)
    // relance le chargement
    await fetchCocktails()
  } catch (e) {
    console.error('Erreur suppression', e)
    alert('Impossible de supprimer.')
  }
}

onMounted(fetchCocktails)
</script>
  
<style scoped>
.line-clamp-3 {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  -webkit-line-clamp: 3;
}
</style>
  

 