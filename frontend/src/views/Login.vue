<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="w-full max-w-md bg-white p-8 rounded-lg shadow">
      <h2 class="text-2xl font-bold mb-6 text-teal-600">Connexion</h2>
      <form @submit.prevent="submitLogin" class="space-y-4">
        <div>
          <label class="block text-sm font-medium mb-1">Email</label>
          <input v-model="email" type="email" required
                 class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400" />
        </div>
        <div>
          <label class="block text-sm font-medium mb-1">Mot de passe</label>
          <input v-model="password" type="password" required
                 class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400" />
        </div>
        <button type="submit"
                class="w-full py-2 bg-teal-600 text-white font-semibold rounded hover:bg-teal-700 transition">
          Se connecter
        </button>
      </form>
      <p class="mt-4 text-center text-sm">
        Pas encore de compte ?
        <router-link to="/register" class="text-teal-600 hover:underline">Inscrivez-vous</router-link>
      </p>
      <p v-if="error" class="mt-2 text-red-500 text-sm">{{ error }}</p>
    </div>
  </div>
</template>

<script>
import { api } from '../services/api'; // *** MODIFICATION : Importe le service API ***

export default {
  data() {
    return {
      email: '',
      password: '',
      error: null,
    };
  },
  methods: {
    async submitLogin() {
      this.error = null;
      try {
        // *** MODIFICATION : Utilise api.post au lieu de fetch direct ***
        // L'endpoint est maintenant juste '/auth/login' car la baseURL est déjà '/api'
        const { token } = await api.post('/auth/login', {
          email: this.email,
          password: this.password
        }, {}, false); // Le dernier paramètre 'false' indique qu'on n'a pas besoin de token pour cette requête

        localStorage.setItem('jwt', token);
        this.$router.push('/'); // page d’accueil ou tableau de bord
      } catch (e) {
        this.error = e.message;
        console.error("Login failed:", e); // Ajout pour un meilleur débogage
      }
    },
  },
};
</script>