import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/auth/Login.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/',
      component: () => import('@/layouts/DefaultLayout.vue'),
      children: [
        {
          path: '',
          name: 'Home',
          component: () => import('@/views/home/Index.vue')
        },
        {
          path: 'applications',
          name: 'Applications',
          component: () => import('@/views/application/List.vue')
        },
        {
          path: 'instances',
          name: 'Instances',
          component: () => import('@/views/instance/List.vue')
        },
        {
          path: 'thinking',
          name: 'Thinking',
          component: () => import('@/views/thinking/Index.vue')
        },
        {
          path: 'articles',
          name: 'Articles',
          component: () => import('@/views/article/List.vue')
        },
        {
          path: 'profile',
          name: 'Profile',
          component: () => import('@/views/user/Profile.vue')
        }
      ]
    }
  ]
})

// 路由守卫（如需登录校验，取消注释即可）
/*
router.beforeEach((to, _from, next) => {
  const userStore = useUserStore()
  if (to.meta.requiresAuth !== false && !userStore.token) {
    next('/login')
  } else {
    next()
  }
})
*/

// 开发环境临时放开登录校验
router.beforeEach((_to, _from, next) => {
  next()
})

export default router
