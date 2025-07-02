<template>
  <div class="bg-white p-4 rounded-lg shadow flex flex-col">
    <!-- ton image, titre, description... -->
    <img ... />
    <h3>…</h3>
    <p>…</p>

    <div class="mt-auto space-y-2">
      <!-- bouton pour aller sur la fiche détail -->
      <router-link
        :to="`/cocktails/${cocktail.id}`"
        class="w-full py-2 bg-gray-200 text-gray-700 rounded hover:bg-gray-300"
      >
        Voir
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Cocktail } from '../types'
defineProps<{ cocktail: Cocktail }>()
</script>
