import request from '../request'
import type { LoginDTO, RegisterDTO, LoginVO } from '../types/auth'
import type { Result } from '../types/common'

/**
 * 用户登录
 */
export const login = (data: LoginDTO) => {
  return request.post<any, Result<LoginVO>>('/auth/login', data)
}

/**
 * 用户注册
 */
export const register = (data: RegisterDTO) => {
  return request.post<any, Result<void>>('/auth/register', data)
}

/**
 * 用户登出
 */
export const logout = () => {
  return request.post<any, Result<void>>('/auth/logout')
}

/**
 * 获取用户信息
 */
export const getUserInfo = () => {
  return request.get<any, Result<LoginVO>>('/auth/info')
}

/**
 * 刷新Token
 */
export const refreshToken = () => {
  return request.post<any, Result<string>>('/auth/refresh')
}
