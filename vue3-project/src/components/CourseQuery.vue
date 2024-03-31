<template>
  <div class="mains">
    <el-input name="abc" class="query-input" placeholder="输入课程号查询 " v-model="QueryCourseId">
      <template #append>
        <el-button style="margin-right: 20px;background-color: #409EFF;border-radius: 0;" @click="search">
          <el-icon color="white">
            <Search/>
          </el-icon>
        </el-button>
        <el-button @click="cancel">
          <el-icon color="black">
            <Close/>
          </el-icon>
        </el-button>
      </template>
    </el-input>
    <div class="availability">有无余量:
      <el-check-tag class="tag" :checked="checked" type="primary" @change="onChange">有</el-check-tag>
      <el-check-tag class="tag" :checked="checked1" type="primary" @change="onChange1">无</el-check-tag>
    </div>
  </div>
</template>
<script>
import {Search} from "@element-plus/icons";

export default {
  name: 'CourseQuery',
  components: {Search},
  emits:['queryCourse'],
  data() {
    return {
      QueryCourseId: '',
      checked: true,
      checked1: true
    }
  },
  methods: {
    onChange() {
      this.checked = !this.checked
    },
    onChange1(){
      this.checked1 = !this.checked1
    },
    search() {
      this.$emit('queryCourse', this.QueryCourseId, this.checked,this.checked1)
    },
    cancel() {
      this.QueryCourseId = '';
      this.checked = this.checked1 = true
    }
  }
}
</script>
<style scoped>
.main {
  margin: 0;
}

.mains {
  position: relative;
  border-radius: 2px;
  height: 200px;
  border: 1px solid rgb(167, 189, 211);
}

.selected {
  margin-left: 0;
  font-size: 14px;
  padding-left: 15%;
  margin-top: 10px;
  background-color: rgb(252, 247, 217);
  border: 1px solid rgb(167, 189, 211);
  height: 30px;
  font-weight: bold;
}

.availability {
  margin-left: 0;
  //position: absolute;
  bottom: 0;
  font-size: 14px;
  font-weight: bold;
  background-color: rgb(252, 247, 217);
  border: 1px solid rgb(167, 189, 211);
  padding-left: 15%;
  height: 30px;
}

.query-input {
  margin-top: 15px;
  margin-left: 20%;
  width: 50%;
}

.tag {
  margin-left: 10px;
  margin-right: 0;
}
</style>