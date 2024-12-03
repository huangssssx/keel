import { createRouter, createWebHistory, RouteLocationNormalized } from 'vue-router'
import { House, Upload, Setting } from '@element-plus/icons-vue'
import { useUserStore,permission } from '@/stores/user'

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
            icon: Setting,
            requireAuth:true,
            permissions:["user:read"]
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



function _checkPermission(route:RouteLocationNormalized, permissions:string[]|null) {
    if(route.meta.requireAuth === false){
      return true;
    }

    const requirePermissions = route.meta?.permissions as string[] ?? [];
    if(!requirePermissions.length) {
        return true;
    }

    if(!permissions){
      return false;
    }

    return requirePermissions.some(permission => permissions.includes(permission));
}

router.beforeEach(async(to:RouteLocationNormalized, from:RouteLocationNormalized, next)=>{
  // 登录页面允许直接访问
  if(to.path === '/login') {
    next();
    return;
  }

  const userStore = useUserStore();
  const { getUserInfo} = userStore;
  const token:string|null = userStore?.token??null;
  
  if(!token) {
    next('/login');
    return;
  }

  // 等待获取用户信息
  if(!userStore.userInfo) {
    await getUserInfo(token);
  }

  if(_checkPermission(to, userStore.permission)) {
    next();
  } else {
    // 无权限时跳转到首页或显示无权限页面
    next('/');
  }
})

export default router