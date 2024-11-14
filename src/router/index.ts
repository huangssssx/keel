import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '@/views/home/index.vue'
import Login from '@/views/login/index.vue'
import HelloWorld from '@/components/HelloWorld.vue'
import Tasks from '@/views/tasks/index.vue'
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
  },
  {
    path: '/hello',
    name: 'Hello',
    component: HelloWorld
  },
  {
    path: '/tasks',
    name: 'Tasks',
    component: Tasks,
    meta: { requiresAuth: false }
  }
]

const router = createRouter({
  history: createWebHistory('/keel/'), // 在这里设置基础路径
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