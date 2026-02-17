<template>
  <div class="profile-container">
    <div class="profile-header">
      <div class="profile-banner">
        <div class="banner-overlay"></div>
        <div class="profile-info">
          <div class="avatar-section">
            <el-avatar :size="120" :src="userInfo.avatar">
              <el-icon><User /></el-icon>
            </el-avatar>
            <el-button class="edit-avatar-btn" circle @click="editAvatar">
              <el-icon><Camera /></el-icon>
            </el-button>
          </div>
          
          <div class="user-details">
            <h1 class="username">{{ userInfo.username }}</h1>
            <p class="user-bio">{{ userInfo.bio }}</p>
            
            <div class="user-stats">
              <div class="stat-item">
                <span class="stat-number">{{ userStats.totalWorks }}</span>
                <span class="stat-label">作品</span>
              </div>
              <div class="stat-item">
                <span class="stat-number">{{ userStats.totalViews }}</span>
                <span class="stat-label">浏览量</span>
              </div>
              <div class="stat-item">
                <span class="stat-number">{{ userStats.followers }}</span>
                <span class="stat-label">关注者</span>
              </div>
              <div class="stat-item">
                <span class="stat-number">{{ userStats.following }}</span>
                <span class="stat-label">关注中</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="profile-content">
      <div class="profile-nav">
        <el-tabs v-model="activeTab" @tab-change="handleTabChange">
          <el-tab-pane label="个人信息" name="info">
            <div class="info-section">
              <el-form :model="userInfo" label-width="100px" class="profile-form">
                <el-form-item label="用户名">
                  <el-input v-model="userInfo.username" :disabled="!editMode" />
                </el-form-item>
                
                <el-form-item label="邮箱">
                  <el-input v-model="userInfo.email" :disabled="!editMode" />
                </el-form-item>
                
                <el-form-item label="手机号">
                  <el-input v-model="userInfo.phone" :disabled="!editMode" />
                </el-form-item>
                
                <el-form-item label="个人简介">
                  <el-input 
                    v-model="userInfo.bio" 
                    type="textarea" 
                    :rows="3"
                    :disabled="!editMode"
                  />
                </el-form-item>
                
                <el-form-item label="职业">
                  <el-input v-model="userInfo.profession" :disabled="!editMode" />
                </el-form-item>
                
                <el-form-item label="所在地">
                  <el-input v-model="userInfo.location" :disabled="!editMode" />
                </el-form-item>
                
                <el-form-item>
                  <el-button 
                    v-if="!editMode" 
                    type="primary" 
                    @click="enableEdit"
                  >
                    编辑资料
                  </el-button>
                  <template v-else>
                    <el-button type="primary" @click="saveProfile">
                      保存更改
                    </el-button>
                    <el-button @click="cancelEdit">
                      取消
                    </el-button>
                  </template>
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>

          <el-tab-pane label="会员中心" name="membership">
            <div class="membership-section">
              <div class="membership-card">
                <div class="membership-header">
                  <div class="membership-badge">
                    <img src="/vip-icon.svg" alt="VIP" />
                    <span>VIP会员</span>
                  </div>
                  <div class="membership-status active">
                    已激活
                  </div>
                </div>
                
                <div class="membership-benefits">
                  <h3>会员权益</h3>
                  <ul>
                    <li>✨ 无限制使用所有AI应用</li>
                    <li>🚀 优先访问新功能</li>
                    <li>📊 高级数据分析报告</li>
                    <li>☁️ 扩展云存储空间</li>
                    <li>🎯 专属客服支持</li>
                  </ul>
                </div>
                
                <div class="membership-info">
                  <div class="info-item">
                    <span class="label">会员类型</span>
                    <span class="value">年费会员</span>
                  </div>
                  <div class="info-item">
                    <span class="label">到期时间</span>
                    <span class="value">2024年12月31日</span>
                  </div>
                  <div class="info-item">
                    <span class="label">剩余积分</span>
                    <span class="value">{{ userInfo.credits }} 分</span>
                  </div>
                </div>
                
                <div class="membership-actions">
                  <el-button type="primary" @click="renewMembership">
                    续费会员
                  </el-button>
                  <el-button @click="viewBenefits">
                    查看详情
                  </el-button>
                </div>
              </div>
            </div>
          </el-tab-pane>

          <el-tab-pane label="账户安全" name="security">
            <div class="security-section">
              <div class="security-item">
                <div class="security-info">
                  <h4>登录密码</h4>
                  <p>定期更换密码可以提高账户安全性</p>
                </div>
                <el-button @click="changePassword">
                  修改密码
                </el-button>
              </div>
              
              <div class="security-item">
                <div class="security-info">
                  <h4>两步验证</h4>
                  <p>为您的账户添加额外的安全保护</p>
                </div>
                <el-switch 
                  v-model="securitySettings.twoFactorEnabled"
                  @change="toggleTwoFactor"
                />
              </div>
              
              <div class="security-item">
                <div class="security-info">
                  <h4>登录通知</h4>
                  <p>当有新设备登录时发送邮件通知</p>
                </div>
                <el-switch 
                  v-model="securitySettings.loginNotification"
                  @change="toggleLoginNotification"
                />
              </div>
              
              <div class="security-item">
                <div class="security-info">
                  <h4>设备管理</h4>
                  <p>管理已登录的设备</p>
                </div>
                <el-button @click="manageDevices">
                  管理设备
                </el-button>
              </div>
            </div>
          </el-tab-pane>

          <el-tab-pane label="使用记录" name="activity">
            <div class="activity-section">
              <div class="activity-stats">
                <div class="stat-card">
                  <h3>本月使用情况</h3>
                  <div class="stat-value">{{ monthlyStats.totalUsage }}</div>
                  <div class="stat-desc">总使用次数</div>
                </div>
                
                <div class="stat-card">
                  <h3>最常用应用</h3>
                  <div class="stat-value">{{ monthlyStats.favoriteApp }}</div>
                  <div class="stat-desc">智能写作助手</div>
                </div>
                
                <div class="stat-card">
                  <h3>创作产出</h3>
                  <div class="stat-value">{{ monthlyStats.createdWorks }}</div>
                  <div class="stat-desc">个作品</div>
                </div>
              </div>
              
              <div class="activity-list">
                <h3>最近活动</h3>
                <div class="activity-timeline">
                  <div 
                    v-for="activity in recentActivities" 
                    :key="activity.id"
                    class="activity-item"
                  >
                    <div class="activity-icon">
                      <el-icon><component :is="activity.icon" /></el-icon>
                    </div>
                    <div class="activity-content">
                      <div class="activity-title">{{ activity.title }}</div>
                      <div class="activity-desc">{{ activity.description }}</div>
                      <div class="activity-time">{{ activity.time }}</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { 
  User, 
  Camera,
  Edit,
  Document,
  Star,
  Clock
} from '@element-plus/icons-vue'

