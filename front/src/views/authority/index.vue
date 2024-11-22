<!-- 权限管理 -->
<template>
  <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
    <el-tab-pane label="用户管理" name="userManagement">
      <UserManagement>
      </UserManagement>
    </el-tab-pane>
    <el-tab-pane label="角色管理" name="roleManagement">
      <RoleManagement />
    </el-tab-pane>
  </el-tabs>
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

<style lang="less" scoped></style>