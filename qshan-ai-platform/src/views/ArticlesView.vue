<template>
  <div class="articles-container">
    <div class="page-header">
      <h1>🔥精华内容</h1>
      <p>发现优质内容，获取创作灵感</p>
    </div>

    <div class="content-filters">
      <div class="filter-tabs">
        <el-radio-group v-model="activeCategory" @change="handleCategoryChange">
          <el-radio-button label="all">全部内容</el-radio-button>
          <el-radio-button label="trending">热门推荐</el-radio-button>
          <el-radio-button label="latest">最新发布</el-radio-button>
          <el-radio-button label="featured">编辑精选</el-radio-button>
        </el-radio-group>
      </div>
      
      <div class="filter-controls">
        <el-select v-model="selectedTags" multiple placeholder="选择标签" style="width: 200px;">
          <el-option
            v-for="tag in availableTags"
            :key="tag.value"
            :label="tag.label"
            :value="tag.value"
          />
        </el-select>
        
        <el-input
          v-model="searchQuery"
          placeholder="搜索内容..."
          style="width: 240px;"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
      </div>
    </div>

    <div class="articles-grid">
      <article 
        v-for="article in filteredArticles" 
        :key="article.id"
        class="article-card"
        @click="openArticle(article)"
      >
        <div class="article-image">
          <img :src="article.coverImage" :alt="article.title" />
          <div class="article-category">{{ article.category }}</div>
          <div class="article-featured" v-if="article.featured">
            <el-icon><Star /></el-icon>
          </div>
        </div>
        
        <div class="article-content">
          <div class="article-header">
            <h3 class="article-title">{{ article.title }}</h3>
            <div class="article-rating">
              <el-rate
                v-model="article.rating"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value}"
                size="small"
              />
            </div>
          </div>
          
          <p class="article-excerpt">{{ article.excerpt }}</p>
          
          <div class="article-tags">
            <el-tag 
              v-for="tag in article.tags" 
              :key="tag"
              size="small"
              type="info"
            >
              {{ tag }}
            </el-tag>
          </div>
          
          <div class="article-meta">
            <div class="author-info">
              <el-avatar :size="24" :src="article.author.avatar" />
              <span class="author-name">{{ article.author.name }}</span>
            </div>
            
            <div class="article-stats">
              <span class="stat-item">
                <el-icon><View /></el-icon>
                {{ formatNumber(article.views) }}
              </span>
              <span class="stat-item">
                <el-icon><ChatLineSquare /></el-icon>
                {{ article.comments }}
              </span>
              <span class="stat-item">
                <el-icon><Star /></el-icon>
                {{ article.likes }}
              </span>
            </div>
          </div>
          
          <div class="article-date">
            {{ formatDate(article.publishedAt) }}
          </div>
        </div>
      </article>
    </div>

    <div class="load-more-section" v-if="hasMore">
      <el-button 
        @click="loadMore" 
        :loading="isLoading"
        size="large"
      >
        加载更多
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { 
  Search, 
  View, 
  Star,
  ChatLineSquare
} from '@element-plus/icons-vue'

const router = useRouter()

// 响应式数据
const activeCategory = ref('all')
const selectedTags = ref([])
const searchQuery = ref('')
const isLoading = ref(false)
const hasMore = ref(true)

// 可用标签
const availableTags = [
  { label: 'AI创作', value: 'ai' },
  { label: '营销策略', value: 'marketing' },
  { label: '产品设计', value: 'design' },
  { label: '技术趋势', value: 'tech' },
  { label: '用户体验', value: 'ux' },
  { label: '数据分析', value: 'data' }
]

// 示例文章数据
const articles = reactive([
  {
    id: 1,
    title: 'AI创作工具的未来发展趋势与机遇',
    excerpt: '随着人工智能技术的快速发展，AI创作工具正在重塑内容创作行业。本文深入分析了当前AI创作工具的发展现状，探讨未来可能的发展方向...',
    coverImage: '/article-covers/ai-future.jpg',
    category: 'AI技术',
    tags: ['AI创作', '技术趋势', '行业分析'],
    author: {
      name: '张晓明',
      avatar: '/avatars/author1.jpg'
    },
    rating: 4.8,
    views: 15420,
    comments: 89,
    likes: 234,
    publishedAt: '2024-02-10',
    featured: true,
    trending: true
  },
  {
    id: 2,
    title: '如何运用设计思维提升产品用户体验',
    excerpt: '设计思维作为一种以用户为中心的创新方法论，在产品设计中发挥着重要作用。本文将通过实际案例，详细介绍设计思维的五个阶段...',
    coverImage: '/article-covers/design-thinking.jpg',
    category: '产品设计',
    tags: ['产品设计', '用户体验', '设计思维'],
    author: {
      name: '李小红',
      avatar: '/avatars/author2.jpg'
    },
    rating: 4.6,
    views: 8930,
    comments: 45,
    likes: 156,
    publishedAt: '2024-02-08',
    featured: false,
    trending: true
  },
  {
    id: 3,
    title: '数字营销中的用户画像构建与应用',
    excerpt: '用户画像是数字营销的基础，通过精准的用户画像，企业可以更好地理解目标用户需求，制定有效的营销策略。本文将介绍用户画像的构建方法...',
    coverImage: '/article-covers/user-persona.jpg',
    category: '营销策略',
    tags: ['营销策略', '用户画像', '数据分析'],
    author: {
      name: '王大伟',
      avatar: '/avatars/author3.jpg'
    },
    rating: 4.7,
    views: 12560,
    comments: 67,
    likes: 198,
    publishedAt: '2024-02-06',
    featured: true,
    trending: false
  },
  {
    id: 4,
    title: '低代码平台在企业数字化转型中的应用',
    excerpt: '低代码平台正在成为企业数字化转型的重要工具。通过可视化的开发方式，企业可以快速构建应用程序，提高开发效率，降低技术门槛...',
    coverImage: '/article-covers/low-code.jpg',
    category: '技术应用',
    tags: ['低代码', '数字化转型', '企业应用'],
    author: {
      name: '孙小华',
      avatar: '/avatars/author4.jpg'
    },
    rating: 4.5,
    views: 6780,
    comments: 32,
    likes: 87,
    publishedAt: '2024-02-04',
    featured: false,
    trending: false
  }
])