// 响应式数据
const activeTab = ref('info')
const editMode = ref(false)

// 用户信息
const userInfo = reactive({
  username: '张晓明',
  email: 'zhang.xiaoming@example.com',
  phone: '138****8888',
  bio: '热爱AI创作，专注数字营销和产品设计',
  profession: '产品经理',
  location: '北京市',
  avatar: '/avatars/user-avatar.jpg',
  credits: 5
})

// 原始用户信息（用于取消编辑时恢复）
const originalUserInfo = { ...userInfo }

// 用户统计
const userStats = reactive({
  totalWorks: 24,
  totalViews: '8.5k',
  followers: 156,
  following: 89
})

// 安全设置
const securitySettings = reactive({
  twoFactorEnabled: false,
  loginNotification: true
})

// 月度统计
const monthlyStats = reactive({
  totalUsage: 127,
  favoriteApp: '智能写作助手',
  createdWorks: 8
})

// 最近活动
const recentActivities = reactive([
  {
    id: 1,
    title: '创建了新作品',
    description: '《智能营销方案》',
    time: '2小时前',
    icon: 'Document'
  },
  {
    id: 2,
    title: '使用了AI应用',
    description: '智能写作助手',
    time: '4小时前',
    icon: 'Edit'
  },
  {
    id: 3,
    title: '收到新点赞',
    description: '作品《产品设计思维导图》获得3个赞',
    time: '1天前',
    icon: 'Star'
  },
  {
    id: 4,
    title: '更新了个人资料',
    description: '修改了个人简介',
    time: '2天前',
    icon: 'User'
  }
])

