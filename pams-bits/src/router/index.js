import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView'
import ServicesView from '@/views/ServicesView'
import BookingView from '@/views/BookingView'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path:'/booking',
    name:'booking',
    component: BookingView
  },
  {
    path: '/services',
    name: 'services',
    component: ServicesView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
