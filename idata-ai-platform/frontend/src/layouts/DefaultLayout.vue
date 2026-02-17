<template>
  <el-container class="layout-container">
    <el-header class="layout-header">
      <LayoutHeader />
    </el-header>
    <el-main class="layout-main">
      <router-view />
    </el-main>
    <el-footer class="layout-footer">
      <LayoutFooter />
    </el-footer>
  </el-container>
</template>

<script setup lang="ts">
import LayoutHeader from './Header.vue'
import LayoutFooter from './Footer.vue'
</script>

<style scoped lang="scss">
.layout-container {
  min-height: 100vh;
  background: #f5f7fa;
}

.layout-header {
  padding: 0;
  height: 64px;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.layout-main {
  padding: 24px;
  min-height: calc(100vh - 64px - 80px);
}

.layout-footer {
  padding: 0;
  height: 80px;
  background: #fff;
}
</style>
