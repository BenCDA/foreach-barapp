<template>
  <div class="min-h-screen bg-gray-50 p-6 flex flex-col items-center">
    <h1 class="text-3xl font-bold text-teal-600 mb-6">
      {{ isEdit ? 'Modifier' : 'Créer' }} un cocktail
    </h1>

    <form @submit.prevent="submitCocktail" class="w-full max-w-2xl space-y-6 bg-white p-6 shadow">
      <!-- Nom, description, image -->
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

      <!-- Catégorie + création à la volée -->
      <div>
        <label class="block mb-1">Catégorie</label>
        <div class="flex gap-2 items-center">
          <!-- Select existantes -->
          <select
            v-if="!addingCategory"
            v-model="form.categoryId"
            required
            class="flex-1 border px-3 py-2"
          >
            <option value="">-- Catégorie --</option>
            <option v-for="cat in categories" :key="cat.id" :value="cat.id">
              {{ cat.name }}
            </option>
          </select>

          <!-- Input création -->
          <input
            v-if="addingCategory"
            v-model="newCategoryName"
            placeholder="Nouvelle catégorie"
            required
            class="flex-1 border px-3 py-2"
          />

          <!-- Bouton bascule -->
          <button
            type="button"
            @click="toggleCategoryInput"
            class="text-teal-600 text-sm hover:underline"
          >
            {{ addingCategory ? 'Annuler' : '+ Catégorie' }}
          </button>
        </div>
      </div>

      <!-- Ingrédients (identique) -->
      <div>
        <label class="block mb-2">Ingrédients</label>
        <div class="space-y-2">
          <div v-for="(ing, i) in form.ingredients" :key="ing.tempId" class="flex gap-2">
            <input
              v-model="ing.name"
              list="ingredient-options"
              placeholder="Ingrédient"
              class="flex-1 border px-2 py-1"
            />
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

      <!-- Prix par taille (identique) -->
      <div>
        <label class="block mb-2">Prix par taille</label>
        <div class="grid grid-cols-3 gap-4">
          <div v-for="size in sortedSizes" :key="size.id">
            <label class="block mb-1">{{ size.label }}</label>
            <input
              v-model.number="form.prices[size.id].prix"
              type="number"
              min="0"
              :placeholder="form.prices[size.id].id ? form.prices[size.id].prix : 'Prix'"
              class="w-full border px-2 py-1"
            />
          </div>
        </div>
      </div>

      <button
        type="submit"
        class="w-full bg-teal-600 text-white py-2 font-semibold hover:bg-teal-700"
      >
        {{ isEdit ? 'Modifier' : 'Créer' }} le cocktail
      </button>
    </form>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { api } from '../services/api'

// Router / mode edit vs create
const router = useRouter()
const route = useRoute()
const cocktailId = route.params.id ? Number(route.params.id) : null
const isEdit = computed(() => cocktailId !== null)

// Form state
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

// Données externes
const categories = ref<{ id: number; name: string }[]>([])
const ingredientSuggestions = ref<{ id: number; name: string }[]>([])
const sizes = ref<{ id: number; label: string }[]>([])

// Tri des tailles
const sortedSizes = computed(() => {
  const order = ['S', 'M', 'L']
  return sizes.value.slice().sort((a, b) => order.indexOf(a.label) - order.indexOf(b.label))
})

// Pour création de catégorie
const addingCategory = ref(false)
const newCategoryName = ref('')

function toggleCategoryInput() {
  addingCategory.value = !addingCategory.value
  if (!addingCategory.value) {
    // annule la création
    newCategoryName.value = ''
  }
}

// Génère un tempId
function uuid() {
  return crypto.randomUUID()
}

onMounted(async () => {
  // Chargement des listes
  categories.value = await api.get('/categories', {}, true)
  ingredientSuggestions.value = await api.get('/ingredients', {}, true)
  sizes.value = await api.get('/sizes', {}, true)

  // Init prix
  sizes.value.forEach(s => {
    form.value.prices[s.id] = { id: undefined, prix: null }
  })

  if (isEdit.value && cocktailId) {
    // Chargement en mode édition
    const c = await api.get(`/cocktails/${cocktailId}`, {}, true)
    form.value.name = c.name
    form.value.description = c.description
    form.value.imageUrl = c.imageUrl
    form.value.categoryId = c.category.id

    const cis = await api.get(`/cocktail-ingredients/by-cocktail/${cocktailId}`, {}, true)
    form.value.ingredients = cis.map((ci: any) => ({
      id: ci.id,
      tempId: uuid(),
      name: ci.ingredient.name,
      quantity: ci.quantite
    }))

    const cps = await api.get(`/cocktail-size-prices/by-cocktail/${cocktailId}`, {}, true)
    cps.forEach((cp: any) => {
      form.value.prices[cp.taille.id] = { id: cp.id, prix: cp.prix }
    })
  } else {
    // Nouveau cocktail
    form.value.ingredients.push({ tempId: uuid(), name: '', quantity: '' })
  }
})

// Gestion ingrédients
function addIngredient() {
  form.value.ingredients.push({ tempId: uuid(), name: '', quantity: '' })
}
function removeIngredient(i: number) {
  form.value.ingredients.splice(i, 1)
}

// Soumission
async function submitCocktail() {
  try {
    // Si on ajoute une catégorie, la créer d'abord
    if (addingCategory.value && newCategoryName.value.trim()) {
      const created = await api.post(
        '/categories',
        { name: newCategoryName.value.trim() },
        {},
        true
      )
      // Met à jour la liste et l'id choisi
      categories.value.push(created)
      form.value.categoryId = created.id
    }

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

    // Gère ensuite ingrédients et prix (identique à l’existant)...
    // [Votre logique d’update/post/delete ici]

    router.push('/barman/cocktails')
  } catch (e) {
    console.error('Erreur lors de la soumission', e)
    alert('Erreur lors de la sauvegarde.')
  }
}
</script>
