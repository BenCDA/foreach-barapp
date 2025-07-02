<template>
    <div class="min-h-screen bg-gray-50 p-6">
        <div class="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow space-y-6">
            <img v-if="cocktail" :src="cocktail.imageUrl" :alt="cocktail.name"
                class="w-full max-h-96 object-cover rounded" />

            <h1 class="text-3xl font-bold mt-4">{{ cocktail?.name }}</h1>
            <p class="text-gray-600 mb-4">{{ cocktail?.description }}</p>

            <div v-if="sizePrices.length > 0" class="mb-6">
                <label class="font-semibold mb-2 block">Choisissez une taille :</label>
                <div class="flex gap-4">
                    <label v-for="sp in sizePrices" :key="sp.sizeId" class="flex items-center cursor-pointer">
                        <input type="radio" :value="sp.sizeId" v-model="selectedSizeId" class="mr-2" />
                        <span class="font-medium">{{ sp.sizeLabel }}</span>
                        <span class="ml-2 text-gray-500">{{ sp.price }} €</span>
                    </label>
                </div>
            </div>

            <button :disabled="!selectedSizeId || loading" @click="addToCart"
                class="w-full py-3 bg-teal-400 hover:bg-teal-500 text-white font-semibold rounded-lg transition">
                Ajouter au panier
                <span v-if="currentPrice">({{ currentPrice }} €)</span>
            </button>

            <div v-if="successMsg" class="mt-3 text-green-600 text-center font-medium">
                {{ successMsg }}
            </div>
            <div v-if="errorMsg" class="mt-3 text-red-600 text-center font-medium">
                {{ errorMsg }}
            </div>
        </div>
    </div>
</template>
  
<script lang="ts" setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { api } from '../services/api'

interface Cocktail {
    id: number
    name: string
    description: string
    imageUrl: string
}

interface SizePrice {
    id: number
    cocktailId: number
    sizeId: number
    sizeLabel: string // ou libelle selon backend, adapte si besoin
    price: number
}

const route = useRoute()
const cocktailId = Number(route.params.id)

const cocktail = ref<Cocktail | null>(null)
const sizePrices = ref<SizePrice[]>([])
const selectedSizeId = ref<number | null>(null)
const loading = ref(false)
const successMsg = ref('')
const errorMsg = ref('')

const currentPrice = computed(() => {
    const sp = sizePrices.value.find((sp) => sp.sizeId === selectedSizeId.value)
    return sp?.price || ''
})

async function loadData() {
    // Charger les infos cocktail
    try {
        cocktail.value = await api.get<Cocktail>(`/cocktails/${cocktailId}`)
    } catch (e) {
        errorMsg.value = "Erreur chargement du cocktail"
        return
    }
    // Charger les tailles/prix disponibles
    try {
        const prices = await api.get<SizePrice[]>(
            `/cocktail-size-prices/by-cocktail/${cocktailId}`, {}, true
        )
        sizePrices.value = prices
        if (prices.length) selectedSizeId.value = prices[0].sizeId
    } catch (e) {
        errorMsg.value = "Erreur chargement des tailles/prix"
    }
}
onMounted(loadData)

async function addToCart() {
    errorMsg.value = ''
    successMsg.value = ''
    loading.value = true
    try {
        // Tu peux ajouter quantity (ici 1) ou personnaliser
        await api.post('/cart/add', {
            cocktailId: cocktailId,
            sizeId: selectedSizeId.value,
            quantity: 1
        }, {}, true)
        successMsg.value = "Cocktail ajouté au panier !"
        // Optionnel : reload panier, rediriger, etc.
    } catch (e: any) {
        errorMsg.value = e.message || 'Erreur lors de l\'ajout au panier'
    } finally {
        loading.value = false
    }
}
</script>
  