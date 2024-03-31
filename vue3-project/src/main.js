import {createApp} from 'vue'
import App from './App.vue'
import axios from "axios"
import router from './router'
import store from "./store"
import ElementPlus from 'element-plus'
import { ElMessage } from 'element-plus'

import 'element-plus/dist/index.css'
import * as icons from '@element-plus/icons'

// 创建vue实例
const app = createApp(App)
app.config.globalProperties.$axios = axios
app.config.globalProperties.$message = ElMessage
// 全局注册ElementPlus图标
Object.keys(icons).forEach(key => {
    app.component(key, icons[key])
})
// 使用路由
app.use(router).use(store).use(ElementPlus).mount('#app')
