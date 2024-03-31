package com.dokidokiujn.sprinngboot.controller.course;

import com.dokidokiujn.sprinngboot.entity.Course;
import com.dokidokiujn.sprinngboot.entity.User;
import com.dokidokiujn.sprinngboot.mapper.CourseMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {

    @Autowired
    private CourseMapper courseMapper;
    @Operation(
            summary = "获取课程列表",
            description = "获取数据库中所有的课程列表",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "获取成功",
                            content = {
                                    @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                                    name = "course",
                                                    type = "object",
                                                    description = "课程",
                                                    implementation = Course.class
                                            ))}),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "未登录")
            }
    )
    @GetMapping("/course/getCourse")
    public List<Course> getCourse(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Course> courses = null;
        if (user != null) {
            courses = courseMapper.queryCourseList(user.getId());
        }
        return courses;
    }
    @Operation(
            summary = "获取已选课程列表",
            description = """
                    获取用户已选的课程列表\n
                    不需要传入任何参数 请求会自动发送cookie\n
                    服务器通过session获取用户信息\n
                    """,
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "获取成功",
                            content = {
                                    @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                                    name = "course",
                                                    type = "object",
                                                    description = "课程",
                                                    implementation = Course.class
                                            ))}),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "未登录")
            }
    )
    @GetMapping("/course/getSelectedCourse")
    public List<Course> getSelectedCourse(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Course> courses = null;
        if (user != null) {
            courses = courseMapper.queryCourseListWithStudentSelection(user.getId());
        }
        return courses;
    }
    @Operation(
            summary = "查询课程",
            description = """
                    查询课程\n
                    可以通过课程代码查询课程\n
                    可以通过flag查询已选课程或未选课程\n
                    flag为true查询已选课程\n
                    flag1为true查询未选课程\n
                    """,
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "查询成功",
                            content = {
                                    @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                                    name = "course",
                                                    type = "object",
                                                    description = "课程",
                                                    implementation = Course.class
                                            ))}),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "未登录")
            }
    )
    @GetMapping("/course/queryCourse")
    public List<Course> queryCourse(String code, Boolean flag, Boolean flag1, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Course> courses = null;
        if (user != null) {
            if (code != null && code.isEmpty()) {
                code = null;
            }
            if (flag && flag1) {
                flag = flag1 = false;
            }
            courses = courseMapper.queryCourseWithCapacity(user.getId(), code, flag, flag1);
        }
        return courses;
    }
    @Operation(
            summary = "选课",
            description = """
                    选课\n
                    通过课程id选课\n
                    选课成功返回success\n
                    选课失败返回fail\n
                    课程已满返回courseIsFull\n
                    已选课程返回courseIsSelected\n
                    """,
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "根据返回信息确定",
                            content = {
                                    @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                                    name = "status",
                                                    type = "string",
                                                    description = "状态码",
                                                    example = "success"
                                            )
                                    )
                            }
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "未登录")
            }
    )
    @PostMapping("/course/selectCourse")
    public String selectCourse(int courseId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (!courseMapper.checkCapacity(courseId)) {
                return "courseIsFull";
            }
            if (courseMapper.checkSelected(user.getId(), courseId)) {
                return "courseIsSelected";
            }
            courseMapper.courseSelect(user.getId(), courseId);
            courseMapper.updateCount(courseId, 1);
            return "success";
        }
        return "fail";
    }
    @Operation(
            summary = "退课",
            description = """
                    退课\n
                    通过课程id退课\n
                    退课成功返回success\n
                    退课失败返回fail\n
                    未选课程返回courseNotSelected\n
                    """,
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "根据返回信息确定",
                            content = {
                                    @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(
                                                    name = "status",
                                                    type = "string",
                                                    description = "状态码",
                                                    example = "success"
                                            )
                                    )
                            }
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "未登录")
            }
    )
    @DeleteMapping("/course/cancelCourse/{courseId}")
    public String cancelCourse(@PathVariable Integer courseId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (!courseMapper.checkSelected(user.getId(), courseId)) {
                return "courseNotSelected";
            }
            courseMapper.courseCancel(user.getId(), courseId);
            courseMapper.updateCount(courseId, -1);
            return "success";
        }
        return "fail";
    }
}
