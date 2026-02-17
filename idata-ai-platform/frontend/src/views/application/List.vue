<template>
  <div class="applications-container">
    <div class="filter-section">
      <el-select v-model="selectedCategory" placeholder="选择分类" clearable>
        <el-option label="全部" :value="null" />
        <el-option label="文案创作" value="文案创作" />
        <el-option label="图片生成" value="图片生成" />
      </el-select>

      <el-input v-model="searchKeyword" placeholder="搜索应用" clearable>
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
    </div>

    <div class="app-grid">
      <el-skeleton v-for="n in 12" :key="n" :rows="3" animated />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'

const selectedCategory = ref<string | null>(null)
const searchKeyword = ref('')
</script>

<style scoped lang="scss">
.filter-section {
  display: flex;
  gap: 16px;
  margin-bottom: 24px;

  .el-select {
    width: 200px;
  }

  .el-input {
    flex: 1;
  }
}

.app-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
}
</style>
