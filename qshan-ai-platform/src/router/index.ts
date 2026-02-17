import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/apps',
      name: 'applications',
      component: () => import('../views/ApplicationsView.vue'),
    },
    {
      path: '/works',
      name: 'works', 
      component: () => import('../views/WorksView.vue'),
    },
    {
      path: '/thinking',
      name: 'thinking',
      component: () => import('../views/ThinkingView.vue'),
    },
    {
      path: '/articles',
      name: 'articles',
      component: () => import('../views/ArticlesView.vue'),
    },
    {
      path: '/profile',
      name: 'profile',
      component: () => import('../views/ProfileView.vue'),
    },
  ],
})

export default router
