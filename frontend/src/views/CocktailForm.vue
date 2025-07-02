<template>
    <div class="min-h-screen bg-gray-50 p-6 flex flex-col items-center">
      <h1 class="text-3xl font-bold text-teal-600 mb-6">Ajouter un nouveau cocktail</h1>
  
      <form @submit.prevent="handleSubmit" class="bg-white shadow p-6 rounded w-full max-w-3xl">
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Nom du cocktail</label>
          <input v-model="cocktail.name" required class="mt-1 w-full border rounded px-3 py-2" />
        </div>
  
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Description</label>
          <textarea v-model="cocktail.description" required class="mt-1 w-full border rounded px-3 py-2" />
        </div>
  
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700">Image (URL)</label>
          <input v-model="cocktail.imageUrl" type="url" required class="mt-1 w-full border rounded px-3 py-2" />
        </div>
  
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-2">Ingrédients</label>
          <div v-for="(ing, index) in ingredients" :key="index" class="flex gap-2 mb-2">
            <select v-model="ing.ingredientId" class="flex-1 border rounded px-3 py-2">
              <option disabled value="">Choisir...</option>
              <option v-for="i in allIngredients" :key="i.id" :value="i.id">{{ i.name }}</option>
            </select>
            <input v-model="ing.quantity" placeholder="Quantité" class="w-1/3 border rounded px-3 py-2" />
            <button type="button" @click="ingredients.splice(index, 1)" class="text-red-500">Suppr</button>
          </div>
          <button type="button" @click="ingredients.push({ ingredientId: '', quantity: '' })"
            class="mt-2 text-teal-600 hover:underline">+ Ajouter un ingrédient</button>
        </div>
  
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-2">Prix par taille</label>
          <div v-for="s in sizes" :key="s.id" class="flex items-center gap-3 mb-2">
            <span class="w-12">{{ s.label }}</span>
            <input v-model.number="prices[s.id]" type="number" min="0" placeholder="Prix en €"
              class="flex-1 border rounded px-3 py-2" />
          </div>
        </div>
  
        <div class="flex justify-end">
          <button type="submit" class="bg-teal-600 text-white px-6 py-2 rounded hover:bg-teal-700 transition">
            Créer
          </button>
        </div>
      </form>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted } from 'vue'
  import { useRouter } from 'vue-router'
  import { api } from '../services/api'
  
  const router = useRouter()
  const cocktail = ref({ name: '', description: '', imageUrl: '', categoryId: 1 })
  const ingredients = ref<{ ingredientId: number | ''; quantity: string }[]>([])
  const prices = ref<Record<number, number>>({})
  const allIngredients = ref<{ id: number; name: string }[]>([])
  const sizes = ref<{ id: number; label: string }[]>([])
  
  onMounted(async () => {
    allIngredients.value = await api.get('/ingredients', {}, true)
    sizes.value = await api.get('/sizes', {}, true)
  })
  
  async function handleSubmit() {
    try {
      // 1. Créer cocktail
      const created = await api.post('/cocktails', cocktail.value, true)
      const cocktailId = created.id
  
      // 2. Créer les ingredients
      for (const ing of ingredients.value) {
        await api.post('/cocktail-ingredients', {
          cocktailId,
          ingredientId: ing.ingredientId,
          quantite: ing.quantity
        }, true)
      }
  
      // 3. Créer les prix par taille
      for (const sizeId in prices.value) {
        const prix = prices.value[+sizeId]
        if (prix != null) {
          await api.post('/cocktail-size-prices', {
            cocktailId,
            sizeId: +sizeId,
            prix: prix
          }, true)
        }
      }
  
      router.push('/barman/cocktails')
    } catch (e) {
      console.error(e)
    }
  }
  </script>
  