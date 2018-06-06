import Vue from 'vue'
import Router from 'vue-router'
import FrontPage from '@/components/FrontPage'
import NewRoute from '@/components/NewRoute'
import JoinCar from '@/components/JoinCar'
import ModifyRoute from '@/components/ModifyRoute'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'FrontPage',
      component: FrontPage
    },
    {
      path: '/NewRoute',
      name: 'NewRoute',
      component: NewRoute
    },
    {
      path: '/JoinCar/:carId',
      name: 'JoinCar',
      props: true,
      component: JoinCar
    },
    {
      path: '/ModifyRoute/:carId',
      name: 'ModifyRoute',
      props: true,
      component: ModifyRoute
    }
  ]
})
