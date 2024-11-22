<template>
  <el-menu
    :default-active="route.path"
    class="el-menu-vertical"
    :collapse="isCollapse"
    :router="true"
  >
    <template v-for="route in menuRoutes" :key="route.path">
      <el-menu-item v-if="!route.children" :index="route.path">
        <el-icon v-if="route.meta?.icon">
          <component :is="route.meta.icon" />
        </el-icon>
        <template #title>{{ route.meta?.title || route.name }}</template>
      </el-menu-item>

      <el-sub-menu v-else :index="route.path">
        <template #title>
          <el-icon v-if="route.meta?.icon">
            <component :is="route.meta.icon" />
          </el-icon>
          <span>{{ route.meta?.title || route.name }}</span>
        </template>
        
        <el-menu-item 
          v-for="child in route.children"
          :key="child.path"
          :index="route.path + '/' + child.path"
        >
          <el-icon v-if="child.meta?.icon">
            <component :is="child.meta.icon" />
          </el-icon>
          <template #title>{{ child.meta?.title || child.name }}</template>
        </el-menu-item>
      </el-sub-menu>
    </template>
  </el-menu>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const props = defineProps<{
  isCollapse: boolean
}>()

const route = useRoute()
const router = useRouter()

// 从路由配置中过滤出需要显示在菜单中的路由
const menuRoutes = computed(() => {
  return router.options.routes.find(route => route.path === '/')?.children?.filter(route => !route.meta?.hidden) || []
})
</script>

<style scoped>
.el-menu-vertical {
  border-right: none;
}

.el-menu-vertical:not(.el-menu--collapse) {
  width: 200px;
}

:deep(.el-menu-item),
:deep(.el-sub-menu__title) {
  span {
    transition: opacity 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  }
}

:deep(.el-menu--collapse) {
  .el-menu-item span,
  .el-sub-menu__title span {
    opacity: 0;
  }
  
  .el-sub-menu__title .el-sub-menu__icon-arrow {
    opacity: 0;
  }
}
</style> 