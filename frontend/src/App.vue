<!-- src/App.vue -->
<template>
  <!-- Affiche la page correspondant à la route actuelle -->
  <router-view />
</template>

<script lang="ts">
// On utilise defineComponent pour typer correctement notre composant racine
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'App',
})
</script>

<style>
/* Styles globaux éventuels */
</style>
