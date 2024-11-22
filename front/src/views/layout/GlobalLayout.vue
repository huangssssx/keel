<template>
  <div class="layout">
    <!-- 顶部导航栏 -->
    <header class="header">
      <div class="header-left">
        <el-icon class="collapse-btn" @click="toggleCollapse">
          <Fold v-if="!isCollapse"/>
          <Expand v-else/>
        </el-icon>
        <div class="breadcrumb">
          <el-breadcrumb>
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentRoute }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
      </div>
      <div class="header-right">
        <el-dropdown trigger="click">
          <span class="user-info">
            <el-avatar :size="32" :icon="UserFilled"/>
            <span class="username">{{ userStore.username }}</span>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>个人信息</el-dropdown-item>
              <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </header>

    <!-- 左侧菜单 -->
    <aside class="sidebar" :class="{ 'is-collapse': isCollapse }">
      <Menu :is-collapse="isCollapse" />
    </aside>

    <!-- 主要内容区域 -->
    <main class="main-content">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { Fold, Expand, UserFilled } from '@element-plus/icons-vue'
import Menu from '@/components/Menu.vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const isCollapse = ref(false)

// 计算当前路由名称
const currentRoute = computed(() => {
  return route.name || '首页'
})

// 切换菜单展开/收起
const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}

// 退出登录
const handleLogout = () => {
  userStore.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout {
  display: grid;
  grid-template-areas: 
    "header header"
    "sidebar main";
  grid-template-columns: auto 1fr;
  grid-template-rows: 60px 1fr;
  height: 100vh;
  transition: all 0.3s;
}

.header {
  grid-area: header;
  background-color: #fff;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  z-index: 2;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.collapse-btn {
  font-size: 20px;
  cursor: pointer;
  color: #666;
}

.header-right {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
}

.username {
  font-size: 14px;
  color: #666;
}

.sidebar {
  grid-area: sidebar;
  width: 200px;
  background-color: #fff;
  border-right: 1px solid #dcdfe6;
  transition: width 0.3s;
  overflow: hidden;
}

.sidebar.is-collapse {
  width: 64px;
}

.el-menu-vertical {
  border-right: none;
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 200px;
}

.main-content {
  grid-area: main;
  background-color: #f0f2f5;
  padding: 20px;
  overflow-y: auto;
}

/* 路由切换动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

:deep(.el-menu-item) span {
  transition: opacity 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

:deep(.el-menu--collapse) .el-menu-item span {
  opacity: 0;
}
</style> 