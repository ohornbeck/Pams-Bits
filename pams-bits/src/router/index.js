import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView'
import ServicesView from '@/views/ServicesView'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/services',
    name: 'services',
    component: ServicesView
  },
  /*{
    path:'/booking',
    name:'booking',
    component: BookingView
  }*/
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
