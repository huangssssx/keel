import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '@/views/home/index.vue'
import Login from '@/views/login/index.vue'
import { useUserStore } from '@/stores/user.ts'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { requiresAuth: true }
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  console.log(from);
  const userStore = useUserStore()
  if (to.matched.some(record => record.meta.requiresAuth) && !userStore.isLoggedIn) {
    next('/login')
  } else {
    next()
  }
})

export default router