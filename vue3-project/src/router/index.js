import {createRouter, createWebHashHistory} from 'vue-router'
//路由列表
const routes = [
    {
        path:'/',
        name: 'Main',
        component: ()=>import('../views/MainView')
    }
]
//创建路由
const router = createRouter({
    history: createWebHashHistory(),
    routes
})
export default router;