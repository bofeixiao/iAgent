<script setup lang="ts">
import { ref } from 'vue'
import { Grid, Document, FolderOpened, Files } from '@element-plus/icons-vue'

const welcomeMessage = ref('欢迎来到青山AI创作平台')
const description = ref('让AI创作更加简单高效')
</script>

<template>
  <div class="home-container">
    <div class="hero-section">
      <h1 class="hero-title">{{ welcomeMessage }}</h1>
      <p class="hero-description">{{ description }}</p>
      
      <div class="action-buttons">
        <el-button type="primary" size="large" @click="$router.push('/apps')">
          开始创作
        </el-button>
        <el-button size="large" @click="$router.push('/articles')">
          浏览精华
        </el-button>
      </div>
    </div>

    <div class="feature-cards">
      <div class="feature-card" @click="$router.push('/apps')">
        <el-icon class="feature-icon"><Grid /></el-icon>
        <h3>AI应用</h3>
        <p>强大的AI应用生态，满足各种创作需求</p>
      </div>
      
      <div class="feature-card" @click="$router.push('/works')">
        <el-icon class="feature-icon"><Document /></el-icon>
        <h3>作品展示</h3>
        <p>展示和分享您的创作作品</p>
      </div>
      
      <div class="feature-card" @click="$router.push('/thinking')">
        <el-icon class="feature-icon"><FolderOpened /></el-icon>
        <h3>思维导图</h3>
        <p>可视化思维过程，整理创作思路</p>
      </div>
      
      <div class="feature-card" @click="$router.push('/articles')">
        <el-icon class="feature-icon"><Files /></el-icon>
        <h3>精华内容</h3>
        <p>精选优质内容，获取创作灵感</p>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.home-container {
  padding: 40px 20px;
}

.hero-section {
  text-align: center;
  margin-bottom: 60px;
}

.hero-title {
  font-size: 48px;
  font-weight: 700;
  background: linear-gradient(135deg, #3ed3cf 0%, #0ea39f 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 16px;
}

.hero-description {
  font-size: 18px;
  color: #666;
  margin-bottom: 32px;
}

.action-buttons {
  display: flex;
  gap: 16px;
  justify-content: center;
  flex-wrap: wrap;
}

.feature-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
  max-width: 1200px;
  margin: 0 auto;
}

.feature-card {
  background: white;
  border-radius: 12px;
  padding: 32px 24px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #f0f0f0;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
    border-color: #3ed3cf;
  }
}

.feature-icon {
  font-size: 48px;
  color: #3ed3cf;
  margin-bottom: 16px;
}

.feature-card h3 {
  font-size: 20px;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 8px;
}

.feature-card p {
  font-size: 14px;
  color: #666;
  line-height: 1.5;
}

@media (max-width: 768px) {
  .hero-title {
    font-size: 32px;
  }

  .hero-description {
    font-size: 16px;
  }

  .action-buttons {
    flex-direction: column;
    align-items: center;
  }

  .feature-cards {
    grid-template-columns: 1fr;
  }
}
</style>
