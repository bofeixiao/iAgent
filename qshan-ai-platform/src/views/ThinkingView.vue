<template>
  <div class="thinking-container">
    <div class="thinking-header">
      <div class="header-content">
        <h1>AI思维导图</h1>
        <p class="description-subtitle">让AI创作更有条理，思路更清晰</p>
        
        <div class="action-buttons">
          <el-button type="primary" size="large" @click="createMindMap">
            <el-icon><Plus /></el-icon>
            创建思维导图
          </el-button>
          <el-button size="large" @click="importMindMap">
            <el-icon><Upload /></el-icon>
            导入导图
          </el-button>
        </div>
      </div>
    </div>

    <div class="main-content">
      <div class="left-panel">
        <!-- 问卷调查卡片 -->
        <div class="questionnaire-card">
          <div class="questionnaire-header">
            <h3>思维启发问卷</h3>
            <p>通过回答问题，激发您的创作灵感</p>
          </div>
          
          <div class="questionnaire-content">
            <div class="question-item" v-for="(question, index) in questions" :key="index">
              <div class="question-text">{{ question.text }}</div>
              <el-input
                v-model="question.answer"
                type="textarea"
                :rows="2"
                :placeholder="question.placeholder"
                @input="updateThinking"
              />
            </div>
            
            <el-button 
              type="primary" 
              @click="generateThinking"
              :loading="isGenerating"
              style="width: 100%; margin-top: 16px;"
            >
              生成思维导图
            </el-button>
          </div>
        </div>

        <!-- 思维历史 -->
        <div class="thinking-history">
          <h3>最近的思维导图</h3>
          <div class="history-list">
            <div 
              v-for="item in thinkingHistory" 
              :key="item.id"
              class="history-item"
              @click="loadThinking(item)"
            >
              <div class="history-title">{{ item.title }}</div>
              <div class="history-date">{{ formatDate(item.createdAt) }}</div>
            </div>
          </div>
        </div>
      </div>

      <div class="right-panel">
        <!-- 思维导图画布 -->
        <div class="mindmap-canvas" ref="canvasRef">
          <div v-if="!currentThinking" class="canvas-placeholder">
            <el-icon class="placeholder-icon"><Share /></el-icon>
            <h3>开始创建您的思维导图</h3>
            <p>填写左侧问卷或直接创建新的思维导图</p>
          </div>
          
          <div v-else class="mindmap-content">
            <!-- 这里是思维导图的可视化内容 -->
            <div class="mindmap-node central-node">
              <div class="node-content">{{ currentThinking.centralTopic }}</div>
            </div>
            
            <div 
              v-for="(branch, index) in currentThinking.branches" 
              :key="index"
              class="mindmap-branch"
              :style="getBranchStyle(index)"
            >
              <div class="branch-node">
                <div class="node-content">{{ branch.title }}</div>
                
                <div 
                  v-for="(subNode, subIndex) in branch.subNodes" 
                  :key="subIndex"
                  class="sub-node"
                >
                  {{ subNode }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 工具栏 -->
        <div v-if="currentThinking" class="mindmap-toolbar">
          <el-button-group>
            <el-button @click="zoomIn">
              <el-icon><ZoomIn /></el-icon>
            </el-button>
            <el-button @click="zoomOut">
              <el-icon><ZoomOut /></el-icon>
            </el-button>
            <el-button @click="resetZoom">
              <el-icon><Refresh /></el-icon>
            </el-button>
          </el-button-group>
          
          <el-button-group>
            <el-button @click="saveMindMap">
              <el-icon><Download /></el-icon>
              保存
            </el-button>
            <el-button @click="exportMindMap">
              <el-icon><Share /></el-icon>
              导出
            </el-button>
          </el-button-group>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { 
  Plus, 
  Upload, 
  Share,
  ZoomIn,
  ZoomOut,
  Refresh,
  Download
} from '@element-plus/icons-vue'

// 响应式数据
const isGenerating = ref(false)
const canvasRef = ref()
const currentThinking = ref(null)

// 问卷问题
const questions = reactive([
  {
    text: '您要解决的核心问题是什么？',
    placeholder: '例如：如何提升用户体验...',
    answer: ''
  },
  {
    text: '您的目标受众是谁？',
    placeholder: '例如：年轻消费者、企业客户...',
    answer: ''
  },
  {
    text: '您希望达成什么目标？',
    placeholder: '例如：增加销量、提高效率...',
    answer: ''
  },
  {
    text: '您有哪些资源和约束？',
    placeholder: '例如：预算限制、时间要求...',
    answer: ''
  }
])

// 思维历史记录
const thinkingHistory = reactive([
  {
    id: 1,
    title: '产品营销策略',
    createdAt: '2024-02-12'
  },
  {
    id: 2,
    title: '用户体验改进',
    createdAt: '2024-02-10'
  },
  {
    id: 3,
    title: '技术架构设计',
    createdAt: '2024-02-08'
  }
])

// 示例思维导图数据
const sampleThinking = {
  centralTopic: '产品营销策略',
  branches: [
    {
      title: '目标用户',
      subNodes: ['年轻消费者', '高收入群体', '科技爱好者']
    },
    {
      title: '营销渠道',
      subNodes: ['社交媒体', '内容营销', '线下活动']
    },
    {
      title: '产品特色',
      subNodes: ['创新设计', '高性价比', '用户体验']
    },
    {
      title: '竞争优势',
      subNodes: ['技术领先', '品牌知名度', '渠道覆盖']
    }
  ]
}

// 方法
const createMindMap = () => {
  ElMessage.success('创建新思维导图')
  currentThinking.value = {
    centralTopic: '新思维导图',
    branches: []
  }
}

const importMindMap = () => {
  ElMessage.info('导入思维导图功能开发中...')
}

const updateThinking = () => {
  // 实时更新思维导图预览
  console.log('Updating thinking based on questionnaire')
}

const generateThinking = async () => {
  isGenerating.value = true
  
  try {
    // 模拟AI生成过程
    await new Promise(resolve => setTimeout(resolve, 2000))
    
    // 基于问卷答案生成思维导图
    const answers = questions.map(q => q.answer).filter(a => a.trim())
    if (answers.length === 0) {
      ElMessage.warning('请至少回答一个问题')
      return
    }
    
    currentThinking.value = sampleThinking
    ElMessage.success('思维导图生成成功！')
  } catch (error) {
    ElMessage.error('生成失败，请重试')
  } finally {
    isGenerating.value = false
  }
}

const loadThinking = (item: any) => {
  currentThinking.value = sampleThinking
  ElMessage.info(`加载思维导图: ${item.title}`)
}

const getBranchStyle = (index: number) => {
  const angle = (index * 360) / (currentThinking.value?.branches?.length || 4)
  const radius = 150
  const x = Math.cos(angle * Math.PI / 180) * radius
  const y = Math.sin(angle * Math.PI / 180) * radius
  
  return {
    transform: `translate(${x}px, ${y}px)`
  }
}

const zoomIn = () => {
  ElMessage.info('放大视图')
}

const zoomOut = () => {
  ElMessage.info('缩小视图')
}

const resetZoom = () => {
  ElMessage.info('重置视图')
}

const saveMindMap = () => {
  ElMessage.success('思维导图已保存')
}

const exportMindMap = () => {
  ElMessage.info('导出思维导图功能开发中...')
}

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('zh-CN')
}
</script>

