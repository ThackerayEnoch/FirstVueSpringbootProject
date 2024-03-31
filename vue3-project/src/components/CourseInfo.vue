<template>
  <el-collapse-item class="status" :class="course.isSelected === true ? 's-true' : 's-false'"
                    :title="'('+course.code+') '+course.name+'\xa0\xa0\xa0-\xa0\xa0\xa0'+course.credit+'\xa0\xa0学分'+'\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0\xa0状态:\xa0\xa0'+(course.isSelected === true ? '已选' : '未选')"
  >
    <el-table
        :data="[course]"
        style="width: 100%;margin-top: 2% !important;"
    >
      <el-table-column label="教学班" width="250">
        {{ course.name + index }}
      </el-table-column>
      <el-table-column prop="teacher" label="上课教师" width="180"/>
      <el-table-column prop="classTime" label="上课时间" width="180"/>
      <el-table-column prop="location" label="上课地点" width="220"/>
      <el-table-column prop="nature" label="课程性质" width="180"/>
      <el-table-column label="已选/容量" width="180">
        {{ course.selectedCount + '/' + course.totalCapacity }}
      </el-table-column>
      <el-table-column prop="status" label="操作">
        <el-button :loading="isLoading" :type="course.isSelected===true?'danger':'primary'" @click="courseSelect">
          {{ course.isSelected === true ? '退选' : '选课' }}
        </el-button>
      </el-table-column>
    </el-table>
  </el-collapse-item>
</template>

<script>
import api from "@/api";

export default {
  name: 'CourseInfo',
  props: {
    index: Number,
    course: Object
  },
  data() {
    return {
      isLoading: false
    }
  },
  methods: {
    courseSelect() {
      this.isLoading = true;

      if (this.course.isSelected === true) {
        api.cancelCourse(this.course.id).then((res) => {
          if (res.status === 200) {
            if (res.data !== "courseNotSelected" && res.data !== "fail") {
              this.$emit('update:course', {
                ...this.course,
                isSelected: false,
                selectedCount: this.course.selectedCount - 1
              });
              this.$message.success("退选成功")
            } else {
              this.$message.warning("课程未选中")
            }
          } else {
            this.$message.error("退选失败 请联系网站管理员")
          }
          this.isLoading = false;
        })
      } else {
        api.selectCourse(this.course.id).then((res) => {
          if (res.status === 200) {
            if (res.data !== "courseIsFull" && res.data !== "fail") {
              this.$emit('update:course', {
                ...this.course,
                isSelected: true,
                selectedCount: this.course.selectedCount + 1
              });
              this.$message.success("选课成功")
            } else {
              this.$message.warning("课程已满")
            }
          } else {
            this.$message.error("选课失败 请联系网站管理员")
          }
          this.isLoading = false;
        })

      }
    }
  }
}
</script>

<style scoped>
.status {
  border: 1px solid #DCDFE6;
  padding: 10px;
  margin: 10px;
  border-radius: 5px;
  box-shadow: 0 0 30px #DCDFE6;
}

.s-true {
  background-color: rgb(193, 255, 193) !important;
}

.s-true :deep(.el-collapse-item__wrap),
.s-true :deep(.el-collapse-item__content) {
  background-color: rgb(193, 255, 193) !important;
}

.s-true :deep(.el-collapse-item__header) {
  height: 18px;
  color: black !important; /* 修改标题颜色 */
  background-color: rgb(193, 255, 193) !important; /* 修改标题背景颜色 */
}

.s-false {
  background-color: #D9ECE7 !important;
}

.s-false :deep(.el-collapse-item__wrap),
.s-false :deep(.el-collapse-item__content) {
  background-color: #D9ECE7 !important;
}

.s-false :deep(.el-collapse-item__header) {
  height: 18px;
  color: black !important; /* 修改标题颜色 */
  background-color: #D9ECE7 !important; /* 修改标题背景颜色 */
}

.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>

