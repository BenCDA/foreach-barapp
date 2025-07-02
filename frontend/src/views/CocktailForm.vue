<template>
    <div class="min-h-screen bg-gray-50 p-6 flex flex-col items-center">
      <h1 class="text-3xl font-bold text-teal-600 mb-6">
        {{ isEdit ? 'Modifier' : 'Créer' }} un cocktail
      </h1>
  
      <form @submit.prevent="submitCocktail" class="w-full max-w-2xl space-y-6 bg-white p-6 shadow">
        <!-- Nom -->
        <div>
          <label class="block font-medium mb-1">Nom</label>
          <input v-model="form.name" type="text" class="w-full border px-3 py-2" required />
        </div>
  
        <!-- Description -->
        <div>
          <label class="block font-medium mb-1">Description</label>
          <textarea v-model="form.description" class="w-full border px-3 py-2" required />
        </div>
  
        <!-- Image -->
        <div>
          <label class="block font-medium mb-1">URL de l’image</label>
          <input v-model="form.imageUrl" type="url" class="w-full border px-3 py-2" />
        </div>
  
        <!-- Catégorie -->
        <div>
          <label class="block font-medium mb-1">Catégorie</label>
          <select v-model="form.categoryId" class="w-full border px-3 py-2" required>
            <option value="">-- Catégorie --</option>
            <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
          </select>
        </div>
  
        <!-- Ingrédients -->
        <div>
          <label class="block font-medium mb-2">Ingrédients</label>
          <div class="space-y-2">
            <div v-for="(ing, index) in form.ingredients" :key="index" class="flex items-center gap-2">
              <input v-model="ing.name" list="ingredient-options" placeholder="Ingrédient" class="flex-1 border px-2 py-1" />
              <input v-model="ing.quantity" placeholder="Quantité" class="flex-1 border px-2 py-1" />
              <button type="button" @click="removeIngredient(index)" class="text-red-500 font-bold">&#x2715;</button>
            </div>
            <datalist id="ingredient-options">
              <option v-for="opt in ingredientSuggestions" :key="opt.id" :value="opt.name" />
            </datalist>
            <button type="button" @click="addIngredient" class="text-sm text-teal-600 hover:underline">
              + Ajouter un ingrédient
            </button>
          </div>
        </div>
  
        <!-- Prix par taille -->
        <div>
          <label class="block font-medium mb-2">Prix par taille</label>
          <div class="grid grid-cols-3 gap-4">
            <div v-for="size in ['S', 'M', 'L']" :key="size">
              <label class="block text-sm font-medium">{{ size }}</label>
              <input
                v-model.number="form.prices[sizeIdMap[size]]"
                type="number"
                min="0"
                class="w-full border px-2 py-1"
                placeholder="Prix"
              />
            </div>
          </div>
        </div>
  
        <!-- Bouton -->
        <div>
          <button type="submit" class="w-full bg-teal-600 text-white font-semibold py-2 hover:bg-teal-700 transition">
            {{ isEdit ? 'Modifier' : 'Créer' }} le cocktail
          </button>
        </div>
      </form>
    </div>
  </template>
  
  <script lang="ts" setup>
  import { ref, onMounted, computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { api } from '../services/api'
  
  const router = useRouter()
  const route = useRoute()
  const isEdit = computed(() => !!route.params.id)
  
  const form = ref({
    name: '',
    description: '',
    imageUrl: '',
    categoryId: null as number | null,
    ingredients: [{ name: '', quantity: '' }],
    prices: { 1: null, 2: null, 3: null }
  })
  
  const categories = ref<{ id: number; name: string }[]>([])
  const ingredientSuggestions = ref<{ id: number; name: string }[]>([])
  const sizeIdMap = { S: 1, M: 2, L: 3 }
  
  onMounted(async () => {
    categories.value = await api.get('/categories', {}, true)
    ingredientSuggestions.value = await api.get('/ingredients', {}, true)
  })
  
  function addIngredient() {
    form.value.ingredients.push({ name: '', quantity: '' })
  }
  
  function removeIngredient(index: number) {
    form.value.ingredients.splice(index, 1)
  }
  
  async function submitCocktail() {
    let cocktailId: number | null = null
  
    try {
      const cocktailPayload = {
        name: form.value.name,
        description: form.value.description,
        imageUrl: form.value.imageUrl,
        categoryId: form.value.categoryId
      }
  
      console.log("🎯 Création cocktail:", cocktailPayload)
  
      if (isEdit.value) {
        await api.put(`/cocktails/${route.params.id}`, cocktailPayload, {}, true)
        cocktailId = Number(route.params.id)
      } else {
        const created = await api.post('/cocktails', cocktailPayload, {}, true)
        cocktailId = created.id
      }
    } catch (err) {
      console.error("❌ Erreur création cocktail :", err)
      alert("Erreur lors de la création du cocktail.")
      return
    }
  
    try {
      for (const ing of form.value.ingredients) {
        let ingredientId = ingredientSuggestions.value.find(i => i.name.toLowerCase() === ing.name.toLowerCase())?.id
        if (!ingredientId) {
          const newIng = await api.post('/ingredients', { name: ing.name }, {}, true)
          ingredientId = newIng.id
        }
  
        console.log("🧪 Ajout ingredient:", { cocktailId, ingredientId, quantity: ing.quantity })
  
        await api.post('/cocktail-ingredients', {
          cocktailId,
          ingredientId,
          quantity: ing.quantity
        }, {}, true)
      }
    } catch (err) {
      console.error("❌ Erreur ingrédients :", err)
      alert("Erreur lors de l'ajout des ingrédients.")
      return
    }
  
    try {
      for (const sizeId of Object.keys(form.value.prices)) {
        const prix = form.value.prices[Number(sizeId)]
        if (prix !== null) {
          await api.post('/cocktail-size-prices', {
            cocktailId,
            sizeId: Number(sizeId),
            price: prix
          }, {}, true)
        }
      }
    } catch (err) {
      console.error("❌ Erreur prix taille :", err)
      alert("Erreur lors de l'ajout des prix par taille.")
      return
    }
  
    router.push('/barman/cocktails')
  }
  </script>
  
  