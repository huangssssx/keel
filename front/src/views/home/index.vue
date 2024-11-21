<template>
  <div class="home">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <h1>Welcome to Keel</h1>
          <span>鼠标位置:{{ x }},{{ y }}</span>
        </div>
      </template>
      <p>Welcome, {{ userStore.username }}!</p>
      <el-button type="primary" @click="handleLogout">Logout</el-button>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { useMousePosition } from "@/composeable/mouse";
import { proxyObject } from "@/composeable/proxyCompose";

const router = useRouter();
const userStore = useUserStore();

const handleLogout = () => {
  userStore.logout();
  router.push("/login");
};

const { x, y } = useMousePosition();

const obj = {
  id:"obj",
  get value(){
    return this.id;
  }
}
const p = proxyObject(obj);

const obj2 = {
  id:"obj2"
};

Object.setPrototypeOf(obj2,p)

console.log(obj2.value);
</script>

<style scoped>
.home {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.box-card {
  width: 480px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
