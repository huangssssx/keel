<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="login-header">
        <img src="@/assets/cover.png" alt="Logo" class="logo" />
        <h2>Welcome Back</h2>
      </div>
      <el-form
        :model="loginForm"
        @submit.prevent="handleLogin"
        class="login-form"
      >
        <el-form-item>
          <el-input
            v-model="loginForm.username"
            placeholder="Username"
            :prefix-icon="User"
          />
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="Password"
            :prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            native-type="submit"
            :loading="loading"
            class="login-button"
          >
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

:deep(.el-input) {
          --el-input-hover-border-color: #4096ff;
          --el-input-focus-border-color: #4096ff;
          
          .el-input__wrapper {
            padding: 0 16px;
            background-color: #ffffff;
            border: 1px solid #e5e6eb;
            border-radius: 8px;
            box-shadow: none;
            transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);

            &:hover {
              border-color: var(--el-input-hover-border-color);
              background-color: #fff;
            }

            &.is-focus {
              border-color: var(--el-input-focus-border-color);
              background-color: #fff;
              box-shadow: 0 0 0 2px rgba(64, 150, 255, 0.1);
            }

            .el-input__inner {
              height: 42px;
              color: #1a1a1a;
              font-size: 14px;
              background-color: #ffffff;
              
              &:-webkit-autofill,
              &:-webkit-autofill:hover,
              &:-webkit-autofill:focus {
                -webkit-text-fill-color: #1a1a1a;
                -webkit-box-shadow: 0 0 0px 1000px #ffffff inset;
                transition: background-color 5000s ease-in-out 0s;
              }
              
              &::placeholder {
                color: #86909c;
                transition: color 0.3s ease;
              }
            }

            .el-input__prefix {
              color: #86909c;
              font-size: 16px;
              transition: color 0.3s ease;
            }
          }

          &:hover, &:focus-within {
            .el-input__prefix {
              color: var(--el-input-hover-border-color);
            }
          }
        }

:deep(.el-input__inner) {
  border-radius: 20px;
}

:deep(.el-button) {
  border-radius: 20px;
}
</style>
