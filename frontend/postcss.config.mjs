// frontend/postcss.config.mjs
import tailwindPlugin from '@tailwindcss/postcss'
import autoprefixer from 'autoprefixer'

export default {
  plugins: [
    // le plugin Tailwind CSS pour PostCSS (package @tailwindcss/postcss)
    tailwindPlugin(),
    // autoprefixer pour la compatibilité navigateurs
    autoprefixer(),
  ],
}
