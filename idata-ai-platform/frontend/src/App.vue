<template>
  <router-view />
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

onMounted(() => {
  // 初始化用户信息
  if (userStore.token) {
    userStore.getUserInfo()
  }
})
</script>

<style scoped>
#app {
  min-height: 100vh;
}
</style>
