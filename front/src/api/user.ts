import request from './index'

export interface LoginParams {
  username: string
  password: string
}

export interface UserInfo {
  id: number
  username: string
  role: string[]
  // ... 其他用户信息字段
}

// 分别导出各个方法
export const login = (data: LoginParams) => {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export const getUserInfo = () => {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

export const updateUser = (data: Partial<UserInfo>) => {
  return request({
    url: '/user/update',
    method: 'put',
    data
  })
} 