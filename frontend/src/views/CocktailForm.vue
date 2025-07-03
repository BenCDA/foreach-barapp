<template>
  <div class="min-h-screen bg-gray-50 p-6 flex flex-col items-center">
    <h1 class="text-3xl font-bold text-teal-600 mb-6">
      {{ isEdit ? 'Modifier' : 'Créer' }} un cocktail
    </h1>

    <form @submit.prevent="submitCocktail" class="w-full max-w-2xl space-y-6 bg-white p-6 shadow">
      <!-- Nom, description, image, catégorie identiques -->
      <div>
        <label class="block mb-1">Nom</label>
        <input v-model="form.name" required class="w-full border px-3 py-2" />
      </div>
      <div>
        <label class="block mb-1">Description</label>
        <textarea v-model="form.description" required class="w-full border px-3 py-2"></textarea>
      </div>
      <div>
        <label class="block mb-1">URL de l’image</label>
        <input v-model="form.imageUrl" type="url" class="w-full border px-3 py-2" />
      </div>
      <div>
        <label class="block mb-1">Catégorie</label>
        <select v-model="form.categoryId" required class="w-full border px-3 py-2">
          <option value="">-- Catégorie --</option>
          <option v-for="cat in categories" :key="cat.id" :value="cat.id">{{ cat.name }}</option>
        </select>
      </div>

      <!-- Ingrédients -->
      <div>
        <label class="block mb-2">Ingrédients</label>
        <div class="space-y-2">
          <div v-for="(ing, i) in form.ingredients" :key="ing.tempId" class="flex gap-2">
            <input v-model="ing.name" list="ingredient-options" placeholder="Ingrédient"
              class="flex-1 border px-2 py-1" />
            <input v-model="ing.quantity" placeholder="Quantité" class="flex-1 border px-2 py-1" />
            <button type="button" @click="removeIngredient(i)" class="text-red-500">✕</button>
          </div>
          <datalist id="ingredient-options">
            <option v-for="opt in ingredientSuggestions" :key="opt.id" :value="opt.name" />
          </datalist>
          <button type="button" @click="addIngredient" class="text-teal-600 text-sm hover:underline">
            + Ajouter un ingrédient
          </button>
        </div>
      </div>

      <!-- Prix par taille (AFFICHAGE ORDONNÉ S, M, L) -->
      <div>
        <label class="block mb-2">Prix par taille</label>
        <div class="grid grid-cols-3 gap-4">
          <div v-for="size in sortedSizes" :key="size.label">
            <label class="block mb-1">{{ size.label }}</label>
            <input v-model.number="form.prices[size.id].prix" type="number" min="0"
              :placeholder="form.prices[size.id].id ? form.prices[size.id].prix : 'Prix'"
              class="w-full border px-2 py-1" />
          </div>
        </div>
      </div>

      <button type="submit" class="w-full bg-teal-600 text-white py-2 font-semibold hover:bg-teal-700">
        {{ isEdit ? 'Modifier' : 'Créer' }} le cocktail
      </button>
    </form>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { api } from '../services/api'

const router = useRouter()
const route = useRoute()
const cocktailId = route.params.id ? Number(route.params.id) : null
const isEdit = computed(() => cocktailId !== null)

interface IngredientForm {
  id?: number
  tempId: string
  name: string
  quantity: string
}
interface PriceForm {
  id?: number
  prix: number | null
}

const form = ref({
  name: '',
  description: '',
  imageUrl: '',
  categoryId: null as number | null,
  ingredients: [] as IngredientForm[],
  prices: {} as Record<number, PriceForm>
})

const categories = ref<{ id: number; name: string }[]>([])
const ingredientSuggestions = ref<{ id: number; name: string }[]>([])
const sizes = ref<{ id: number; label: string }[]>([])

// Trier les tailles dans l'ordre S, M, L quel que soit leur id ou leur ordre en BDD/API
const sortedSizes = computed(() => {
  const order = ['S', 'M', 'L']
  return sizes.value.slice().sort((a, b) =>
    order.indexOf(a.label) - order.indexOf(b.label)
  )
})

// Utilitaire pour générer un tempId unique
function uuid() { return crypto.randomUUID() }

