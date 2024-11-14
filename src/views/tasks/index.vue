<template>
    <div class="tasks">
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <h1>任务管理</h1>
                </div>
            </template>
            <div class="card-content">
                <el-button type="primary" class="add-btn" @click="dialogVisible = true">添加任务</el-button>
                <div class="task-list">
                    <div :id="`item_${task.getId().toString()}`" class="task-item" v-for="task in taskList" :key="task.getId()">
                        <div class="task-id">#{{ task.getId() }}</div>
                        <div class="task-name">{{ task.getName() }}</div>
                        <div class="task-actions">
                            <el-button type="danger" size="small" :icon="Delete" text @click="deleteTaskHandler(task.getId())">删除</el-button>
                        </div>
                    </div>
                </div>
            </div>

            <el-dialog
                v-model="dialogVisible"
                title="添加任务"
                width="30%"
                :close-on-click-modal="false"
                :show-close="true"
            >
                <div class="dialog-content">
                    <el-form :model="taskForm" label-width="80px">
                        <el-form-item label="任务名称">
                            <el-input v-model="taskForm.name" placeholder="请输入任务名称" />
                        </el-form-item>
                        <el-form-item label="任务描述">
                            <el-input
                                v-model="taskForm.description"
                                type="textarea"
                                :rows="3"
                                placeholder="请输入任务描述"
                            />
                        </el-form-item>
                    </el-form>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="confirmAddHandler">确定</el-button>
                    </span>
                </template>
            </el-dialog>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { computed, nextTick, reactive, ref } from 'vue';
import { Task } from "./task";
import { Delete } from '@element-plus/icons-vue'

// 任务管理逻辑将在这里实现
// const tasks: Array<Task> = reactive([])

const dialogVisible = ref(false)
const taskForm = reactive({
    name: '',
    description: ''
})


const taskMap = ref(new Map<number,Task>());

const taskList = computed(()=>{
    return Array.from(taskMap.value.values());
});
//添加任务
const confirmAddHandler=()=>{
    const {name,description} = taskForm;
    const taskId = Date.now();
    const task = new Task(taskId,name,description);
    taskMap.value.set(taskId,task);

    nextTick(()=>{
        const $targetTaskElement = document.querySelector(`#item_${taskId}`);
        if($targetTaskElement){
            $targetTaskElement.classList.add("task-enter");
            setTimeout(()=>{
                $targetTaskElement.classList.remove("task-enter");
            },400)
        }
    })

    dialogVisible.value = false;
}

const deleteTaskHandler=(taskId:number)=>{
    const $targetTaskElement = document.querySelector(`#item_${taskId}`);
    if($targetTaskElement){
        $targetTaskElement.classList.add("before-delete");
        setTimeout(()=>{
            taskMap.value.delete(taskId);
        },400)
    }
}

</script>

<style scoped lang="less">
.tasks {
    padding: 24px;
    background-color: #f5f7fa;
    min-height: calc(100vh - 48px);

    .box-card {
        border-radius: 8px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);

        :deep(.el-card__header) {
            border-bottom: 2px solid #f0f2f5;
            padding: 16px 20px;
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;

            h1 {
                margin: 0;
                font-size: 18px;
                font-weight: 600;
                color: #303133;
            }
        }

        .card-content {
            position: relative;
            padding: 0;

            .add-btn {
                position: relative;
                right: -50%;
                transform: translate(-50%);
                margin: 20px 0;
            }

            .task-list {
                .task-item {
                    display: flex;
                    align-items: center;
                    padding: 16px 20px;
                    border-bottom: 1px solid #f0f2f5;
                    transition: all 0.3s ease;

                    &:hover {
                        background-color: #f5f7fa;
                    }

                    &:last-child {
                        border-bottom: none;
                    }

                    .task-id {
                        width: 80px;
                        color: #909399;
                        font-family: monospace;
                        font-size: 14px;
                    }

                    .task-name {
                        flex: 1;
                        color: #303133;
                        font-size: 14px;
                        padding: 0 16px;
                    }

                    .task-actions {
                        width: 100px;
                        text-align: right;
                        opacity: 0;
                        transition: opacity 1s ease;
                    }

                    &:hover .task-actions {
                        opacity: 1;
                    }
                }

                @keyframes before-delete {
                    0% {
                        opacity: 1;
                        transform: translateX(0);
                        max-height: 60px;  // 根据你的实际内容高度调整
                        margin-bottom: 8px; // 如果有间距的话
                    }
                    50% {
                        opacity: 0;
                        transform: translateX(-30px);
                        max-height: 60px;
                        margin-bottom: 8px;
                    }
                    100% {
                        opacity: 0;
                        transform: translateX(-30px);
                        max-height: 0;
                        margin-bottom: 0;
                    }
                }


                @keyframes task-enter {
                    0% {
                        opacity: 0;
                        transform: translateX(30px);
                        max-height: 0;
                    }
                    50% {
                        opacity: 0.5;
                        transform: translateX(15px);
                        max-height: 30px;
                    }
                    100% {
                        opacity: 1;
                        transform: translateX(0);
                        max-height: 60px;
                    }
                }


                .before-delete{
                    animation: before-delete 0.4s ease-out  0s 1 normal forwards;
                }
                .task-enter {
                    animation: task-enter 0.4s cubic-bezier(0.4, 0, 0.2, 1) forwards;
                }
            }
        }
    }
}

:deep(.el-dialog) {
    border-radius: 8px;
    
    .el-dialog__header {
        margin: 0;
        padding: 20px;
        border-bottom: 1px solid #f0f2f5;
        
        .el-dialog__title {
            font-size: 16px;
            font-weight: 600;
        }
    }
    
    .el-dialog__body {
        padding: 20px;
    }
    
    .el-dialog__footer {
        padding: 10px 20px 20px;
        border-top: 1px solid #f0f2f5;
    }
}

.dialog-content {
    .el-form {
        max-width: 460px;
        margin: 0 auto;
    }
}

.dialog-footer {
    display: flex;
    justify-content: flex-end;
    gap: 8px;
}
</style>