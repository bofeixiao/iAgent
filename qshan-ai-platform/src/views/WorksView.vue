<template>
  <div class="works-container">
    <div class="page-header">
      <div class="creation-stats-card">
        <div class="card-header">
          <el-icon class="header-icon"><Histogram /></el-icon>
          <h2>我的创作统计</h2>
        </div>
        <div class="stats-content">
          <div class="stat-item">
            <span class="stat-number">{{ totalWorks }}</span>
            <span class="stat-label">总作品数</span>
          </div>
          <div class="stat-item">
            <span class="stat-number">{{ totalViews }}</span>
            <span class="stat-label">总浏览量</span>
          </div>
          <div class="stat-item">
            <span class="stat-number">{{ totalLikes }}</span>
            <span class="stat-label">获得点赞</span>
          </div>
        </div>
      </div>
    </div>

    <div class="works-controls">
      <div class="control-left">
        <el-button type="primary" @click="createNewWork">
          <el-icon><Plus /></el-icon>
          创建作品
        </el-button>
      </div>
      
      <div class="control-right">
        <el-select v-model="sortBy" placeholder="排序方式" style="width: 120px;">
          <el-option label="最新创建" value="newest" />
          <el-option label="最多浏览" value="views" />
          <el-option label="最多点赞" value="likes" />
        </el-select>
        
        <el-input
          v-model="searchQuery"
          placeholder="搜索作品..."
          style="width: 240px;"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
      </div>
    </div>

    <div class="works-grid">
      <div 
        v-for="work in filteredWorks" 
        :key="work.id"
        class="work-card"
        @click="openWork(work)"
      >
        <div class="work-thumbnail">
          <img :src="work.thumbnail" :alt="work.title" />
          <div class="work-type-badge">{{ work.type }}</div>
        </div>
        
        <div class="work-info">
          <h3 class="work-title">{{ work.title }}</h3>
          <p class="work-description">{{ work.description }}</p>
          
          <div class="work-meta">
            <span class="meta-item">
              <el-icon><Calendar /></el-icon>
              {{ formatDate(work.createdAt) }}
            </span>
            <span class="meta-item">
              <el-icon><View /></el-icon>
              {{ work.views }}
            </span>
            <span class="meta-item">
              <el-icon><Star /></el-icon>
              {{ work.likes }}
            </span>
          </div>
          
          <div class="work-actions">
            <el-button size="small" @click.stop="editWork(work)">
              编辑
            </el-button>
            <el-button size="small" plain @click.stop="shareWork(work)">
              分享
            </el-button>
            <el-dropdown @command="handleWorkAction">
              <el-button size="small" plain>
                <el-icon><MoreFilled /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item :command="{ action: 'duplicate', work }">
                    复制作品
                  </el-dropdown-item>
                  <el-dropdown-item :command="{ action: 'delete', work }" divided>
                    删除作品
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </div>
    </div>

    <div v-if="filteredWorks.length === 0" class="empty-state">
      <el-empty description="还没有作品">
        <el-button type="primary" @click="createNewWork">创建第一个作品</el-button>
      </el-empty>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { 
  Plus, 
  Search, 
  View, 
  Star,
  Calendar,
  Histogram,
  MoreFilled
} from '@element-plus/icons-vue'

const router = useRouter()

// 响应式数据
const searchQuery = ref('')
const sortBy = ref('newest')

// 统计数据
const totalWorks = ref(12)
const totalViews = ref('3.2k')
const totalLikes = ref(89)

// 示例作品数据
const works = reactive([
  {
    id: 1,
    title: '智能营销方案',
    description: '基于AI分析的精准营销策略制定',
    thumbnail: '/work-thumbnails/marketing.jpg',
    type: '商业策划',
    createdAt: '2024-02-10',
    views: 234,
    likes: 15
  },
  {
    id: 2,
    title: '产品设计思维导图',
    description: '用户体验驱动的产品设计流程图',
    thumbnail: '/work-thumbnails/design.jpg',
    type: '设计思维',
    createdAt: '2024-02-08',
    views: 189,
    likes: 22
  },
  {
    id: 3,
    title: '技术架构文档',
    description: '微服务架构设计与实现方案',
    thumbnail: '/work-thumbnails/tech.jpg',
    type: '技术方案',
    createdAt: '2024-02-05',
    views: 156,
    likes: 8
  }
])

