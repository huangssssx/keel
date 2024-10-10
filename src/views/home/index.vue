<template>
  <div class="home">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <h1>Welcome to Keel</h1>
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

const router = useRouter();
const userStore = useUserStore();

const handleLogout = () => {
  userStore.logout();
  router.push("/login");
};
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
