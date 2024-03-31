<template>
  <el-descriptions
      class="margin-top"
      :title="title"
      :column="3"
      size="small"
      border
  >
    <template #extra>
      <el-button type="danger" :loading="isLoading" @click="delCourse">退选</el-button>
    </template>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          课程代码
        </div>
      </template>
      {{ course.code }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          教师
        </div>
      </template>
      {{ course.teacher }}
    </el-descriptions-item>
     <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          学分
        </div>
      </template>
      {{ course.credit }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          上课时间
        </div>
      </template>
      {{ course.classTime }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          教室
        </div>
      </template>
      {{ course.location }}
    </el-descriptions-item>
    <el-descriptions-item>
      <template #label>
        <div class="cell-item">
          课程类型
        </div>
      </template>
      {{ course.nature }}
    </el-descriptions-item>
  </el-descriptions>
</template>
<script>
import api from "@/api";

export default {
  name: 'SelectedCourseInfo',
  props: {
    index: Number,
    course: Object
  },
  emits: ['delCourse'],
  data() {
    return {
      title: '课程名: ' + this.course.name,
      isLoading: false
    }
  },
  methods: {
    delCourse() {
      this.isLoading = true;
      api.cancelCourse(this.course.id).then((res) => {
        if (res.status === 200) {
          if (res.data !== "courseNotSelected" && res.data !== "fail") {
            this.$message.success("退选成功")
            this.$emit('delCourse', this.course.id)
            this.isLoading = false;
          } else {
            this.$message.warning("课程未选中")
          }
        } else {
          this.$message.error("退选失败 请联系网站管理员")
        }
        this.isLoading = false;
      }, () => {
        this.$message.error("退选失败 请联系网站管理员")
        this.isLoading = false;
      })
    }
  }
}
</script>
<style scoped>
.el-descriptions {
  margin-top: 20px;
}

.cell-item {
  display: flex;
  align-items: center;
}

.margin-top {
  margin-top: 20px;
}
</style>