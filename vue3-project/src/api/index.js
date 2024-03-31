import axios from '../utils/request';
import path from './path';

const api = {
    login(username, password) {
        return axios.post(path.login, {username, password})
    },
    register(username, password, mail, major) {
        return axios.post(path.register, {username, password, mail, major})
    },
    logout() {
        return axios.post(path.logout)
    },
    getUserInfo() {
        return axios.get(path.userinfo)
    },
    getCourse() {
        return axios.get(path.getCourse)
    },
    getSelectedCourse() {
        return axios.get(path.getSelectedCourse)
    },
    selectCourse(courseId) {
        return axios.post(path.selectCourse, {courseId})
    },
    cancelCourse(courseId) {
        return axios.delete(`${path.cancelCourse}/${courseId}`);
    },
    queryCourse(code,flag,flag1) {
        return axios.get(path.queryCourse, {params: {code,flag,flag1}})
    }
}
export default api;