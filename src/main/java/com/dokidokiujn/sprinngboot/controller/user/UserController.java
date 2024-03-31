package com.dokidokiujn.sprinngboot.controller.user;

import com.dokidokiujn.sprinngboot.entity.User;
import com.dokidokiujn.sprinngboot.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController", description = "用户控制器接口")
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Operation(
            summary = "登录接口",
            description = """
                    用户登录接口\n
                    需传入用户名和密码\n
                    后端完成用户名转小写操作\n
                    密码采用哈希加盐SHA256加密(暂时不可用后面再补上 先用原始密码代替)\n
                    返回session-id(由浏览器自动处理 不需要前端人工处理)\n
                    返回值为INT整形""",
            parameters = {
                    @Parameter(name = "username", description = "用户名", required = true, example = "123456"),
                    @Parameter(name = "password", description = "密码", required = true, example = "123456")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "登录成功",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    name = "username",
                                                    type = "string",
                                                    description = "用户名",
                                                    implementation = User.class
                                            ))}),
                    @ApiResponse(responseCode = "300", description = "用户不存在"),
                    @ApiResponse(responseCode = "301", description = "密码错误")
            }

    )
    @PostMapping("/user/login")
    public Integer UserLogin(String username, String password, HttpServletResponse response, HttpServletRequest request) {
        // 需传入SHA256加密后的密码
        // 300 用户不存在 301 密码错误 200 登录成功
        // 返回session
        username = username.toLowerCase();
        User userByName = userMapper.queryUserByName(username);
        if (userByName == null) {
            return 300;
        }
        if (!userByName.getPassword().equals(password)) {
            return 301;
        }
        request.getSession().setAttribute("user", userByName);
        return 200;
    }

    @Operation(
            summary = "登出接口",
            description = """
                    用户登出接口\n
                    登出会删除服务器端对应session\n
                    cookie中需要有有效session-id(已登录)\n
                    否则返回401(未认证)\n
                    返回值为INT整形
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "登出成功",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    name = "status",
                                                    type = "integer",
                                                    description = "状态码",
                                                    example = "200"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "401", description = "未认证")
            }
    )
    @PostMapping("/user/logout")
    public Integer userLogout(HttpSession session) {
        session.removeAttribute("user");
        return 200;
    }

    @Operation(
            summary = "获取用户信息接口",
            description = """
                    获取用户信息接口\n
                    cookie中需要有有效session-id(已登录)\n
                    否则返回401(未认证)\n
                    返回值为用户信息的JSON字符串
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "获取成功"),
                    @ApiResponse(responseCode = "401", description = "未登录")
            }
    )
    @GetMapping("/user/info")
    public User userInfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        User u = userMapper.queryUserById(user.getId());
        return u;
    }

    @Operation(
            summary = "注册接口",
            description = """
                    用户注册接口\n
                    需传入用户名、密码、邮箱、学院\n
                    id不需要传入由数据库完成自增\n
                    后端已完成用户名转小写操作\n
                    后端已完成用户名和密码 不能为空 只能包含字母数字下划线的校验\n
                    返回值为INT整形
                    """,
            parameters = {
                    @Parameter(name = "username", description = "用户名", required = true, example = "BLOONS"),
                    @Parameter(name = "password", description = "密码", required = true, example = "TD6"),
                    @Parameter(name = "mail", description = "邮箱", required = true, example = "mail@example.com"),
                    @Parameter(name="major",description = "专业",required = true,example = "计算机科学与技术")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "注册成功",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(
                                                    name = "status",
                                                    type = "string",
                                                    description = "状态码",
                                                    example = "注册成功"
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "300", description = "用户名或密码或邮箱不能为空"),
                    @ApiResponse(responseCode = "301", description = "用户名只能包含字母数字下划线"),
                    @ApiResponse(responseCode = "302", description = "用户名已存在"),
                    @ApiResponse(responseCode = "303", description = "未知错误 注册失败")
            }

    )
    @PostMapping("/user/register")
    public Integer URegister(String username, String password, String mail,String major) {
        if (username.isEmpty() || password.isEmpty() || mail.isEmpty()||major.isEmpty()) {
            return 300;
        }
        String regex = "^[a-zA-Z0-9_]+$";
        if (!username.matches(regex)) {
            return 301;
        }
        if (userMapper.userExists(username)) {
            return 302;
        }
        username = username.toLowerCase();
        User user = new User(username, password, mail,major);
        int res = userMapper.addUser(user);
        if (res > 0) {
            return 200;
        } else {
            return 303;
        }
    }
}
