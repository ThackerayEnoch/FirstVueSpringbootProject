package com.dokidokiujn.sprinngboot.interceptor;

import com.dokidokiujn.sprinngboot.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("拦截");
            response.setStatus(401);
            return false;
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println("拦截");
            response.setStatus(401);
            return false;
        }
        System.out.println("放行");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("postHandle...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("afterCompletion...");
    }

}
