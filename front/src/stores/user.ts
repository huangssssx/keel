import { defineStore } from 'pinia'

interface UserState {
  token: string | null
  username: string | null
}

interface LoginPayload {
  username: string
  password: string
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: null,
    username: null
  }),
  actions: {
    async login(payload: LoginPayload) {
      // 这里应该是一个实际的 API 调用
      // 为了演示，我们使用一个模拟的登录过程
      return new Promise<void>((resolve, reject) => {
        setTimeout(() => {
          if (payload.username === 'admin' && payload.password === 'password') {
            this.token = 'mock_token'
            this.username = payload.username
            resolve()
          } else {
            reject(new Error('Invalid credentials'))
          }
        }, 1000)
      })
    },
    logout() {
      this.token = null
      this.username = null
    }
  },
  getters: {
    isLoggedIn: (state) => !!state.token
  }
})