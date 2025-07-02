<template>
    <div
      class="bg-white p-4 rounded-lg shadow hover:shadow-lg transition flex flex-col justify-between"
    >
      <img
        v-if="cocktail.imageUrl"
        :src="cocktail.imageUrl"
        alt="cocktail.name"
        class="w-full h-32 object-cover rounded mb-4"
      />
      <h3 class="text-lg font-semibold mb-2">{{ cocktail.name }}</h3>
      <p class="text-sm text-gray-600 mb-4" v-if="cocktail.description">
        {{ cocktail.description }}
      </p>
      <router-link
        :to="`/cocktails/${cocktail.id}`"
        class="mt-auto inline-block w-full text-center py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition"
      >
        Voir le cocktail
      </router-link>
    </div>
  </template>
  
  <script lang="ts" setup>
  import type { Cocktail } from '../types'
  
  // reçoit en prop un objet Cocktail
  defineProps<{ cocktail: Cocktail }>()
  </script>
  