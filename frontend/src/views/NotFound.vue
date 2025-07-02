<!-- src/views/NotFound.vue -->
<template>
    <div class="min-h-screen flex flex-col items-center justify-center bg-gray-50 p-4">
      <h1 class="text-4xl font-bold text-gray-700 mb-4">404</h1>
      <p class="text-lg mb-6">Page non trouvée</p>
      <router-link to="/" class="px-4 py-2 bg-teal-600 text-white rounded hover:bg-teal-700 transition">
        Retour à l’accueil
      </router-link>
    </div>
  </template>
  
  <script lang="ts" setup>
  // rien de particulier ici
  </script>
  