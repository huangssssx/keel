import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import GlobalLayout from '@/views/layout/GlobalLayout.vue'
import Home from '@/views/home/index.vue'
import Login from '@/views/login/index.vue'
import Upload from '@/views/upload/index.vue'
import Authority from '@/views/authority/index.vue'
import { useUserStore } from '@/stores/user.ts'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    component: GlobalLayout,
    children: [
      {
        path: '',  // 默认子路由
        name: 'Home',
        component: Home,
        meta: { requiresAuth: true }
      },
      {
        path: 'upload',
        name: 'Upload',
        component: Upload,
        meta: { requiresAuth: true }
      },
      {
        path: 'authority',
        name: 'Authority',
        component: Authority,
        meta: { requiresAuth: true }
      }
    ]
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
  const userStore = useUserStore()
  if (to.matched.some(record => record.meta.requiresAuth) && !userStore.isLoggedIn) {
    next('/login')
  } else {
    next()
  }
})

export default router