// 计算属性
const filteredWorks = computed(() => {
  let filtered = works

  // 搜索过滤
  if (searchQuery.value) {
    filtered = filtered.filter(work => 
      work.title.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      work.description.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  // 排序
  if (sortBy.value === 'views') {
    filtered.sort((a, b) => b.views - a.views)
  } else if (sortBy.value === 'likes') {
    filtered.sort((a, b) => b.likes - a.likes)
  } else {
    filtered.sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
  }

  return filtered
})

// 方法
const createNewWork = () => {
  console.log('Creating new work')
  ElMessage.success('创建新作品功能开发中...')
}

const openWork = (work: any) => {
  console.log('Opening work:', work.title)
  ElMessage.info(`打开作品: ${work.title}`)
}

const editWork = (work: any) => {
  console.log('Editing work:', work.title)
  ElMessage.info(`编辑作品: ${work.title}`)
}

const shareWork = (work: any) => {
  console.log('Sharing work:', work.title)
  ElMessage.success(`已复制作品分享链接: ${work.title}`)
}

const handleWorkAction = async (command: { action: string, work: any }) => {
  const { action, work } = command
  
  if (action === 'duplicate') {
    ElMessage.success(`已复制作品: ${work.title}`)
  } else if (action === 'delete') {
    try {
      await ElMessageBox.confirm(
        '确定要删除这个作品吗？此操作不可逆。',
        '删除确认',
        {
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          type: 'warning',
        }
      )
      ElMessage.success(`已删除作品: ${work.title}`)
    } catch {
      ElMessage.info('已取消删除')
    }
  }
}

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('zh-CN')
}

onMounted(() => {
  console.log('Works view mounted')
})
</script>

<style scoped lang="scss">
.works-container {
  padding: 24px;
}

.page-header {
  margin-bottom: 32px;
}

.creation-stats-card {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  border: 1px solid #f0f0f0;

  .card-header {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 20px;

    .header-icon {
      font-size: 24px;
      color: #3ed3cf;
    }

    h2 {
      font-size: 20px;
      font-weight: 600;
      color: #1a1a1a;
      margin: 0;
    }
  }

  .stats-content {
    display: flex;
    gap: 32px;

    .stat-item {
      text-align: center;

      .stat-number {
        display: block;
        font-size: 32px;
        font-weight: 700;
        color: #3ed3cf;
        margin-bottom: 4px;
      }

      .stat-label {
        font-size: 14px;
        color: #666;
      }
    }
  }
}

.works-controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  gap: 16px;

  .control-right {
    display: flex;
    gap: 12px;
  }
}

.works-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 24px;
}

.work-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
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

.work-thumbnail {
  position: relative;
  width: 100%;
  height: 180px;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    background: linear-gradient(135deg, #3ed3cf 0%, #0ea39f 100%);
  }

  .work-type-badge {
    position: absolute;
    top: 8px;
    left: 8px;
    background: rgba(0, 0, 0, 0.7);
    color: white;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 12px;
  }
}

.work-info {
  padding: 20px;
}

.work-title {
  font-size: 18px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0 0 8px 0;
}

.work-description {
  font-size: 14px;
  color: #666;
  line-height: 1.5;
  margin-bottom: 16px;
}

.work-meta {
  display: flex;
  gap: 16px;
  margin-bottom: 16px;

  .meta-item {
    display: flex;
    align-items: center;
    gap: 4px;
    font-size: 12px;
    color: #888;
  }
}

.work-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

.empty-state {
  padding: 80px 0;
  text-align: center;
}

@media (max-width: 768px) {
  .works-controls {
    flex-direction: column;
    align-items: stretch;
    gap: 16px;

    .control-right {
      flex-direction: column;
      gap: 12px;
    }
  }

  .works-grid {
    grid-template-columns: 1fr;
  }

  .creation-stats-card {
    .stats-content {
      gap: 16px;
    }
  }
}
</style>