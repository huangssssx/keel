<template>
    <div class="search-container">
        <el-form ref="searchFormRef" :model="searchForm" inline @submit.prevent class="search-form">
            <el-form-item label="用户名" prop="username">
                <el-input 
                    v-model="searchForm.username" 
                    placeholder="请输入用户名" 
                    @keyup.enter="search"
                    :prefix-icon="Search"
                    class="search-input"
                />
            </el-form-item>

            <el-form-item class="search-buttons">
                <el-button 
                    type="default" 
                    @click="resetForm"
                    :icon="Refresh"
                    class="reset-button"
                >重置</el-button>
                <el-button 
                    type="primary" 
                    @click="search"
                    :icon="Search"
                    class="search-button"
                >搜索</el-button>
            </el-form-item>
        </el-form>
    </div>
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
import { Search, Refresh } from '@element-plus/icons-vue'

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

<style scoped>
.search-container {
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
    margin-bottom: 20px;
}

.search-form {
    display: flex;
    align-items: center;
    flex-wrap: wrap;
    gap: 10px;
}

.search-input {
    width: 240px;
}

.search-input :deep(.el-input__wrapper) {
    border-radius: 4px;
}

.search-buttons {
    margin-left: auto;
    margin-right: 0;
}

.search-buttons :deep(.el-form-item__content) {
    display: flex;
    gap: 10px;
}

.reset-button {
    background-color: #f4f4f5;
    border-color: #dcdfe6;
}

.reset-button:hover {
    background-color: #e9e9eb;
    border-color: #dcdfe6;
}

.search-button {
    margin-left: 0;
}
</style>
