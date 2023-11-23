
//导入请求工具类
import instance from '@/utils/request.js'
//导入@/stores/token.js
import { useTokenStore } from '@/stores/token.js'


//文章分类列表查询
export const articleCategoryListService = () => {
    
    return instance.get('/category/get')
      
}

//添加文章分类

export const articleCategoryAddService = (categoryModel) => {    
   
    return instance.post('/category/add', categoryModel
    )
}

//修改文章分类
export const articleCategoryUpdateService = (categoryModel)=>{
    return instance.put('/category/update',categoryModel)
}

//删除文章分类

export const articleCategoryDeleteService = (id) => { 
    return instance.delete('/category?id=' + id)
}

//获取文章列表
export const articleListService = (params) => {
    return instance.get('/article', { params })
}

//添加文章
export const articleAddService = (articleModel) => {
    return instance.post('/article', articleModel)
}