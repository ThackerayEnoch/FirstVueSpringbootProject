<template>
  <el-drawer v-model="drawer" title="已选课程" :with-header="true" :before-close="close">
    <div style="color: red" v-if="flag"><h3>未选课</h3></div>
    <SelectedCourseInfo v-else v-for="(item,index) in courses" :key="index" :course="item" @delCourse="delCourse"/>
  </el-drawer>
</template>
<script>
import api from "@/api";
import SelectedCourseInfo from "@/components/SelectedCourseInfo.vue";

export default {
  name: 'SelectedCourseDrawer',
  components: {SelectedCourseInfo},
  props: {
    openFlag: Boolean
  },
  emits: ['closeDrawer'],
  data() {
    return {
      drawer: this.openFlag,
      flag: false,
      courses: []
    }
  },
  watch: {
    openFlag(newVal) {
      this.drawer = newVal;
      if (newVal) {
        this.getCourse();
      }
    }
  },
  mounted() {
    this.getCourse();
  },
  methods: {
    close() {
      this.$emit('closeDrawer')
    },
    getCourse() {
      api.getSelectedCourse().then((res) => {
        if (res.status === 200) {
          this.courses = res.data;
          this.flag = this.courses.length === 0
        }
      }, (error) => {
        if (error.response&&error.response.status !== 401) {
          this.$message.error("获取已选课程失败")
        }
      })
    },
    delCourse(courseId) {
      const index = this.courses.findIndex(course => course.id === courseId);
      if (index !== -1) {
        this.courses.splice(index, 1);
      }
    }
  }
}
</script>
<style scoped>

</style>