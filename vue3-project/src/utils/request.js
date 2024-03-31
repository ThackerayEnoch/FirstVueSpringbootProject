import axios from "axios"

import qs from "qs";
import router from "@/router";



// 创建axios实例
const instance = axios.create({
    timeout: 5000
});
axios.defaults.withCredentials = true;
// 添加请求拦截器
instance.interceptors.request.use(config => {
    // 在发送请求之前做些什么
    // post请求时，将json数据转换为字符串
    if (config.method === "post") {
        config.data = qs.stringify(config.data);
    }
    return config;
}, error => {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
// 添加响应拦截器
instance.interceptors.response.use(response => {
    // 对响应数据做点什么
    // 如果http状态码不等于200，抛出错误
    if (response.status !== 200) {
        console.log('HTTP status is not 200');
        return Promise.reject(response);
    }
    console.log("abcd")
    return response;
}, error => {
    if (error.response) {
        if (error.response.status === 401) {
            router.push({name: 'Login'})
        } else {
            console.log('HTTP status:', error.response.status);
        }
    } else {
        console.log('Error:', error.message);
    }
    // 对响应错误做点什么
    return Promise.reject(error);
});
export default instance;
