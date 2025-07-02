<template>
    <nav class="bg-white shadow py-4">
      <div class="container mx-auto flex flex-col sm:flex-row sm:items-center sm:justify-between px-4 space-y-4 sm:space-y-0">
        <!-- Logo -->
        <router-link to="/" class="text-2xl font-bold text-teal-600">
          Bar’App
        </router-link>
  
        <!-- Liens -->
        <ul class="flex flex-col sm:flex-row sm:items-center sm:space-x-6 space-y-2 sm:space-y-0">
          <!-- Accueil -->
          <li>
            <router-link to="/" class="hover:text-teal-600">
              Accueil
            </router-link>
          </li>
  
          <!-- CLIENT only -->
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
  
          <!-- BARMAN only -->
          <template v-else-if="role === 'ROLE_BARMAN'">
            <li>
              <router-link to="/dashboard" class="hover:text-teal-600">
                Commandes à traiter
              </router-link>
            </li>
            <li>
              <router-link to="/barman/cocktails" class="hover:text-teal-600">
                Ma carte
              </router-link>
            </li>
          </template>
  
          <!-- Déconnexion -->
          <li v-if="role">
            <button @click="logout" class="hover:text-red-500">
              Déconnexion
            </button>
          </li>
        </ul>
      </div>
    </nav>
  </template>
  
  <script lang="ts" setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  
  const router = useRouter()
  const role = ref<string | null>(localStorage.getItem('role'))
  
  function logout() {
    localStorage.removeItem('jwt')
    localStorage.removeItem('role')
    router.push('/login')
  }
  </script>
  