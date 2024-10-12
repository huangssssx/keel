<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-header">
        <img src="@/assets/cover.png" alt="Logo" class="logo" />
        <h2>Welcome Back Friend!</h2>
      </div>
      <el-form :model="loginForm" @submit.prevent="handleLogin" class="login-form">
        <el-form-item>
          <el-input v-model="loginForm.username" placeholder="Username" :prefix-icon="User" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="loginForm.password" type="password" placeholder="Password" :prefix-icon="Lock"
            show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit" :loading="loading" class="login-button">
            {{ loading ? "Logging in..." : "Login" }}
          </el-button>
        </el-form-item>
      </el-form>
      <div class="login-footer">
        <el-link type="info" href="#">Forgot password?</el-link>
      </div>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { ElMessage } from "element-plus";
import { User, Lock } from "@element-plus/icons-vue";

const router = useRouter();
const userStore = useUserStore();

const loginForm = reactive({
  username: "",
  password: "",
});

const loading = ref(false);

const handleLogin = async () => {
  loading.value = true;
  try {
    await userStore.login(loginForm);
    ElMessage.success("Login successful!");
    router.push("/");
  } catch (error) {
    console.error("Login failed:", error);
    ElMessage.error("Login failed. Please check your credentials.");
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  margin: 0;
  padding: 0;
}

.login-card {
  width: 400px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.login-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.login-header {
  text-align: center;
  margin-bottom: 20px;
}

.logo {
  width: 80px;
  height: 80px;
  margin-bottom: 10px;
}

.login-form {
  padding: 0 20px;
}

.login-button {
  width: 100%;
}

.login-footer {
  text-align: center;
  margin-top: 20px;
}

:deep(.el-input__inner) {
  border-radius: 20px;
}

:deep(.el-button) {
  border-radius: 20px;
}
</style>
