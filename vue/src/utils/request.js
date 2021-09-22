import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '../store'

 const service = axios.create({
  baseURL: process.env.BASE_API,    timeout: 5000  })

              
 service.interceptors.response.use(
  response => {
     
    const res = response.data
    if (res.code !== 1) {
      Message({
        message: res.msg,
        type: 'error',
        duration: 5 * 1000
      })
      if (res.code === 40001) {
                 MessageBox.confirm(
          '你已被登出，可以取消继续留在该页面，或者重新登录',
          '确定登出',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('FedLogOut').then(() => {
            location.reload()            })
        })
      } else if (res.code === 403) {
                 MessageBox.confirm(
          '当前登录账号权限不足，是否重新登录',
          '确定登出',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('LogOut').then(() => {
            location.reload()            })
        })
      }
      return Promise.reject('error')
    } else {
      return res
    }
  },
  error => {
    console.log('err' + error)      Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
