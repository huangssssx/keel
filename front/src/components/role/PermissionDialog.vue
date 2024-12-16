<template>
  <el-dialog
    v-model="dialogVisible"
    title="权限配置"
    width="500px"
    destroy-on-close
  >
    <div>
      弹窗内容: {{ msg }}
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handlerClose">取消</el-button>
        <el-button type="primary" @click="handleConfirm">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { computed } from 'vue'

const props = defineProps<{ 
  visible: boolean;
  msg: string;
}>();

const emit = defineEmits<{
  'update:visible': [value: boolean]
}>();

// 创建计算属性来处理双向绑定
const dialogVisible = computed({
  get: () => props.visible,
  set: (value) => emit('update:visible', value)
});

const handlerClose = () => {
  emit('update:visible', false);
}

const handleConfirm = () => {
  // TODO: 处理确认逻辑
  handlerClose();
}
</script>

<style scoped>
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}
</style>
