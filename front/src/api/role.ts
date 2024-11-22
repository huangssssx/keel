import request from './index'

export interface RoleParams {
  name: string
  description?: string
  permissions: string[]
}

// 分别导出各个方法
export const getRoleList = () => {
  return request({
    url: '/role/list',
    method: 'get'
  })
}

export const createRole = (data: RoleParams) => {
  return request({
    url: '/role/create',
    method: 'post',
    data
  })
}

export const updateRole = (id: number, data: Partial<RoleParams>) => {
  return request({
    url: `/role/${id}`,
    method: 'put',
    data
  })
} 