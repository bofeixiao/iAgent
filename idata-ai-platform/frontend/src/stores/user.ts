import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as apiLogin, register as apiRegister, getUserInfo as apiGetUserInfo, logout as apiLogout } from '@/api/modules/auth'
import type { LoginDTO, RegisterDTO, UserInfo } from '@/api/types/auth'

export const useUserStore = defineStore(
  'user',
  () => {
    const token = ref<string>('')
    const userInfo = ref<UserInfo | null>(null)

    const login = async (loginData: LoginDTO) => {
      const res = await apiLogin(loginData)
      if (res.data) {
        token.value = res.data.token
        userInfo.value = res.data
      }
    }

    const register = async (registerData: RegisterDTO) => {
      await apiRegister(registerData)
    }

    const logout = async () => {
      await apiLogout()
      token.value = ''
      userInfo.value = null
    }

    const getUserInfo = async () => {
      const res = await apiGetUserInfo()
      if (res.data) {
        userInfo.value = res.data
      }
    }

    return {
      token,
      userInfo,
      login,
      register,
      logout,
      getUserInfo
    }
  },
  {
    persist: true
  }
)
