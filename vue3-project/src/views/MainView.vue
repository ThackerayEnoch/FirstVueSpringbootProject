<template>
  <div class="common-layout">
    <el-container>
      <el-header class="top-nav custom-nav">
        <el-menu default-active="1"
                 class="custom-nav"
                 mode="horizontal"
                 :ellipsis="false">
          <el-menu-item class="nav-text">自主选课系统</el-menu-item>
          <div class="flex-grow"/>
          <el-dropdown class="nav-user">
        <span class="el-dropdown-link">
          {{ username }}<el-icon class="el-icon--right"><arrow-down/></el-icon>
        </span>
            <template #dropdown>
              <el-dropdown-menu v-if="!isLoggedFlag">
                <el-dropdown-item @click="showLogdialog">
                  <el-icon>
                    <UserFilled/>
                  </el-icon>
                  登录
                </el-dropdown-item>
              </el-dropdown-menu>
              <el-dropdown-menu v-else>
                <el-dropdown-item @click="selectedCoursePage">
                  <el-icon>
                    <Collection/>
                  </el-icon>
                  已选课程
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-icon>
                    <Setting/>
                  </el-icon>
                  设置
                </el-dropdown-item>
                <el-dropdown-item style="color: red;" @click="logout">
                  <el-icon>
                    <SwitchButton/>
                  </el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-menu>
      </el-header>
      <el-main class="main">
        <CourseQuery @queryCourse="queryCourse"/>
        <div class="course-result">
          <h3 v-if="!isLoggedFlag" style="text-align: center; color: crimson">未登录</h3>
          <el-collapse v-else>
            <CourseInfo v-for="(item,index) in courses" :key="index" :course="item" :index="index"
                        @update:course="updateCourse"/>
          </el-collapse>
        </div>
        <UserDialog :dialogflag="logvisible" @closedia="closedia"/>
        <SelectedCourseDrawer :open-flag="openFlag" @closeDrawer="closeDrawer"/>
      </el-main>
    </el-container>
  </div>
</template>
<script>

import {} from '@element-plus/icons'
import CourseQuery from "@/components/CourseQuery.vue";
import CourseInfo from "@/components/CourseInfo.vue";
import UserDialog from "@/components/UserDialog.vue";
import api from "@/api";
import SelectedCourseDrawer from "@/components/SelectedCourseDrawer.vue";


export default {
  name: 'MainView',
  computed: {},
  mounted() {
    this.checkLogin();
  },
  methods: {
    showLogdialog()  //点击按钮，显示dialog
    {
      this.logvisible = true;
    },
    closedia() {
      this.logvisible = false;
    },
    closeDrawer() {
      this.openFlag = false;
      this.showCourse();
    },
    updateCourse(updatedCourse) {
      // Find the index of the course to update
      const index = this.courses.findIndex(course => course.id === updatedCourse.id);
      // Replace the course at the found index with the updated course
      this.courses[index] = updatedCourse;
    },
    checkLogin() {
      api.getUserInfo().then((res) => {
        if (res.status === 200) {
          this.username = res.data.username;
          this.isLoggedFlag = true;
          this.showCourse();
        } else {
          this.username = '未登录';
          this.isLoggedFlag = false;
        }
      }, () => {
        this.username = '未登录';
        this.isLoggedFlag = false;
      })
    },
    logout() {
      api.logout().then(() => {
        this.$message.success("登出成功")
      }, () => {
        this.$message.error("未知错误 登出失败")
      })
      location.reload()
    },
    selectedCoursePage() {
      this.openFlag = true
    },
    showCourse() {
      api.getCourse().then((res) => {
        if (res.status === 200) {
          this.courses = res.data;
        }
      }, () => {
        this.$message.error('获取课程信息失败')
      })
    },
    queryCourse(courseCode, flag, flag1) {
      api.queryCourse(courseCode, flag, flag1).then((res) => {
        if (res.status === 200) {
          this.courses = res.data;
          this.$message.success("查询成功 ")
        }
      }, () => {
        this.$message.error('查询失败')
      })
    }
  },

  components: {SelectedCourseDrawer, UserDialog, CourseInfo, CourseQuery},
  data() {
    return {
      isLoggedFlag: false,
      logvisible: false,
      openFlag: false,
      username: '',
      courses: []
    }
  }
}
</script>
<style scoped>
.common-layout {
  background-color: whitesmoke;
  width: 100%;
  height: 100%;
}

.main {
  margin-top: 60px;
  margin-left: 1%;
  margin-right: 1%;
  background-color: white;
  padding: 20px;
  border: 1px solid rgb(167, 189, 211);
}

.custom-nav {
  background-color: white;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 48px;
  z-index: 1000;
}

.nav-text {
  color: #409EFF;
  font-size: 20px;
  font-weight: bold;
  margin-left: 20px;
  line-height: 48px;
}

.nav-user {
  margin-right: 20px;
  line-height: 48px;
}

.course-result {
  margin-top: 20px;
  border: 1px solid rgb(167, 189, 211);
}

.flex-grow {
  flex-grow: 1;
}

.block-col-2 .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}

.block-col-2 .el-dropdown-link {
  display: flex;
  align-items: center;
}

</style>