import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/Home.vue'
import Category from '../views/Category.vue'
import CocktailDetail from '../views/CocktailDetail.vue'
import CocktailForm from '../views/CocktailForm.vue'
import Cart from '../views/Cart.vue'
import Orders from '../views/Orders.vue'
import Dashboard from '../views/Dashboard.vue'
import OrderDetail from '../views/OrderDetail.vue'
import NotFound from '../views/NotFound.vue'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import OrderProgress from '../views/OrderProgress.vue'

const ROLE_CLIENT = 'ROLE_CLIENT'
const ROLE_BARMAN = 'ROLE_BARMAN'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: Home,
    meta: { requiresAuth: true, roles: [ROLE_CLIENT, ROLE_BARMAN] }
  },
  {
    path: '/categories/:id',
    component: Category,
    meta: { requiresAuth: true, roles: [ROLE_CLIENT] }
  },
  {
    path: '/cocktails/:id',
    component: CocktailDetail,
    meta: { requiresAuth: true, roles: [ROLE_CLIENT] }
  },

  // Barman routes
  {
    path: '/barman/cocktails',
    component: () => import('../views/CocktailList.vue'),
    meta: { requiresAuth: true, roles: [ROLE_BARMAN] }
  },
  {
    path: '/barman/cocktails/create',
    component: CocktailForm,
    meta: { requiresAuth: true, roles: [ROLE_BARMAN] }
  },
  {
    path: '/barman/cocktails/:id/edit',
    component: CocktailForm,
    meta: { requiresAuth: true, roles: [ROLE_BARMAN] }
  },
  {
    path: '/dashboard',
    component: Dashboard,
    meta: { requiresAuth: true, roles: [ROLE_BARMAN] }
  },
  {
    path: '/dashboard/:id',
    component: OrderDetail,
    meta: { requiresAuth: true, roles: [ROLE_BARMAN] }
  },

  // Client-only pages
  {
    path: '/cart',
    component: Cart,
    meta: { requiresAuth: true, roles: [ROLE_CLIENT] }
  },
  {
    path: '/orders',
    component: Orders,
    meta: { requiresAuth: true, roles: [ROLE_CLIENT] }
  },
  // La route manquante pour le détail d'une commande client :
  {
    path: '/orders/:id',
    component: OrderDetail,
    meta: { requiresAuth: true, roles: [ROLE_CLIENT] }
  },
  // Si tu veux garder une page d'avancement séparée :
  {
    path: '/orders/:id/avancement',
    component: OrderProgress,
    meta: { requiresAuth: true, roles: [ROLE_CLIENT] }
  },

  // Auth
  { path: '/login', component: Login },
  { path: '/register', component: Register },

  // Catch-all
  { path: '/:pathMatch(.*)*', component: NotFound }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Global guard
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('jwt')
  const userRole = localStorage.getItem('role')

  if (to.meta.requiresAuth && !token) {
    return next('/login')
  }

  if (to.path === '/' && token && userRole === 'ROLE_BARMAN') {
    // Si barman accède à la racine, redirige directement sur sa carte
    return next('/barman/cocktails')
  }

  if (to.meta.roles && userRole && !(to.meta.roles as string[]).includes(userRole)) {
    return next('/')
  }

  next()
})

export default router
