import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'  import 'nprogress/nprogress.css' import { getToken } from '@/utils/auth'  
const whiteList = ['/login']  router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()      } else {
      if (!store.getters.user) {
                 store.dispatch('GetUserInfo').then().catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || '验证失败，请重新登录')
            next({ path: '/' })
          })
        })
                                                                     }
      next()
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)        NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()  })
