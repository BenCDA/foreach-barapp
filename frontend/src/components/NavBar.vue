<template>
    <nav class="bg-white shadow py-4">
      <div class="container mx-auto flex flex-col sm:flex-row sm:items-center sm:justify-between px-4 space-y-4 sm:space-y-0">
        <router-link to="/" class="text-2xl font-bold text-teal-600">
          Bar’App
        </router-link>
  
        <ul v-if="isAuthenticated" class="flex flex-col sm:flex-row sm:items-center sm:space-x-6 space-y-2 sm:space-y-0">
          <li>
            <router-link to="/" class="hover:text-teal-600">
              Accueil
            </router-link>
          </li>
  
          <template v-if="role === 'ROLE_CLIENT'">
            <li>
              <router-link to="/cart" class="hover:text-teal-600">
                Panier
              </router-link>
            </li>
            <li>
              <router-link to="/orders" class="hover:text-teal-600">
                Mes commandes
              </router-link>
            </li>
          </template>
  
          <template v-else-if="role === 'ROLE_BARMAN'">
            <li>
              <router-link to="/dashboard" class="hover:text-teal-600">
                Commandes à traiter
              </router-link>
            </li>
            <!-- <li>
              <router-link to="/barman/cocktails" class="hover:text-teal-600">
                Ma carte
              </router-link>
            </li> -->
          </template>
  
          <li>
            <button @click="logout" class="hover:text-red-500">
              Déconnexion
            </button>
          </li>
        </ul>
      </div>
    </nav>
  </template>
  
  <script lang="ts" setup>
  import { computed, ref, watch } from 'vue'
  import { useRouter, useRoute } from 'vue-router'
  
  const router = useRouter()
  const route = useRoute()
  
  const role = ref<string | null>(localStorage.getItem('role'))
  
  // Surveillance des changements de route pour mettre à jour automatiquement le rôle
  watch(
    () => route.path,
    () => {
      role.value = localStorage.getItem('role')
    }
  )
  
  const isAuthenticated = computed(() => !!role.value && !['/login', '/register'].includes(route.path))
  
  function logout() {
    localStorage.removeItem('jwt')
    localStorage.removeItem('role')
    role.value = null // force une actualisation immédiate
    router.push('/login')
  }
  </script>
  