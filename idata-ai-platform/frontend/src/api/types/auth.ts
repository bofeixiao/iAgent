/**
 * 登录DTO
 */
export interface LoginDTO {
  account: string
  password: string
}

/**
 * 注册DTO
 */
export interface RegisterDTO {
  email: string
  phone?: string
  password: string
  nickname: string
  invitationCode?: string
}

/**
 * 用户信息
 */
export interface UserInfo {
  userId: number
  username?: string
  nickname: string
  email: string
  avatarUrl?: string
  vipLevel: number
  vipExpireTime?: string
  credits: number
}

/**
 * 登录返回VO
 */
export interface LoginVO extends UserInfo {
  token: string
}