// 方法
const handleTabChange = (tabName: string) => {
  console.log('Tab changed to:', tabName)
}

const editAvatar = () => {
  ElMessage.info('头像编辑功能开发中...')
}

const enableEdit = () => {
  editMode.value = true
}

const saveProfile = () => {
  // 保存逻辑
  editMode.value = false
  ElMessage.success('个人资料已更新')
  
  // 更新原始信息
  Object.assign(originalUserInfo, userInfo)
}

const cancelEdit = () => {
  // 恢复原始信息
  Object.assign(userInfo, originalUserInfo)
  editMode.value = false
  ElMessage.info('已取消编辑')
}

const renewMembership = () => {
  ElMessage.info('续费功能开发中...')
}

const viewBenefits = () => {
  ElMessage.info('查看会员详情功能开发中...')
}

const changePassword = async () => {
  try {
    await ElMessageBox.prompt('请输入新密码', '修改密码', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputType: 'password'
    })
    ElMessage.success('密码修改成功')
  } catch {
    ElMessage.info('已取消修改')
  }
}

const toggleTwoFactor = (value: boolean) => {
  ElMessage.success(value ? '已开启两步验证' : '已关闭两步验证')
}

const toggleLoginNotification = (value: boolean) => {
  ElMessage.success(value ? '已开启登录通知' : '已关闭登录通知')
}

const manageDevices = () => {
  ElMessage.info('设备管理功能开发中...')
}

onMounted(() => {
  console.log('Profile view mounted')
})
</script>

<style scoped lang="scss">
.profile-container {
  min-height: calc(100vh - 160px);
}

.profile-header {
  margin-bottom: 32px;
}

.profile-banner {
  position: relative;
  height: 300px;
  background: linear-gradient(135deg, #3ed3cf 0%, #0ea39f 100%);
  border-radius: 12px;
  overflow: hidden;
  
  .banner-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.3);
  }
  
  .profile-info {
    position: absolute;
    bottom: 32px;
    left: 32px;
    right: 32px;
    display: flex;
    align-items: flex-end;
    gap: 24px;
    color: white;
  }
}

.avatar-section {
  position: relative;
  
  .edit-avatar-btn {
    position: absolute;
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.7);
    border: none;
    color: white;
    
    &:hover {
      background: rgba(0, 0, 0, 0.9);
    }
  }
}

.user-details {
  flex: 1;
  
  .username {
    font-size: 32px;
    font-weight: 700;
    margin: 0 0 8px 0;
  }
  
  .user-bio {
    font-size: 16px;
    opacity: 0.9;
    margin-bottom: 20px;
  }
}

.user-stats {
  display: flex;
  gap: 32px;
  
  .stat-item {
    text-align: center;
    
    .stat-number {
      display: block;
      font-size: 24px;
      font-weight: 700;
      margin-bottom: 4px;
    }
    
    .stat-label {
      font-size: 14px;
      opacity: 0.8;
    }
  }
}

.profile-content {
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  border: 1px solid #f0f0f0;
  overflow: hidden;
}

.profile-nav {
  padding: 24px;
}

.profile-form {
  max-width: 600px;
}

.membership-section {
  max-width: 600px;
}

