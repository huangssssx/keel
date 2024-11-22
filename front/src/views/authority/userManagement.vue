<template>
    <el-form ref="searchFormRef" :model="searchForm" inline>
        <el-form-item label="用户名" prop="username">
            <el-input v-model="searchForm.username" placeholder="请输入用户名" />
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="resetForm">重置</el-button>
            <el-button type="primary" @click="search">搜索</el-button>
        </el-form-item>
    </el-form>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="date" label="Date" width="180" />
      <el-table-column prop="name" label="Name" width="180" />
      <el-table-column prop="address" label="Address" />
    </el-table>
</template>

<script lang="ts" setup>
import { ref, onMounted, watchEffect } from 'vue'
import { getUserInfo } from '@/api/user'
import type { UserInfo } from '@/api/user'
import { FormInstance } from 'element-plus'
import { debouncedSearch } from '@/composeable/search'

// 定义表格数据
const tableData = ref([])

// 获取用户信息
const userInfo = ref<UserInfo>()

const fetchUserInfo = async () => {
  try {
    const res = await getUserInfo()
    userInfo.value = res.data
  } catch (error) {
    console.error('获取用户信息失败:', error)
  }
}

const searchFormRef = ref<FormInstance | null>(null)


// 重置表单
const resetForm = () => {
  if (searchFormRef.value) {
    searchFormRef.value.resetFields() // 确保字段被正确重置
  }
}

// 搜索功能
const searchForm = ref({
  username: '' // 初始值为空字符串
})

const searchWordDebounced = debouncedSearch((username:string)=>{
    //搜索关键字
    console.log(username);
});


watchEffect(()=>{
    // console.log(searchForm.value.username);
    searchWordDebounced(searchForm.value.username);
})


const search = () => {
  console.log(searchForm.value)
}

onMounted(() => {
  fetchUserInfo()
})
</script>
