<template>
  <div class="home">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <h1>Welcome to Keel</h1>
        </div>
      </template>
      <p>This is the home page.</p>
      <el-button type="primary">Element Plus Button</el-button>
    </el-card>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "Home",
});
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
