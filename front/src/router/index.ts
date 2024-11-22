import { createRouter, createWebHistory } from 'vue-router'
import { House, Upload, Setting } from '@element-plus/icons-vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: () => import('@/views/layout/GlobalLayout.vue'),
      children: [
        {
          path: '/',
          name: '首页',
          component: () => import('@/views/home/index.vue'),
          meta: {
            title: '首页',
            icon: House
          }
        },
        {
          path: '/upload',
          name: '文件上传',
          component: () => import('@/views/upload/index.vue'),
          meta: {
            title: '文件上传',
            icon: Upload
          }
        },
        {
          path: '/authority',
          name: '权限管理',
          component: () => import('@/views/authority/index.vue'),
          meta: {
            title: '权限管理',
            icon: Setting
          }
        }
      ]
    },
    // 其他路由配置...
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/login/index.vue')
    }
  ]
})

export default router