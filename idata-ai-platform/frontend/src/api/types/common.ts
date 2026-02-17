/**
 * 统一响应结果
 */
export interface Result<T = any> {
  code: number
  message: string
  data: T
  timestamp?: number
}

/**
 * 分页请求参数
 */
export interface PageQuery {
  current?: number
  size?: number
}

/**
 * 分页响应结果
 */
export interface PageResult<T> {
  records: T[]
  total: number
  current: number
  size: number
  pages: number
}
