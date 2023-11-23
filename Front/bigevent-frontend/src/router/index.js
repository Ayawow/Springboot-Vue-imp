//导入vue-router
import { createRouter, createWebHistory } from 'vue-router'
//导入组件


//定义路由关系
const routes = [
    { path: '/login', component:() => import('@/views/LoginView.vue') },
    {
        path: '/', component:() => import('@/views/LayoutView.vue'),
        //重定向
        redirect: '/article/manage',
        //子路由
        children: [     
            {
                path: '/article/manage',
                component: () => import('@/views/ArticleManage.vue')
        
            },
            {
                path: '/article/category',
                component: () => import('@/views/ArticleCategory.vue')
            },
            {
                path: 'user/info',
                component: () => import('@/views/UserInfo.vue')
            },
            {
                path: 'user/avatar',
                component: () => import('@/views/UserAvatar.vue')
            },
            {
                path: 'user/password',
                component: () => import('@/views/UserResetPassword.vue')
            }

        ]
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
});

export default router
