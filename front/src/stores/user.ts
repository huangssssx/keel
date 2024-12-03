import { defineStore } from 'pinia'

interface UserState {
  token: string | null
  username: string | null,
  permission:string[]|null,
  userInfo:UserInfo|null
}

interface UserInfo {
  id: number
  username: string
  password: string
  nickname: string
  email: string
  phone: string
  status: boolean
  createTime: string
  updateTime: string
  lastLoginTime: string | null
  roles: Role[]
}


interface LoginPayload {
  username: string
  password: string
}

// interface LoginResponse {
//   token: string,
//   permission:string
//   userInfo:UserInfo
// }

interface Permission {
  id: number
  parentId: number | null
  permissionName: string
  permissionCode: string
  permissionType: number
  path: string | null
  component: string | null
  icon: string | null
  sort: number
  status: boolean
  createTime: string
  updateTime: string
}

// 角色接口
interface Role {
  id: number
  roleName: string
  roleCode: string
  description: string | null
  status: boolean
  createTime: string
  updateTime: string
  permissions: Permission[]
}


function transformPermission(roles:Role[]): string[] {
  if(!roles || roles.length===0){
    return [];
  }

  return roles.flatMap((role:Role)=>{
    return role.permissions.flatMap((permission:Permission)=>permission.permissionCode);;
  });
}

import {login,getUserInfo} from "@/api/user"
export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: localStorage.getItem('token')||null,
    userInfo:null,
    username: null,
    permission:null
  }),
  
  actions: {
    async login(payload: LoginPayload) {
      try {
        const { data: response } = await login(payload)
        this.token = response.token;
        this.username = payload.username;
        this.permission = transformPermission(response?.userInfo?.roles);
        this.userInfo = response.userInfo;
        // 保存到本地存储
        localStorage.setItem('token', response.token)
      } catch (error) {
        console.error('Login failed:', error)
        throw error
      }
    },
    async getUserInfo(token:string){
      try{
        // const token = localStorage.getItem('token');
        if(!token){
          throw new Error("token is empty!");
        }
  
        const {data:response} = await getUserInfo(token)
        this.token = token;
        this.username = response?.userInfo?.username;
        this.permission = transformPermission(response?.userInfo?.roles);
        this.userInfo = response.userInfo;
        return this.userInfo;
      }
      catch(error){
        this.logout();
      }
    },
    logout() {
      this.token = null
      this.username = null
      this.permission = null  // 需要添加
      this.userInfo = null   // 需要添加
      localStorage.removeItem('token')
    }
  },
  
  getters: {
    isLoggedIn: (state) => !!state.token
  }
})