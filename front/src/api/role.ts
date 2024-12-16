import request from './index'

export interface RoleParams {
  name: string
  description?: string
  permissions: string[]
}

export interface pageParams {
  page:number,
  size:number
}

// 分别导出各个方法
export const fetchPage = (params:pageParams) => {
  return request({
    url: '/role/fetchPage',
    method: 'get',
    params
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