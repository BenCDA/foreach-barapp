<template>
    <div class="min-h-screen bg-gray-50 p-4 flex flex-col items-center">
      <h1
        class="text-3xl font-bold mb-6"
        :class="isBarman ? 'text-teal-600' : 'text-green-600'"
      >
        {{ isBarman ? 'Ma carte : Cocktails' : `Cocktails de « ${categoryName} »` }}
      </h1>
  
      <!-- BARMAN : bouton d'ajout -->
      <button
        v-if="isBarman"
        @click="goToCreate"
        class="mb-6 px-4 py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition"
      >
        + Ajouter un cocktail
      </button>
  
      <!-- chargement -->
      <div v-if="loading" class="text-gray-500">Chargement…</div>
  
      <!-- grille -->
      <div
        v-else
        class="grid gap-6 w-full
               grid-cols-1
               sm:grid-cols-2
               lg:grid-cols-3
               xl:grid-cols-4
               max-w-6xl"
      >
        <div
          v-for="c in cocktails"
          :key="c.id"
          class="bg-white rounded shadow hover:shadow-lg transition overflow-hidden"
        >
          <img
            :src="c.imageUrl"
            alt=""
            class="w-full h-40 object-cover"
          />
          <div class="p-4 flex flex-col justify-between h-40">
            <div>
              <h2 class="text-xl font-semibold mb-1">{{ c.name }}</h2>
              <p class="text-gray-600 text-sm line-clamp-3">
                {{ c.description }}
              </p>
            </div>
            <div class="mt-3 flex justify-between items-center">
              <!-- CLIENT : voir détail -->
              <router-link
                v-if="!isBarman"
                :to="`/cocktails/${c.id}`"
                class="px-2 py-1 bg-green-100 text-green-700 rounded hover:bg-green-200 transition text-sm"
              >
                Voir
              </router-link>
              <!-- BARMAN : modifier -->
              <router-link
                v-else
                :to="`/barman/cocktails/${c.id}/edit`"
                class="px-2 py-1 bg-teal-100 text-teal-700 rounded hover:bg-teal-200 transition text-sm"
              >
                Modifier
              </router-link>
            </div>
          </div>
        </div>
      </div>
  
      <!-- aucun résultat -->
      <div v-if="!loading && cocktails.length === 0" class="mt-4 text-red-500">
        Aucun cocktail trouvé.
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted, computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { api } from '../services/api'
  
  interface Cocktail {
    id: number
    name: string
    description: string
    imageUrl: string
  }
  
  const route = useRoute()
  const router = useRouter()
  const loading = ref(true)
  const cocktails = ref<Cocktail[]>([])
  const categoryName = ref('')
  
  // role & flag
  const role = localStorage.getItem('role') || ''
  const isBarman = computed(() => role.includes('ROLE_BARMAN'))
  
  onMounted(async () => {
    try {
      if (isBarman.value) {
        // tout voir
        cocktails.value = await api.get<Cocktail[]>('/cocktails', {}, true)
      } else {
        // on récupère la catégorie
        const catId = route.params.id
        const cat = await api.get<{ id: number; name: string }>(
          `/categories/${catId}`,
          {},
          false
        )
        categoryName.value = cat.name
        // puis les cocktails de cette catégorie
        cocktails.value = await api.get<Cocktail[]>(
          `/cocktails?categoryId=${catId}`,
          {},
          false
        )
      }
    } catch (e) {
      console.error(e)
    } finally {
      loading.value = false
    }
  })
  
  function goToCreate() {
    router.push('/barman/cocktails/create')
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
  