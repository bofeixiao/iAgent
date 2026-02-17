<template>
  <div class="home-container">
    <el-carousel :interval="5000" arrow="always" height="400px">
      <el-carousel-item v-for="item in banners" :key="item.id">
        <div class="banner-item">
          <h1>{{ item.title }}</h1>
          <p>{{ item.description }}</p>
        </div>
      </el-carousel-item>
    </el-carousel>

    <div class="section">
      <div class="section-header">
        <h2>热门应用</h2>
        <el-button text @click="router.push('/applications')">
          查看更多 <el-icon><ArrowRight /></el-icon>
        </el-button>
      </div>
      <div class="app-grid">
        <el-skeleton v-for="n in 4" :key="n" :rows="3" animated />
      </div>
    </div>

    <div class="section">
      <div class="section-header">
        <h2>精华内容</h2>
        <el-button text @click="router.push('/articles')">
          查看更多 <el-icon><ArrowRight /></el-icon>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowRight } from '@element-plus/icons-vue'

const router = useRouter()

const banners = ref([
  {
    id: 1,
    title: '欢迎来到 iData AI 创作平台',
    description: '释放AI的力量，让创作更简单'
  },
  {
    id: 2,
    title: '海量应用模板',
    description: '文案、图片、视频，应有尽有'
  },
  {
    id: 3,
    title: '智能思维分析',
    description: '深度了解你的创作风格'
  }
])
</script>

<style scoped lang="scss">
.banner-item {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;

  h1 {
    font-size: 48px;
    margin: 0 0 16px 0;
  }

  p {
    font-size: 24px;
    margin: 0;
  }
}

.section {
  margin: 48px 0;

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;

    h2 {
      font-size: 28px;
      margin: 0;
    }
  }
}

.app-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
}
</style>
