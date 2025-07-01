<template>
    <div class="min-h-screen flex items-center justify-center bg-gray-50">
      <div class="w-full max-w-md bg-white p-8 rounded-lg shadow">
        <h2 class="text-2xl font-bold mb-6 text-teal-600">Créer un compte</h2>
        <form @submit.prevent="submitRegister" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-1">Nom</label>
            <input v-model="name" type="text" required
                   class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400" />
          </div>
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
          <div>
            <label class="block text-sm font-medium mb-1">Rôle</label>
            <select v-model="role"
                    class="w-full px-3 py-2 border rounded focus:outline-none focus:ring-2 focus:ring-teal-400">
              <option value="CLIENT">Client</option>
              <option value="BARMAN">Barman</option>
            </select>
          </div>
          <button type="submit"
                  class="w-full py-2 bg-teal-600 text-white font-semibold rounded hover:bg-teal-700 transition">
            S’inscrire
          </button>
        </form>
        <p class="mt-4 text-center text-sm">
          Déjà inscrit ?
          <router-link to="/login" class="text-teal-600 hover:underline">Connectez-vous</router-link>
        </p>
        <p v-if="error" class="mt-2 text-red-500 text-sm">{{ error }}</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        name: '',
        email: '',
        password: '',
        role: 'CLIENT',
        error: null,
      };
    },
    methods: {
      async submitRegister() {
        this.error = null;
        try {
          const res = await fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
              name: this.name,
              email: this.email,
              password: this.password,
              role: this.role,
            }),
          });
          if (!res.ok) throw new Error('Échec de l’inscription');
          alert('Inscription réussie ! Connectez-vous.');
          this.$router.push('/login');
        } catch (e) {
          this.error = e.message;
        }
      },
    },
  };
  </script>
  