<style scoped lang="scss">
.thinking-container {
  padding: 24px;
  height: calc(100vh - 160px);
  display: flex;
  flex-direction: column;
}

.thinking-header {
  margin-bottom: 24px;
  
  .header-content {
    text-align: center;
    
    h1 {
      font-size: 32px;
      font-weight: 700;
      color: #1a1a1a;
      margin-bottom: 8px;
    }
    
    .description-subtitle {
      font-size: 16px;
      color: #666;
      margin-bottom: 24px;
    }
    
    .action-buttons {
      display: flex;
      gap: 16px;
      justify-content: center;
    }
  }
}

.main-content {
  flex: 1;
  display: flex;
  gap: 24px;
  min-height: 0;
}

.left-panel {
  width: 320px;
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.questionnaire-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  border: 1px solid #f0f0f0;
  
  .questionnaire-header {
    margin-bottom: 20px;
    
    h3 {
      font-size: 18px;
      font-weight: 600;
      color: #1a1a1a;
      margin-bottom: 4px;
    }
    
    p {
      font-size: 14px;
      color: #666;
    }
  }
  
  .question-item {
    margin-bottom: 16px;
    
    .question-text {
      font-size: 14px;
      color: #333;
      margin-bottom: 8px;
      font-weight: 500;
    }
  }
}

.thinking-history {
  flex: 1;
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  border: 1px solid #f0f0f0;
  
  h3 {
    font-size: 16px;
    font-weight: 600;
    color: #1a1a1a;
    margin-bottom: 16px;
  }
  
  .history-list {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }
  
  .history-item {
    padding: 12px;
    border-radius: 8px;
    border: 1px solid #f0f0f0;
    cursor: pointer;
    transition: all 0.2s ease;
    
    &:hover {
      border-color: #3ed3cf;
      background: rgba(62, 211, 207, 0.05);
    }
    
    .history-title {
      font-size: 14px;
      font-weight: 500;
      color: #333;
      margin-bottom: 4px;
    }
    
    .history-date {
      font-size: 12px;
      color: #888;
    }
  }
}

.right-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.mindmap-canvas {
  flex: 1;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  border: 1px solid #f0f0f0;
  position: relative;
  overflow: hidden;
  
  .canvas-placeholder {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    color: #666;
    
    .placeholder-icon {
      font-size: 64px;
      margin-bottom: 16px;
      color: #ddd;
    }
    
    h3 {
      font-size: 18px;
      margin-bottom: 8px;
    }
    
    p {
      font-size: 14px;
    }
  }
  
  .mindmap-content {
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  
  .central-node {
    position: absolute;
    z-index: 10;
    
    .node-content {
      background: linear-gradient(135deg, #3ed3cf 0%, #0ea39f 100%);
      color: white;
      padding: 16px 24px;
      border-radius: 50%;
      font-weight: 600;
      text-align: center;
      min-width: 120px;
      box-shadow: 0 4px 12px rgba(62, 211, 207, 0.3);
    }
  }
  
  .mindmap-branch {
    position: absolute;
    
    .branch-node {
      .node-content {
        background: #f8f9fa;
        color: #333;
        padding: 12px 16px;
        border-radius: 8px;
        border: 2px solid #3ed3cf;
        font-weight: 500;
        margin-bottom: 8px;
      }
      
      .sub-node {
        background: white;
        color: #666;
        padding: 6px 12px;
        border-radius: 4px;
        border: 1px solid #e0e0e0;
        margin-bottom: 4px;
        font-size: 12px;
      }
    }
  }
}

.mindmap-toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  padding: 12px 16px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  border: 1px solid #f0f0f0;
}

@media (max-width: 1024px) {
  .main-content {
    flex-direction: column;
  }
  
  .left-panel {
    width: 100%;
  }
  
  .thinking-header {
    .action-buttons {
      flex-wrap: wrap;
    }
  }
}
</style>