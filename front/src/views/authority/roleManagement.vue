<template>
  <div class="space-y-4">
    <!-- 搜索区域 -->
    <div class="bg-white rounded-lg shadow-sm p-6">
      <el-form ref="searchFormRef" :model="searchForm" inline @submit.prevent>
        <div class="flex flex-wrap items-center gap-4">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="searchForm.roleName" placeholder="请输入角色名称" @keyup.enter="search" :prefix-icon="Search"
              class="w-64" />
          </el-form-item>
          <el-form-item label="角色代码" prop="roleCode">
            <el-input v-model="searchForm.roleCode" placeholder="请输入角色代码" @keyup.enter="search" :prefix-icon="Search"
              class="w-64" />
          </el-form-item>

          <div class="flex items-center gap-2 ml-auto">
            <el-button type="default" @click="resetForm" :icon="Refresh" class="hover:bg-gray-50">
              重置
            </el-button>
            <el-button type="primary" @click="search" :icon="Search">
              搜索
            </el-button>
            <el-button type="success" :icon="Plus" @click="handleAdd">
              新增角色
            </el-button>
          </div>
        </div>
      </el-form>
    </div>

    <!-- 表格区域 -->
    <div class="bg-white rounded-lg shadow-sm p-6">
      <el-table v-loading="isLoading" :data="tableData" style="width: 100%" :border="false" stripe
        highlight-current-row>
        <el-table-column prop="roleName" label="角色名称" min-width="120">
          <template #default="{ row }">
            <div class="font-medium text-gray-900">{{ row.roleName }}</div>
          </template>
        </el-table-column>
        <el-table-column prop="roleCode" label="角色代码" min-width="120">
          <template #default="{ row }">
            <span class="text-gray-600 font-mono">{{ row.roleCode }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="{ row }">
            <span class="text-gray-600">{{ formatDate(row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status ? 'success' : 'danger'" size="small">
              {{ row.status ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <div class="flex items-center space-x-2">
              <el-button type="primary" link @click="handleEditPermissions(row)">
                权限配置
              </el-button>
              <el-button type="warning" link @click="handleEdit(row)">
                编辑
              </el-button>
              <el-button type="danger" link @click="handleDelete(row)">
                删除
              </el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页器 -->
      <div class="flex justify-end mt-4">
        <el-pagination v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[10, 20, 50, 100]"
          :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"
          layout="total, sizes, prev, pager, next" background />
      </div>
    </div>

    <!-- 权限配置对话框 -->
    <PermissionDialog :msg="'hello'" v-model:visible="permissionDialogVisible" />
  </div>
</template>

<script lang="ts" setup>
import { fetchPage } from "@/api/role"
import { onMounted, ref } from "vue";
import { ElMessage, ElMessageBox, FormInstance } from "element-plus";
import PermissionDialog from "@/components/role/PermissionDialog.vue";
import { formatDate } from "@/utils/date";
import { Search, Refresh, Plus } from '@element-plus/icons-vue';
import { debouncedSearch } from '@/composeable/search';

interface Permission {
  id: number;
  parentId: number | null;
  permissionName: string;
  permissionCode: string;
  permissionType: number;
  path: string | null;
  component: string | null;
  icon: string | null;
  sort: number;
  status: boolean;
  createTime: string;
  updateTime: string | null;
}

interface Role {
  id: number;
  roleName: string;
  roleCode: string;
  description: string | null;
  status: boolean;
  createTime: string;
  updateTime: string | null;
  permissions: Permission[];
}

const searchFormRef = ref<FormInstance | null>(null);
const searchForm = ref({
  roleName: '',
  roleCode: '',
});

const isLoading = ref(false);
const permissionDialogVisible = ref(false);
const tableData = ref<Role[]>([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

// 重置表单
const resetForm = () => {
  if (searchFormRef.value) {
    searchFormRef.value.resetFields();
    loadData(); // 重置后重新加载数据
  }
};

// 搜索功能
const search = () => {
  currentPage.value = 1; // 搜索时重置页码
  loadData();
};

const loadData = async () => {
  isLoading.value = true;
  try {
    const res = await fetchPage({
      page: currentPage.value,
      size: pageSize.value,
      roleName: searchForm.value.roleName,
      roleCode: searchForm.value.roleCode
    });
    if (res.code === 200) {
      tableData.value = res.data.content;
      total.value = res.data.totalElements;
    }
  } catch (error) {
    ElMessage.error('加载数据失败');
  } finally {
    isLoading.value = false;
  }
};

const handleSizeChange = (val: number) => {
  pageSize.value = val;
  loadData();
};

const handleCurrentChange = (val: number) => {
  currentPage.value = val;
  loadData();
};

const handleAdd = () => {
  // TODO: 实现新增角色逻辑
  console.log('新增角色');
};

const handleEditPermissions = (row: Role) => {
  permissionDialogVisible.value = true;
  console.log('配置权限:', row);
};

const handleEdit = (row: Role) => {
  console.log('编辑角色:', row);
  // TODO: 实现编辑角色逻辑
};

const handleDelete = (row: Role) => {
  ElMessageBox.confirm(
    `确定要删除角色 "${row.roleName}" 吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    // TODO: 实现删除逻辑
    ElMessage({
      type: 'success',
      message: '删除成功',
    });
  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '已取消删除',
    });
  });
};

onMounted(() => {
  loadData();
});
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
