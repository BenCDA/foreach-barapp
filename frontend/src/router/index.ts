// src/router/index.ts
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home        from '../views/Home.vue'
import Category    from '../views/Category.vue'
import Cocktail    from '../views/Cocktail.vue'
import Cart        from '../views/Cart.vue'
import Orders      from '../views/Orders.vue'
import Dashboard   from '../views/Dashboard.vue'
import OrderDetail from '../views/OrderDetail.vue'
import NotFound    from '../views/NotFound.vue'
import Login       from '../views/Login.vue'
import Register    from '../views/Register.vue'

const ROLE_CLIENT = 'ROLE_CLIENT'
const ROLE_BARMAN = 'ROLE_BARMAN'

const routes: RouteRecordRaw[] = [
  { path: '/',                component: Home,        meta: { requiresAuth: true, roles: [ROLE_CLIENT, ROLE_BARMAN] } },
  { path: '/categories/:id',  component: Category,    meta: { requiresAuth: true, roles: [ROLE_CLIENT] } },
  { path: '/cocktails/:id',   component: Cocktail,    meta: { requiresAuth: true, roles: [ROLE_CLIENT] } },
  {
    path: '/categories/:id',
    component: Cocktail,
    meta: { requiresAuth: true, roles: ['ROLE_CLIENT'] }
  },
  // page barman (sa carte globale)
  {
    path: '/barman/cocktails',
    component: Cocktail,
    meta: { requiresAuth: true, roles: ['ROLE_BARMAN'] }
  },
  {
    path: '/barman/cocktails/create',
    component: () => import('../views/CocktailForm.vue'),
    meta: { requiresAuth: true, roles: ['ROLE_BARMAN'] }
  },
  // page détail d’un cocktail (client)
  {
    path: '/cocktails/:id',
    component: () => import('../views/CocktailDetail.vue'),
    meta: { requiresAuth: true, roles: ['ROLE_CLIENT'] }
  },
  {
    path: '/barman/cocktails/:id/edit',
    component: () => import('../views/CocktailForm.vue'),
    meta: { requiresAuth: true, roles: ['ROLE_BARMAN'] }
  },


  

  { path: '/cart',            component: Cart,        meta: { requiresAuth: true, roles: [ROLE_CLIENT] } },
  { path: '/orders',          component: Orders,      meta: { requiresAuth: true, roles: [ROLE_CLIENT] } },
  { path: '/dashboard',       component: Dashboard,   meta: { requiresAuth: true, roles: [ROLE_BARMAN] } },
  { path: '/dashboard/:id',   component: OrderDetail, meta: { requiresAuth: true, roles: [ROLE_BARMAN] } },
  { path: '/login',           component: Login },
  { path: '/register',        component: Register },
  { path: '/:pathMatch(.*)*', component: NotFound },
  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// guard global pour vérifier authentification et rôle
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('jwt')
  const userRole = localStorage.getItem('role')

  if (to.meta.requiresAuth && !token) {
    return next('/login')
  }
  if (to.meta.roles && userRole && !(to.meta.roles as string[]).includes(userRole)) {
    // si le rôle stocké n'est pas autorisé pour cette route
    return next('/')
  }
  next()
})

export default router
