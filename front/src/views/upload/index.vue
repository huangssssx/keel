<template>
  <div class="upload-container">
    <el-upload
      class="upload-demo"
      action="http://localhost:8000/api/files/upload"
      :headers="headers"
      :on-success="handleUploadSuccess"
      :on-error="handleUploadError"
      :before-upload="handleBeforeUpload"
      multiple
      name="file"
    >
      <el-button type="primary">点击上传</el-button>
      <template #tip>
        <div class="el-upload__tip">
          支持上传任意类型文件，大小不超过10MB
        </div>
      </template>
    </el-upload>

    <!-- 文件列表展示 -->
    <div class="file-list" v-if="fileList.length > 0">
      <h3>已上传文件列表：</h3>
      <el-table :data="fileList" style="width: 100%">
        <el-table-column prop="name" label="文件名" />
        <el-table-column prop="url" label="访问链接">
          <template #default="scope">
            <el-link type="primary" :href="scope.row.url" target="_blank">
              {{ scope.row.url }}
            </el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import type { UploadProps } from 'element-plus'

const fileList = ref<Array<{ name: string; url: string }>>([])

// 上传请求头，如果需要认证token，可以在这里添加
const headers = {
  // 'Authorization': 'Bearer your-token'
}

// 上传前的处理
const handleBeforeUpload: UploadProps['beforeUpload'] = (file) => {
  const isLt10M = file.size / 1024 / 1024 < 10
  if (!isLt10M) {
    ElMessage.error('上传文件大小不能超过 10MB!')
    return false
  }
  return true
}

// 上传成功的处理
const handleUploadSuccess: UploadProps['onSuccess'] = (response, uploadFile) => {
  ElMessage.success('上传成功!')
  // 构建文件访问URL，需要根据你的Minio配置来设置正确的基础URL
  const fileUrl = `${response}`
  fileList.value.push({
    name: uploadFile.name,
    url: fileUrl
  })
}

// 上传失败的处理
const handleUploadError: UploadProps['onError'] = (error) => {
  ElMessage.error('上传失败: ' + error.message)
}
</script>

<style scoped>
.upload-container {
  padding: 20px;
}

.file-list {
  margin-top: 20px;
}

.el-upload__tip {
  margin-top: 8px;
  color: #666;
}
</style>
