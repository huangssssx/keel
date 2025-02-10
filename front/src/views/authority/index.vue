<!-- 权限管理 -->
<template>
  <div class="min-h-[400px]">
    <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
      <el-tab-pane label="用户管理" name="userManagement">
        <Transition enter-active-class="transition-all duration-1000 ease-out"
          enter-from-class="opacity-0 translate-x-4" enter-to-class="opacity-1000 translate-x-0"
          leave-active-class="transition-all duration-1000 ease-in" leave-from-class="opacity-1000 translate-x-0"
          leave-to-class="opacity-0 -translate-x-4" mode="out-in">
          <div v-show="activeName === 'userManagement'" class="w-full">
            <UserManagement />
          </div>
        </Transition>
      </el-tab-pane>
      <el-tab-pane label="角色管理" name="roleManagement">
        <Transition enter-active-class="transition-all duration-1000 ease-out"
          enter-from-class="opacity-0 translate-x-4" enter-to-class="opacity-100 translate-x-0"
          leave-active-class="transition-all duration-1000 ease-in" leave-from-class="opacity-1000 translate-x-0"
          leave-to-class="opacity-0 -translate-x-4" mode="out-in">
          <div v-show="activeName === 'roleManagement'" class="w-full">
            <RoleManagement />
          </div>
        </Transition>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue'
import type { TabsPaneContext } from 'element-plus'
import UserManagement from './userManagement.vue'
import RoleManagement from './roleManagement.vue'

const activeName = ref('userManagement')

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}
</script>

<style lang="less" scoped>
.demo-tabs {
  :deep(.el-tabs__nav-wrap::after) {
    @apply bg-gray-200;
  }

  :deep(.el-tabs__item) {
    @apply text-gray-600 text-base transition-colors duration-300;

    &.is-active {
      @apply text-primary font-medium;
    }

    &:hover {
      @apply text-primary;
    }
  }

  :deep(.el-tabs__active-bar) {
    @apply bg-primary;
  }
}
</style>