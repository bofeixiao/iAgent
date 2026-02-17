<template>
  <div class="header-container">
    <div class="header-left">
      <div class="logo" @click="router.push('/')">
        <span class="logo-text">iData AI</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        mode="horizontal"
        @select="handleMenuSelect"
      >
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/applications">应用</el-menu-item>
        <el-menu-item index="/instances">我的作品</el-menu-item>
        <el-menu-item index="/thinking">思维</el-menu-item>
        <el-menu-item index="/articles">精华内容</el-menu-item>
      </el-menu>
    </div>

    <div class="header-right">
      <div class="credits-display" @click="handleRecharge">
        <el-icon><CreditCard /></el-icon>
        <span>{{ userStore.userInfo?.credits || 0 }} 积分</span>
      </div>

      <el-dropdown v-if="userStore.token">
        <div class="user-info">
          <el-avatar :src="userStore.userInfo?.avatarUrl" :size="32">
            {{ userStore.userInfo?.nickname?.charAt(0) }}
          </el-avatar>
          <span class="username">{{ userStore.userInfo?.nickname }}</span>
          <el-tag
            v-if="userStore.userInfo?.vipLevel"
            :type="getVipTagType(userStore.userInfo.vipLevel)"
            size="small"
          >
            {{ getVipLevelName(userStore.userInfo.vipLevel) }}
          </el-tag>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="router.push('/profile')">
              <el-icon><User /></el-icon>
              个人中心
            </el-dropdown-item>
            <el-dropdown-item divided @click="handleLogout">
              <el-icon><SwitchButton /></el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>

      <el-button v-else type="primary" @click="router.push('/login')">
        登录注册
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { CreditCard, User, SwitchButton } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleMenuSelect = (index: string) => {
  router.push(index)
}

const handleRecharge = () => {
  if (!userStore.token) {
    router.push('/login')
    return
  }
  ElMessage.info('充值功能开发中...')
}

const handleLogout = async () => {
  await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })

  await userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}

const getVipLevelName = (level: number) => {
  const levelNames: Record<number, string> = {
    0: '普通',
    1: '白银',
    2: '黄金',
    3: '铂金'
  }
  return levelNames[level] || ''
}

const getVipTagType = (level: number) => {
  const tagTypes: Record<number, any> = {
    0: '',
    1: 'info',
    2: 'warning',
    3: 'danger'
  }
  return tagTypes[level] || ''
}
</script>

<style scoped lang="scss">
.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
  height: 64px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 24px;
}

.logo {
  cursor: pointer;
  .logo-text {
    font-size: 24px;
    font-weight: bold;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.credits-display {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background: #f5f7fa;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.3s;

  &:hover {
    background: #e8eaed;
  }

  span {
    font-weight: 500;
    color: #409eff;
  }
}

.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;

  .username {
    font-size: 14px;
    color: #333;
  }
}
</style>
