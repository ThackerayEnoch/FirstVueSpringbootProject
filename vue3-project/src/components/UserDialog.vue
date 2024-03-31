<template>
  <el-dialog
      v-model="localDialogFlag"
      :close-on-click-modal="false"
      :before-close="logdialogclose"
      destroy-on-close
      :title="title"
      width="450"
      align-center
  >
    <div v-if="loginFlag">
      <el-form label-position="top" ref='form' :model='loginForm' label-width="auto" class="login-box">
        <el-form-item label="用户名">
          <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <div class="centered-text">{{ mes }}</div>
        <el-form-item>
          <el-button  :loading="isLoading" type="primary" @click="login" :disabled="isButtonDisabled">登录</el-button>
          <el-button :loading="isLoading" type="warning" @click="registerViewShow" :disabled="isButtonDisabled">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-else id="register">
      <el-form label-position="top" ref='registerForm' :model='registerForm' label-width="auto" class="register-box">
        <el-form-item label="用户名">
          <el-input type="text" v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="registerForm.password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input type="password" v-model="registerForm.confirmPassword" placeholder="请再次输入密码"
                    show-password></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input type="text" v-model="registerForm.mail" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input type="text" v-model="registerForm.major" placeholder="请输入所学专业"></el-input>
        </el-form-item>
        <div style="color: red;">{{ mes }}</div>
        <el-form-item>
          <el-button :loading="isLoading" type="primary" @click="Register" :disabled="isButtonDisabled">注册</el-button>
          <el-button :loading="isLoading" type="warning" @click="loginViewShow" :disabled="isButtonDisabled">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>
<script>

import api from "@/api";

export default {
  computed: {},
  watch: {
    dialogflag(newVal) {
      this.localDialogFlag = newVal;
      this.loginFlag = true;
      this.title = '登录 ';
      this.mes = ''
    }
  },
  name: 'UserDialog',
  props: {
    dialogflag: Boolean
  },
  emits: ['closedia'],
  methods: {
    logdialogclose() {
      this.$emit('closedia')
    },
    registerViewShow() {
      this.loginFlag = false;
      this.title = '注册'
      this.mes = ''
    },
    loginViewShow() {
      this.loginFlag = true;
      this.title = '登录'
      this.mes = ''
    },
    login() {
      this.msg = ''
      this.isLoading=true;
      setTimeout(() => {
        api.login(this.loginForm.username, this.loginForm.password).then((res) => {
          if (res.data === 200) {
            this.$emit('closedia')
            location.reload()
          } else {
            if (res.data === 300) {
              this.mes = "用户名不存在"
            } else this.mes = "密码错误"
          }
          this.isLoading=false;
        }, () => {
          this.$message.error("服务器异常")
          this.isLoading=false;
        })
      }, 500)
    },
    Register() {
      this.isLoading=true;
      if (this.registerForm.password !== this.registerForm.confirmPassword) {
        this.mes = "两次密码不一致"
        this.isLoading=false;
        return
      }
      api.register(this.registerForm.username, this.registerForm.password, this.registerForm.mail, this.registerForm.major).then((res) => {
        if (res.data === 200) {
          this.$message.success("注册成功")
          this.$emit('closedia')
          location.reload()
        } else {
          switch (res.data) {
            case 300:
              this.mes = "用户名或密码或邮箱不能为空"
              break
            case 301:
              this.mes = "用户名只能包含字母数字下划线"
              break
            case 302:
              this.mes = "用户名已存在"
              break
            default:
              this.mes = "未知错误 注册失败"
          }
        }
        this.isLoading=false;
      }, () => {
        this.$message.error("服务器异常")
        this.isLoading=false;
      })
    }
  },
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        mail: '',
        major: ''
      },
      localDialogFlag: this.dialogflag,
      loginFlag: true,
      title: '登录 ',
      mes: '',
      isLoading:false,
    }
  }
}
</script>
<style scoped>
.centered-text {
  margin: 0 0 5% 0;
  color: red;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  height: 100%; /* 你可能需要根据实际情况调整这个值 */
}
</style>