// 计算属性
const filteredArticles = computed(() => {
  let filtered = articles

  // 分类筛选
  if (activeCategory.value === 'trending') {
    filtered = filtered.filter(article => article.trending)
  } else if (activeCategory.value === 'featured') {
    filtered = filtered.filter(article => article.featured)
  } else if (activeCategory.value === 'latest') {
    filtered = [...filtered].sort((a, b) => 
      new Date(b.publishedAt).getTime() - new Date(a.publishedAt).getTime()
    )
  }

  // 标签筛选
  if (selectedTags.value.length > 0) {
    filtered = filtered.filter(article =>
      selectedTags.value.some(tag =>
        article.tags.some(articleTag =>
          availableTags.find(t => t.value === tag)?.label === articleTag
        )
      )
    )
  }

  // 搜索筛选
  if (searchQuery.value) {
    const query = searchQuery.value.toLowerCase()
    filtered = filtered.filter(article =>
      article.title.toLowerCase().includes(query) ||
      article.excerpt.toLowerCase().includes(query) ||
      article.tags.some(tag => tag.toLowerCase().includes(query))
    )
  }

  return filtered
})

// 方法
const handleCategoryChange = (value: string) => {
  console.log('Category changed to:', value)
}

const openArticle = (article: any) => {
  console.log('Opening article:', article.title)
  ElMessage.info(`正在打开文章: ${article.title}`)
}

const loadMore = async () => {
  isLoading.value = true
  try {
    // 模拟加载更多数据
    await new Promise(resolve => setTimeout(resolve, 1000))
    ElMessage.success('已加载更多内容')
    hasMore.value = false // 演示用，实际根据数据情况判断
  } catch (error) {
    ElMessage.error('加载失败，请重试')
  } finally {
    isLoading.value = false
  }
}

const formatNumber = (num: number) => {
  if (num >= 1000) {
    return (num / 1000).toFixed(1) + 'k'
  }
  return num.toString()
}

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('zh-CN')
}

onMounted(() => {
  console.log('Articles view mounted')
})
</script>

<style scoped lang="scss">
.articles-container {
  padding: 24px;
}

.page-header {
  margin-bottom: 32px;
  text-align: center;
  
  h1 {
    font-size: 32px;
    font-weight: 700;
    color: #1a1a1a;
    margin-bottom: 8px;
  }
  
  p {
    font-size: 16px;
    color: #666;
  }
}

.content-filters {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  gap: 16px;
  
  .filter-controls {
    display: flex;
    gap: 12px;
  }
}

.articles-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(360px, 1fr));
  gap: 24px;
  margin-bottom: 40px;
}

.article-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  border: 1px solid #f0f0f0;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
    border-color: #3ed3cf;
  }
}

.article-image {
  position: relative;
  width: 100%;
  height: 200px;
  overflow: hidden;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    background: linear-gradient(135deg, #3ed3cf 0%, #0ea39f 100%);
  }

  .article-category {
    position: absolute;
    top: 12px;
    left: 12px;
    background: rgba(0, 0, 0, 0.7);
    color: white;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 12px;
  }

  .article-featured {
    position: absolute;
    top: 12px;
    right: 12px;
    background: #ff9900;
    color: white;
    padding: 4px;
    border-radius: 50%;
    width: 24px;
    height: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}

.article-content {
  padding: 20px;
}

.article-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 12px;
}

.article-title {
  font-size: 18px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0;
  line-height: 1.4;
  flex: 1;
  margin-right: 8px;
}

.article-rating {
  white-space: nowrap;
}

.article-excerpt {
  font-size: 14px;
  color: #666;
  line-height: 1.6;
  margin-bottom: 16px;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.article-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-bottom: 16px;
}

.article-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 8px;
  
  .author-name {
    font-size: 14px;
    color: #666;
  }
}

.article-stats {
  display: flex;
  gap: 12px;
  
  .stat-item {
    display: flex;
    align-items: center;
    gap: 4px;
    font-size: 12px;
    color: #888;
  }
}

.article-date {
  font-size: 12px;
  color: #999;
}

.load-more-section {
  text-align: center;
}

@media (max-width: 768px) {
  .content-filters {
    flex-direction: column;
    align-items: stretch;
    gap: 16px;
    
    .filter-controls {
      flex-direction: column;
      gap: 12px;
    }
  }
  
  .articles-grid {
    grid-template-columns: 1fr;
  }
  
  .page-header h1 {
    font-size: 24px;
  }
}
</style>