<script setup>
import { Edit, Delete } from "@element-plus/icons-vue";
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";

import {
  articleCategoryListService,
  articleCategoryAddService,
  articleCategoryUpdateService,
  articleCategoryDeleteService,
} from "@/api/article.js";

const categorys = ref([]);

const getAllCategory = async () => {
  let result = await articleCategoryListService();
  categorys.value = result.data;
};
getAllCategory();

//控制弹窗显示
const dialogVisible = ref(false);
//添加分类数据模型
const categoryModel = ref({
  categoryName: "",
  categoryAlias: "",
});

//添加分类表单验证
const rules = {
  categoryName: [
    { required: true, message: "请输入分类名称", trigger: "blur" },
  ],
  categoryAlias: [
    { required: true, message: "请输入分类别名", trigger: "blur" },
  ],
};

const addCategory = async () => {
  let result = await articleCategoryAddService(categoryModel.value);
  ElMessage.success(result.message ? result.message : "添加成功");
  //隐藏弹窗
  dialogVisible.value = false;
  //再次访问后台接口，查询所有分类
  getAllCategory();
};

const title = ref("");

//展示编辑分类弹窗
const showEditCategory = (row) => {
  dialogVisible.value = true;
  title.value = "修改分类";
  categoryModel.value.id = row.id;
  categoryModel.value.categoryName = row.categoryName;
  categoryModel.value.categoryAlias = row.categoryAlias;
};

//修改分类回显
const updateCategory = async () => {
  let result = await articleCategoryUpdateService(categoryModel.value);
  ElMessage.success(result.message ? result.message : "修改成功");
  //隐藏弹窗
  dialogVisible.value = false;
  //再次访问后台接口，查询所有分类
  getAllCategory();
};

//清空模型数据
const clearCategoryModel = () => {
  (categoryModel.value.categoryName = ""),
    (categoryModel.value.categoryAlias = "");
};

//删除分类  给删除按钮绑定事件
const deleteCategory = (row) => {
  ElMessageBox.confirm("你确认删除该分类信息吗？", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      //用户点击了确认
      let result = await articleCategoryDeleteService(row.id);
      ElMessage.success(result.message ? result.message : "删除成功");
      //再次调用getAllCategory，获取所有文章分类
      getAllCategory();
    })
    .catch(() => {
      //用户点击了取消
      ElMessage({
        type: "info",
        message: "取消删除",
      });
    });
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>文章分类</span>
        <div class="extra">
          <el-button
            type="primary"
            @click="
              dialogVisible = true;
              title = '添加分类';
              clearCategoryModel();
            "
            >添加分类</el-button
          >
        </div>
      </div>
    </template>
    <el-table style="width: 100%" :data="categorys">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="分类名称" prop="categoryName"></el-table-column>
      <el-table-column label="分类别名" prop="categoryAlias"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="showEditCategory(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteCategory(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>
    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form
        :model="categoryModel"
        :rules="rules"
        label-width="100px"
        style="padding-right: 30px"
      >
        <el-form-item label="分类名称" prop="categoryName">
          <el-input
            v-model="categoryModel.categoryName"
            minlength="1"
            maxlength="10"
          ></el-input>
        </el-form-item>
        <el-form-item label="分类别名" prop="categoryAlias">
          <el-input
            v-model="categoryModel.categoryAlias"
            minlength="1"
            maxlength="15"
          ></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button
            type="primary"
            @click="title === '添加分类' ? addCategory() : updateCategory()"
          >
            确认
          </el-button>
        </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>
