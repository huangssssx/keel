import { defineStore } from 'pinia'
import { http } from '@/utils/http'

interface UserState {
  token: string | null
  username: string | null,
  permission:string[]
}

interface LoginPayload {
  username: string
  password: string
}

interface LoginResponse {
  token: string,
  permission:string
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: localStorage.getItem('token'),
    username: localStorage.getItem('username'),
    permission:JSON.parse(localStorage.getItem("permission")||"[]")
  }),
  
  actions: {
    async login(payload: LoginPayload) {
      try {
        const response = await http.post<LoginResponse>('/authenticate', payload)
        
        this.token = response.token
        this.username = payload.username
        
        // 保存到本地存储
        localStorage.setItem('token', response.token)
        localStorage.setItem('username', payload.username)
        localStorage.setItem("permission",response.permission)
        
      } catch (error) {
        console.error('Login failed:', error)
        throw error
      }
    },
    
    logout() {
      this.token = null
      this.username = null
      localStorage.removeItem('token')
      localStorage.removeItem('username')
    }
  },
  
  getters: {
    isLoggedIn: (state) => !!state.token
  }
})