import { createApp } from 'vue'
import App from './App.vue'
import router from './router'      // ← plus d’extension .js
import './index.css'               // Tailwind

createApp(App)
  .use(router)
  .mount('#app')
