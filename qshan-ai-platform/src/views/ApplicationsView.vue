<template>
  <div class="applications-container">
    <div class="page-header">
      <h1>AI应用中心</h1>
      <p>探索强大的AI应用，提升您的创作效率</p>
      
      <div class="header-actions">
        <el-button type="primary" @click="createNewApp">
          <el-icon><Plus /></el-icon>
          创建应用
        </el-button>
      </div>
    </div>

    <div class="filters">
      <div class="filter-tabs">
        <el-radio-group v-model="activeTab" @change="handleTabChange">
          <el-radio-button label="all">全部应用</el-radio-button>
          <el-radio-button label="trending">热门推荐</el-radio-button>
          <el-radio-button label="my">我的应用</el-radio-button>
        </el-radio-group>
      </div>
      
      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="搜索应用..."
          @input="handleSearch"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
      </div>
    </div>

    <div class="applications-grid">
      <div 
        v-for="app in filteredApps" 
        :key="app.id"
        class="app-card"
        @click="openApp(app)"
      >
        <div class="app-image">
          <img :src="app.image" :alt="app.name" />
        </div>
        
        <div class="app-content">
          <div class="app-header">
            <h3 class="app-name">{{ app.name }}</h3>
            <div class="app-category">{{ app.category }}</div>
          </div>
          
          <p class="app-description">{{ app.description }}</p>
          
          <div class="app-stats">
            <span class="stat-item">
              <el-icon><View /></el-icon>
              {{ app.views }}
            </span>
            <span class="stat-item">
              <el-icon><Star /></el-icon>
              {{ app.rating }}
            </span>
          </div>
          
          <div class="app-actions">
            <el-button size="small" @click.stop="useApp(app)">
              立即使用
            </el-button>
            <el-button size="small" plain @click.stop="favoriteApp(app)">
              <el-icon><Star /></el-icon>
            </el-button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="loading" class="loading-container">
      <el-skeleton :rows="4" animated />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { 
  Plus, 
  Search, 
  View, 
  Star
} from '@element-plus/icons-vue'

const router = useRouter()

// 响应式数据
const activeTab = ref('all')
const searchQuery = ref('')
const loading = ref(false)

// 示例应用数据
const applications = reactive([
  {
    id: 1,
    name: '智能写作助手',
    category: '文本创作',
    description: '基于大语言模型的智能写作工具，支持多种文体创作',
    image: '/app-icons/writing.png',
    views: '12.5k',
    rating: '4.8',
    trending: true
  },
  {
    id: 2,
    name: 'AI图像生成器',
    category: '图像处理',
    description: '通过文字描述生成高质量的AI图像作品',
    image: '/app-icons/image-gen.png',
    views: '8.9k',
    rating: '4.7',
    trending: true
  },
  {
    id: 3,
    name: '代码智能补全',
    category: '编程工具',
    description: '智能代码生成和补全，支持多种编程语言',
    image: '/app-icons/code.png',
    views: '15.2k',
    rating: '4.9',
    trending: false
  },
  {
    id: 4,
    name: '数据可视化',
    category: '数据分析',
    description: '将复杂数据转换为直观的可视化图表',
    image: '/app-icons/chart.png',
    views: '6.3k',
    rating: '4.6',
    trending: false
  }
])

// 计算属性
const filteredApps = computed(() => {
  let apps = applications

  // 根据标签筛选
  if (activeTab.value === 'trending') {
    apps = apps.filter(app => app.trending)
  } else if (activeTab.value === 'my') {
    apps = apps.filter(app => app.id <= 2) // 示例：前两个是用户的应用
  }

  // 根据搜索查询筛选
  if (searchQuery.value) {
    apps = apps.filter(app => 
      app.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      app.description.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  return apps
})

// 方法
const handleTabChange = (value: string) => {
  console.log('Tab changed to:', value)
}

const handleSearch = (value: string) => {
  console.log('Search query:', value)
}

const createNewApp = () => {
  console.log('Create new app')
  // 这里可以打开创建应用的模态框或跳转到创建页面
}

const openApp = (app: any) => {
  console.log('Opening app:', app.name)
  // 这里可以跳转到应用详情页或直接使用应用
}

const useApp = (app: any) => {
  console.log('Using app:', app.name)
  // 这里可以跳转到应用使用页面
}

const favoriteApp = (app: any) => {
  console.log('Favoriting app:', app.name)
  // 这里可以添加收藏逻辑
}

onMounted(() => {
  // 组件挂载后的初始化操作
  console.log('Applications view mounted')
})
</script>

<style scoped lang="scss">
.applications-container {
  padding: 24px;
}

.page-header {
  margin-bottom: 32px;
  
  h1 {
    font-size: 32px;
    font-weight: 700;
    color: #1a1a1a;
    margin-bottom: 8px;
  }
  
  p {
    font-size: 16px;
    color: #666;
    margin-bottom: 24px;
  }
}

.header-actions {
  display: flex;
  gap: 12px;
}

.filters {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  gap: 16px;
  
  .search-bar {
    width: 300px;
  }
}

.applications-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 24px;
  margin-bottom: 32px;
}

.app-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  border: 1px solid #f0f0f0;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
    border-color: #3ed3cf;
  }
}

.app-image {
  width: 64px;
  height: 64px;
  margin-bottom: 16px;
  
  img {
    width: 100%;
    height: 100%;
    border-radius: 8px;
    object-fit: cover;
    background: linear-gradient(135deg, #3ed3cf 0%, #0ea39f 100%);
  }
}

.app-content {
  flex: 1;
}

.app-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 8px;
}

.app-name {
  font-size: 18px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0;
}

.app-category {
  font-size: 12px;
  padding: 4px 8px;
  background: rgba(62, 211, 207, 0.1);
  color: #3ed3cf;
  border-radius: 4px;
}

.app-description {
  font-size: 14px;
  color: #666;
  line-height: 1.5;
  margin-bottom: 16px;
}

.app-stats {
  display: flex;
  gap: 16px;
  margin-bottom: 16px;
  
  .stat-item {
    display: flex;
    align-items: center;
    gap: 4px;
    font-size: 12px;
    color: #888;
  }
}

.app-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

.loading-container {
  padding: 24px;
}

@media (max-width: 768px) {
  .filters {
    flex-direction: column;
    align-items: stretch;
    gap: 16px;
    
    .search-bar {
      width: 100%;
    }
  }
  
  .applications-grid {
    grid-template-columns: 1fr;
  }
  
  .page-header h1 {
    font-size: 24px;
  }
}
</style>