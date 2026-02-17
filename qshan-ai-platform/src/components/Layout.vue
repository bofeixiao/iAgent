<template>
  <div class="layout-container">
    <!-- 活动横幅 -->
    <div class="banner-bar-wrapper">
      <div class="banner-bar">
        <div class="banner-content clickable" @click="handleBannerClick">
          <div class="banner-text">
            <span class="banner-title">🎁 新春活动！推荐有奖~拿198元抵扣券</span>
            <el-button type="text" class="detail-button">
              立即参与 <el-icon class="ml-1"><ArrowRight /></el-icon>
            </el-button>
          </div>
        </div>
      </div>
    </div>

    <!-- 顶部导航栏 -->
    <header class="top-navbar">
      <div class="navbar-content">
        <div class="navbar-left">
          <!-- Logo区域 -->
          <div class="logo-section">
            <div class="logo-icon">
              <img src="/logo.svg" alt="logo" />
            </div>
            <span class="system-name">青山AI创作平台</span>
          </div>

          <!-- 主导航菜单 -->
          <nav class="nav-menu">
            <router-link
              v-for="item in navigationItems"
              :key="item.path"
              :to="item.path"
              class="nav-item"
              :class="{ active: $route.path === item.path }"
            >
              <el-icon class="nav-icon">
                <component :is="item.icon" />
              </el-icon>
              <span>{{ item.label }}</span>
            </router-link>
          </nav>
        </div>

        <div class="navbar-right">
          <!-- 邀请按钮 -->
          <el-button class="invitation-btn">
            <el-icon><Share /></el-icon>
            <span class="btn-text">邀请有奖</span>
            <span class="btn-text-mobile">邀请</span>
          </el-button>

          <!-- 购买会员按钮 -->
          <el-button class="membership-center-btn">
            <el-icon><ShoppingCart /></el-icon>
            <span class="btn-text">购买会员</span>
            <span class="btn-text-mobile">购买</span>
          </el-button>

          <!-- VIP会员标识 -->
          <div class="membership-badge-container has-membership">
            <div class="membership-badge active">
              <div class="badge-content">
                <img src="/vip-icon.svg" alt="VIP" class="badge-icon-img" />
                <span class="badge-credit">
                  5 
                  <span class="credit-unit credit-unit-full">积分</span>
                  <span class="credit-unit credit-unit-short">分</span>
                </span>
              </div>
            </div>
          </div>

          <!-- 用户头像 -->
          <el-dropdown>
            <div class="user-avatar-section">
              <div class="user-avatar">
                <el-icon><User /></el-icon>
              </div>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="goToProfile">个人资料</el-dropdown-item>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </header>

    <!-- 主要内容区域 -->
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { 
  ArrowRight, 
  Share, 
  ShoppingCart, 
  User,
  Grid,
  Document,
  FolderOpened,
  Files
} from '@element-plus/icons-vue'

const router = useRouter()

// 导航菜单项
const navigationItems = ref([
  {
    path: '/apps',
    label: '应用',
    icon: 'Grid'
  },
  {
    path: '/works', 
    label: '作品',
    icon: 'Document'
  },
  {
    path: '/thinking',
    label: '思维',
    icon: 'FolderOpened'
  },
  {
    path: '/articles',
    label: '🔥精华内容',
    icon: 'Files'
  }
])

// 事件处理
const handleBannerClick = () => {
  // 处理横幅点击事件
  console.log('Banner clicked')
}

const goToProfile = () => {
  router.push('/profile')
}

const logout = () => {
  // 处理登出逻辑
  console.log('Logout')
}
</script>

<style scoped lang="scss">
.layout-container {
  min-height: 100vh;
  background-color: #f5f6fa;
}

.banner-bar-wrapper {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 8px 0;
}

.banner-bar {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.banner-content {
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}

.banner-text {
  display: flex;
  align-items: center;
  gap: 12px;
}

.banner-title {
  color: white;
  font-size: 14px;
  font-weight: 500;
}

.detail-button {
  color: white !important;
  
  .ml-1 {
    margin-left: 4px;
  }
}

.top-navbar {
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.navbar-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 64px;
}

.navbar-left {
  display: flex;
  align-items: center;
  gap: 32px;
}

.logo-section {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-icon img {
  width: 32px;
  height: 32px;
}

.system-name {
  font-size: 18px;
  font-weight: 600;
  color: #1a1a1a;
}

.nav-menu {
  display: flex;
  align-items: center;
  gap: 24px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 12px;
  border-radius: 6px;
  color: #666;
  text-decoration: none;
  transition: all 0.2s ease;

  &:hover {
    color: #3ed3cf;
    background-color: rgba(62, 211, 207, 0.1);
  }

  &.active {
    color: #3ed3cf;
    background-color: rgba(62, 211, 207, 0.1);
  }
}

.nav-icon {
  font-size: 16px;
}

.navbar-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.invitation-btn {
  background: linear-gradient(135deg, #3ed3cf 0%, #0ea39f 100%);
  border: none;
  color: white;
}

.membership-center-btn {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  border: none;
  color: white;
}

.membership-badge-container {
  .membership-badge {
    background: linear-gradient(135deg, rgb(148, 163, 184) 0%, rgb(100, 116, 139) 100%);
    box-shadow: rgba(148, 163, 184, 0.2) 0px 2px 8px;
    border-radius: 8px;
    padding: 6px 12px;

    .badge-content {
      display: flex;
      align-items: center;
      gap: 6px;
      color: white;
      font-size: 12px;

      .badge-icon-img {
        width: 16px;
        height: 16px;
      }
    }
  }
}

.user-avatar-section {
  cursor: pointer;
  
  .user-avatar {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    background: linear-gradient(135deg, #3ed3cf 0%, #0ea39f 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
  }
}

.main-content {
  padding: 24px;
  max-width: 1200px;
  margin: 0 auto;
}

@media (max-width: 768px) {
  .navbar-content {
    padding: 0 16px;
  }

  .navbar-left {
    gap: 16px;
  }

  .nav-menu {
    display: none;
  }

  .btn-text {
    display: none;
  }

  .btn-text-mobile {
    display: inline;
  }

  .credit-unit-full {
    display: none;
  }

  .credit-unit-short {
    display: inline;
  }
}

@media (min-width: 769px) {
  .btn-text-mobile {
    display: none;
  }

  .credit-unit-short {
    display: none;
  }
}
</style>