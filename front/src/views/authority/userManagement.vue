<template>
    <div class="space-y-4">
        <!-- 搜索区域 -->
        <div class="bg-white rounded-lg shadow-sm p-6">
            <el-form ref="searchFormRef" :model="searchForm" inline @submit.prevent>
                <div class="flex flex-wrap items-center gap-4">
                    <el-form-item label="用户名" prop="username">
                        <el-input v-model="searchForm.username" placeholder="请输入用户名" @keyup.enter="search"
                            :prefix-icon="Search" class="w-64" />
                    </el-form-item>

                    <div class="flex items-center gap-2 ml-auto">
                        <el-button type="default" @click="resetForm" :icon="Refresh" class="hover:bg-gray-50">
                            重置
                        </el-button>
                        <el-button type="primary" @click="search" :icon="Search">
                            搜索
                        </el-button>
                        <el-button type="success" :icon="Plus" @click="handleAdd">
                            新增用户
                        </el-button>
                    </div>
                </div>
            </el-form>
        </div>

        <!-- 表格区域 -->
        <div class="bg-white rounded-lg shadow-sm p-6">
            <el-table :data="tableData" style="width: 100%" :border="false" stripe highlight-current-row>
                <el-table-column prop="username" label="用户名" min-width="120">
                    <template #default="{ row }">
                        <div class="flex items-center gap-2">
                            <el-avatar :size="32" :src="row.avatar || '/default-avatar.png'" />
                            <span>{{ row.username }}</span>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column prop="nickname" label="别名" min-width="120" />

                <el-table-column prop="email" label="邮箱" min-width="180">
                    <template #default="{ row }">
                        <span v-if="row.email" class="flex items-center gap-1">
                            <el-icon>
                                <Message />
                            </el-icon>
                            {{ row.email }}
                        </span>
                        <span v-else class="text-gray-400">-</span>
                    </template>
                </el-table-column>

                <el-table-column prop="phone" label="电话" min-width="140">
                    <template #default="{ row }">
                        <span v-if="row.phone" class="flex items-center gap-1">
                            <el-icon>
                                <Phone />
                            </el-icon>
                            {{ row.phone }}
                        </span>
                        <span v-else class="text-gray-400">-</span>
                    </template>
                </el-table-column>

                <el-table-column prop="roles" label="角色" min-width="180">
                    <template #default="{ row }">
                        <div class="flex flex-wrap gap-1">
                            <el-tag v-for="role in row.roles" :key="role.id" :type="getRoleTagType(role.roleName)"
                                effect="light" size="small" class="rounded-full">
                                {{ role.roleName }}
                            </el-tag>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="180" fixed="right">
                    <template #default="{ row }">
                        <div class="flex items-center gap-2">
                            <el-button type="primary" link @click="handleEdit(row)">
                                编辑
                            </el-button>
                            <el-button type="danger" link @click="handleDelete(row)">
                                删除
                            </el-button>
                        </div>
                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页 -->
            <div class="flex justify-end mt-4">
                <el-pagination v-model:current-page="searchForm.page" v-model:page-size="searchForm.pageSize"
                    :total="total" :page-sizes="[10, 20, 50, 100]" layout="total, sizes, prev, pager, next"
                    @size-change="handleSizeChange" @current-change="handleCurrentChange" />
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, watchEffect } from 'vue'
import { getUserPage } from '@/api/user'
import { FormInstance } from 'element-plus'
import { debouncedSearch } from '@/composeable/search'
import {
    Search,
    Refresh,
    Plus,
    Message,
    Phone,
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ElMessageBox, ElMessage } from 'element-plus'

// 定义表格数据
const tableData = ref([])

// 获取用户信息
const userInfo = ref<UserInfo>()

// const fetchUserInfo = async () => {
//     try {
//         const res = await getUserInfo({})
//         userInfo.value = res.data
//         debugger
//     } catch (error) {
//         console.error('获取用户信息失败:', error)
//     }
// }




const searchFormRef = ref<FormInstance | null>(null)


// 重置表单
const resetForm = () => {
    if (searchFormRef.value) {
        searchFormRef.value.resetFields() // 确保字段被正确重置
    }
}

// 搜索功能
const searchForm = ref({
    username: '', // 初始值为空字符串
    page: 1,
    pageSize: 10
})

const searchWordDebounced = debouncedSearch(async (username: string, page: number, pageSize: number) => {
    //搜索关键字
    const res = await getUserPage({ username, page, pageSize });
    if (res.status === 200) {
        tableData.value = res.data.content;
    }
});


watchEffect(() => {
    // console.log(searchForm.value.username);
    searchWordDebounced(searchForm.value.username, searchForm.value.page, searchForm.value.pageSize);
})


const search = () => {
    console.log(searchForm.value)
}

onMounted(() => {

})

// 新增的功能函数
const total = ref(0)

function getRoleTagType(roleName: string) {
    const typeMap: Record<string, string> = {
        'admin': 'danger',
        'manager': 'warning',
        'user': 'info'
    }
    return typeMap[roleName.toLowerCase()] || 'info'
}

function handleAdd() {
    ElMessage.info('新增用户功能开发中...')
}

function handleEdit(row: any) {
    ElMessage.info('编辑用户功能开发中...')
}

function handleDelete(row: any) {
    ElMessageBox.confirm(
        `确定要删除用户 "${row.username}" 吗？`,
        '警告',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => {
        ElMessage.success('删除成功')
    }).catch(() => {
        ElMessage.info('已取消删除')
    })
}

function handleSizeChange(val: number) {
    searchForm.value.pageSize = val
    search()
}

function handleCurrentChange(val: number) {
    searchForm.value.page = val
    search()
}

// 更新 watchEffect 中的逻辑以包含分页数据
watchEffect(async () => {
    const res = await getUserPage({
        username: searchForm.value.username,
        page: searchForm.value.page,
        pageSize: searchForm.value.pageSize
    })
    if (res.status === 200) {
        tableData.value = res.data.content
        total.value = res.data.total
    }
})
</script>

<style scoped>
.el-table {
    --el-table-border-color: var(--el-border-color-lighter);
    --el-table-header-bg-color: #f8fafc;
}

:deep(.el-table__header) {
    @apply font-medium;
}

:deep(.el-pagination) {
    @apply justify-end;
}

:deep(.el-input__wrapper) {
    @apply shadow-none;
}

:deep(.el-button--default) {
    @apply border-gray-200;
}
</style>
