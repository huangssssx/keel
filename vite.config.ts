/// <reference types="node" />

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath } from 'url'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    open: true // 这将在开发服务器启动时自动打开浏览器
  },
  resolve: {
    alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      }
  }
})