.membership-card {
  background: linear-gradient(135deg, #f8f9ff 0%, #e8f4fd 100%);
  border: 1px solid #e0e6ff;
  border-radius: 12px;
  padding: 24px;
  
  .membership-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
    
    .membership-badge {
      display: flex;
      align-items: center;
      gap: 8px;
      font-size: 18px;
      font-weight: 600;
      color: #1a1a1a;
      
      img {
        width: 24px;
        height: 24px;
      }
    }
    
    .membership-status {
      background: #52c41a;
      color: white;
      padding: 4px 12px;
      border-radius: 12px;
      font-size: 12px;
      
      &.active {
        background: #52c41a;
      }
    }
  }
  
  .membership-benefits {
    margin-bottom: 24px;
    
    h3 {
      font-size: 16px;
      font-weight: 600;
      margin-bottom: 12px;
      color: #1a1a1a;
    }
    
    ul {
      list-style: none;
      padding: 0;
      margin: 0;
      
      li {
        padding: 6px 0;
        font-size: 14px;
        color: #333;
      }
    }
  }
  
  .membership-info {
    margin-bottom: 24px;
    
    .info-item {
      display: flex;
      justify-content: space-between;
      padding: 8px 0;
      border-bottom: 1px solid rgba(0, 0, 0, 0.05);
      
      &:last-child {
        border-bottom: none;
      }
      
      .label {
        color: #666;
        font-size: 14px;
      }
      
      .value {
        color: #1a1a1a;
        font-weight: 500;
        font-size: 14px;
      }
    }
  }
  
  .membership-actions {
    display: flex;
    gap: 12px;
  }
}

.security-section {
  max-width: 600px;
}

.security-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0;
  border-bottom: 1px solid #f0f0f0;
  
  &:last-child {
    border-bottom: none;
  }
  
  .security-info {
    flex: 1;
    
    h4 {
      font-size: 16px;
      font-weight: 600;
      color: #1a1a1a;
      margin: 0 0 4px 0;
    }
    
    p {
      font-size: 14px;
      color: #666;
      margin: 0;
    }
  }
}

.activity-section {
  .activity-stats {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 24px;
    margin-bottom: 32px;
    
    .stat-card {
      background: #f8f9fa;
      border-radius: 8px;
      padding: 20px;
      text-align: center;
      
      h3 {
        font-size: 14px;
        color: #666;
        margin: 0 0 12px 0;
      }
      
      .stat-value {
        font-size: 32px;
        font-weight: 700;
        color: #3ed3cf;
        margin-bottom: 4px;
      }
      
      .stat-desc {
        font-size: 12px;
        color: #888;
      }
    }
  }
  
  .activity-list {
    h3 {
      font-size: 18px;
      font-weight: 600;
      margin-bottom: 20px;
      color: #1a1a1a;
    }
  }
  
  .activity-timeline {
    .activity-item {
      display: flex;
      gap: 16px;
      padding: 16px 0;
      border-bottom: 1px solid #f0f0f0;
      
      &:last-child {
        border-bottom: none;
      }
      
      .activity-icon {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background: rgba(62, 211, 207, 0.1);
        display: flex;
        align-items: center;
        justify-content: center;
        color: #3ed3cf;
        flex-shrink: 0;
      }
      
      .activity-content {
        flex: 1;
        
        .activity-title {
          font-size: 14px;
          font-weight: 500;
          color: #1a1a1a;
          margin-bottom: 4px;
        }
        
        .activity-desc {
          font-size: 13px;
          color: #666;
          margin-bottom: 4px;
        }
        
        .activity-time {
          font-size: 12px;
          color: #999;
        }
      }
    }
  }
}

@media (max-width: 768px) {
  .profile-banner {
    height: 200px;
    
    .profile-info {
      flex-direction: column;
      align-items: center;
      text-align: center;
      bottom: 16px;
      left: 16px;
      right: 16px;
    }
  }
  
  .user-stats {
    gap: 16px;
    justify-content: center;
  }
  
  .profile-nav {
    padding: 16px;
  }
  
  .membership-card {
    padding: 16px;
  }
}
</style>