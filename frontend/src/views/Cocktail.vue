<template>
    <div class="min-h-screen bg-gray-50 p-4 flex flex-col items-center">
      <h1 class="text-3xl font-bold mb-6 text-teal-600">Ma carte</h1>
      <button @click="goToCreate" class="mb-6 px-4 py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition">
        + Ajouter un cocktail
      </button>
  
      <div v-if="loading" class="text-gray-500">Chargement…</div>
      <div v-else-if="cocktails.length === 0" class="text-red-500">Aucun cocktail trouvé.</div>
      <div
        v-else
        class="grid gap-6 w-full max-w-6xl grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4"
      >
        <div v-for="c in cocktails" :key="c.id" class="bg-white rounded shadow p-4 flex flex-col">
          <img :src="c.imageUrl" alt="" class="w-full h-40 object-cover rounded" />
          <h2 class="text-xl font-semibold mt-2">{{ c.name }}</h2>
          <p class="text-gray-600 text-sm line-clamp-3">{{ c.description }}</p>
          <div class="mt-auto flex gap-2 justify-end">
            <router-link
              :to="`/barman/cocktails/${c.id}/edit`"
              class="px-3 py-1 bg-teal-100 text-teal-700 rounded hover:bg-teal-200 transition text-sm"
            >
              Modifier
            </router-link>
            <button
              @click="deleteCocktail(c.id)"
              class="px-3 py-1 bg-red-100 text-red-700 rounded hover:bg-red-200 transition text-sm"
            >
              Supprimer
            </button>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { api } from '../services/api'
  
  const cocktails = ref<Array<any>>([])
  const loading = ref(true)
  const router = useRouter()
  
  async function fetchCocktails() {
    loading.value = true
    try {
      cocktails.value = await api.get('/cocktails', {}, true)
    } catch (e) {
      console.error('Erreur chargement cocktails:', e)
    } finally {
      loading.value = false
    }
  }
  
  onMounted(fetchCocktails)
  
  function goToCreate() {
    router.push('/barman/cocktails/create')
  }
  
  async function deleteCocktail(id: number) {
    if (confirm('Confirmez-vous la suppression de ce cocktail ?')) {
      try {
        await api.delete(`/cocktails/${id}`, {}, true)
        cocktails.value = cocktails.value.filter(c => c.id !== id)
      } catch (e) {
        alert('Erreur lors de la suppression du cocktail.')
        console.error('Erreur suppression cocktail:', e)
      }
    }
  }
  </script>
  
  <style scoped>
  .line-clamp-3 {
    display: -webkit-box;
    -webkit-box-orient: vertical;
    overflow: hidden;
    -webkit-line-clamp: 3;
  }
  </style>
  