onMounted(async () => {
  categories.value = await api.get('/categories', {}, true)
  ingredientSuggestions.value = await api.get('/ingredients', {}, true)
  sizes.value = await api.get('/sizes', {}, true)

  // Initialiser prix par taille
  sizes.value.forEach(s => {
    form.value.prices[s.id] = { id: undefined, prix: null }
  })

  if (isEdit.value && cocktailId) {
    // Charger le cocktail
    const c = await api.get(`/cocktails/${cocktailId}`, {}, true)
    form.value.name = c.name
    form.value.description = c.description
    form.value.imageUrl = c.imageUrl
    form.value.categoryId = c.category.id

    // Charger les ingrédients existants
    const cis = await api.get(`/cocktail-ingredients/by-cocktail/${cocktailId}`, {}, true)
    form.value.ingredients = cis.map((ci: any) => ({
      id: ci.id,
      tempId: uuid(),
      name: ci.ingredient.name,
      quantity: ci.quantite
    }))

    // Charger les prix existants
    const cps = await api.get(`/cocktail-size-prices/by-cocktail/${cocktailId}`, {}, true)
    cps.forEach((cp: any) => {
      form.value.prices[cp.taille.id] = {
        id: cp.id,
        prix: cp.prix
      }
    })
  } else {
    // Nouveau cocktail : un seul ingrédient vide
    form.value.ingredients.push({ tempId: uuid(), name: '', quantity: '' })
  }
})

function addIngredient() {
  form.value.ingredients.push({ tempId: uuid(), name: '', quantity: '' })
}
function removeIngredient(i: number) {
  form.value.ingredients.splice(i, 1)
}

async function submitCocktail() {
  try {
    // 1) create or update cocktail principal
    const payload = {
      name: form.value.name,
      description: form.value.description,
      imageUrl: form.value.imageUrl,
      categoryId: form.value.categoryId
    }
    let id = cocktailId
    if (isEdit.value && id) {
      await api.put(`/cocktails/${id}`, payload, {}, true)
    } else {
      const created: any = await api.post('/cocktails', payload, {}, true)
      id = created.id
    }

    // 2) Gérer les ingrédients
    const existing = new Map<number, IngredientForm>()
    form.value.ingredients.forEach(ing => {
      if (ing.id) existing.set(ing.id, ing)
    })

    //   a) Mettre à jour ou créer
    for (const ing of form.value.ingredients) {
      if (ing.id) {
        await api.put(`/cocktail-ingredients/${ing.id}`, {
          cocktailId: id,
          ingredientId: ingredientSuggestions.value.find(i => i.name === ing.name)!.id,
          quantite: ing.quantity
        }, {}, true)
        existing.delete(ing.id)
      } else {
        const ingrObj = ingredientSuggestions.value.find(i => i.name === ing.name)
        const ingId = ingrObj ? ingrObj.id :
          (await api.post('/ingredients', { name: ing.name }, {}, true)).id
        await api.post('/cocktail-ingredients', {
          cocktailId: id,
          ingredientId: ingId,
          quantite: ing.quantity
        }, {}, true)
      }
    }
    //   b) Supprimer ceux qui restent dans `existing`
    for (const [oldId] of existing) {
      await api.delete(`/cocktail-ingredients/${oldId}`, {}, true)
    }

    // 3) Gérer les prix par taille (même logique)
    const existingPrices = new Map<number, PriceForm>()
    Object.entries(form.value.prices).forEach(([sizeId, p]) => {
      if (p.id) existingPrices.set(p.id, { ...p })
    })

    for (const [sizeIdStr, p] of Object.entries(form.value.prices)) {
      const sizeId = Number(sizeIdStr)
      if (p.id) {
        await api.put(`/cocktail-size-prices/${p.id}`, {
          cocktailId: id,
          sizeId,
          price: p.prix
        }, {}, true)
        existingPrices.delete(p.id)
      } else if (p.prix !== null) {
        await api.post('/cocktail-size-prices', {
          cocktailId: id,
          sizeId,
          price: p.prix
        }, {}, true)
      }
    }
    // Supprimer anciens prix non repris
    for (const oldP of existingPrices.keys()) {
      await api.delete(`/cocktail-size-prices/${oldP}`, {}, true)
    }

    router.push('/barman/cocktails')
  } catch (e) {
    console.error('Erreur lors de la soumission', e)
    alert('Erreur lors de la sauvegarde.')
  }
}
</script>
