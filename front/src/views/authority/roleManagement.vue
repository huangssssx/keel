<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="roleName" label="角色名称" width="180" />
    <el-table-column prop="roleCode" label="角色代码" width="180" />
    <el-table-column prop="description" label="描述" />
    <el-table-column prop="createTime" label="创建时间" />
    <el-table-column prop="status" label="状态">
      <template #default="scope">
        {{ scope.row.status ? '启用' : '禁用' }}
      </template>
    </el-table-column>
    <el-table-column label="操作" width="250">
      <template #default="scope">
        <el-button 
          type="primary" 
          size="small" 
          @click="handleEditPermissions(scope.row)"
        >
          权限配置
        </el-button>
        <el-button 
          type="warning" 
          size="small" 
          @click="handleEdit(scope.row)"
        >
          编辑
        </el-button>
        <el-button 
          type="danger" 
          size="small" 
          @click="handleDelete(scope.row)"
        >
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  
  <div class="pagination-container">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[10, 20, 30, 50]"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      layout="total, sizes, prev, pager, next"
    />

    <PermissionDialog :msg="'hello'" v-model:visible="permissionDialogVisible"/>
  </div>
</template>

<script lang="ts" setup>
import { fetchPage } from "@/api/role"
import { onMounted, ref } from "vue";
import PermissionDialog from "@/components/role/PermissionDialog.vue";

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

const permissionDialogVisible = ref(false);

const tableData = ref<Role[]>([]);
const currentPage = ref(1);
const pageSize = ref(10);
const total = ref(0);

const loadData = async () => {
  const res = await fetchPage({ 
    page: currentPage.value, 
    size: pageSize.value 
  });
  if (res.code === 200) {
    tableData.value = res.data.content;
    total.value = res.data.totalElements;
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
.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

:deep(.el-button) {
  margin-right: 8px;
  &:last-child {
    margin-right: 0;
  }
}
</style>
  