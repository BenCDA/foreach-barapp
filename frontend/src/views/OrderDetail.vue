<template>
    <div class="min-h-screen bg-gray-50 p-4 flex flex-col items-center">
      <h1 class="text-3xl font-bold mb-6 text-teal-600">Détails de la commande</h1>
  
      <div v-if="loading" class="text-gray-500">Chargement…</div>
  
      <div v-else class="w-full max-w-2xl bg-white rounded-lg shadow p-6 space-y-6">
        <div v-for="oc in cocktails" :key="oc.id" class="space-y-2">
          <h2 class="text-xl font-semibold">{{ oc.name }} ({{ oc.size }})</h2>
          <p>Status : {{ formatStep(oc.step) }}</p>
          <button
            @click="advance(oc.id)"
            :disabled="oc.step === 'DONE'"
            class="px-3 py-1 bg-teal-600 text-white rounded hover:bg-teal-700 transition text-sm"
          >
            {{ oc.step === 'DONE' ? 'Terminé' : 'Étape suivante' }}
          </button>
          <hr />
        </div>
      </div>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import { api } from '../services/api'
  
  interface OrderedCocktail {
    id: number
    name: string
    size: string
    step: string
  }
  
  const route = useRoute()
  const loading = ref(true)
  const cocktails = ref<OrderedCocktail[]>([])
  
  onMounted(async () => {
    try {
      const id = route.params.id
      cocktails.value = await api.get<OrderedCocktail[]>(`/orders/${id}`, {}, true)
    } catch (e) {
      console.error(e)
    } finally {
      loading.value = false
    }
  })
  
  async function advance(ocId: number) {
    try {
      const updated = await api.patch(
        `/order-cocktails/${ocId}/step`,
        {},
        {},
        true
      )
      // on recharge
      cocktails.value = await api.get<OrderedCocktail[]>(
        `/orders/${route.params.id}`,
        {},
        true
      )
    } catch (e) {
      console.error(e)
    }
  }
  
  function formatStep(s: string) {
    return {
      INGREDIENTS: 'Préparation des ingrédients',
      ASSEMBLY: 'Assemblage',
      PLATING: 'Dressage',
      DONE: 'Terminée'
    }[s] || s
  }
  </script>
  