/// <reference types="node" />

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8000',
        changeOrigin: true,
        secure: false,
        configure: (proxy, options) => {
          // 请求发送前的日志
          proxy.on('proxyReq', (proxyReq, req, res) => {
            const { method, url } = req
            console.log('\n=== 代理请求信息 ===')
            console.log(`时间: ${new Date().toLocaleString()}`)
            console.log(`方法: ${method}`)
            console.log(`URL: ${url}`)
            console.log(`目标: ${options.target}${url}`)
            
            // 打印请求头
            console.log('请求头:', proxyReq.getHeaders())
            
            // 如果是 POST 请求，打印请求体
            if (req.body) {
              console.log('请求体:', req.body)
            }
          })

          // 响应接收后的日志
          proxy.on('proxyRes', (proxyRes, req, res) => {
            const { statusCode, headers } = proxyRes
            console.log('\n=== 代理响应信息 ===')
            console.log(`时间: ${new Date().toLocaleString()}`)
            console.log(`状态码: ${statusCode}`)
            console.log(`URL: ${req.url}`)
            console.log('响应头:', headers)
            
            // 收集响应体
            let responseBody = ''
            proxyRes.on('data', chunk => {
              responseBody += chunk
            })
            proxyRes.on('end', () => {
              try {
                const data = JSON.parse(responseBody)
                console.log('响应体:', data)
              } catch (e) {
                console.log('响应体:', responseBody)
              }
            })
          })

          // 错误处理
          proxy.on('error', (err, req, res) => {
            console.log('\n=== 代理错误 ===')
            console.log(`时间: ${new Date().toLocaleString()}`)
            console.log(`URL: ${req.url}`)
            console.error('错误:', err)
          })
        }
      }
    }
  }
})
