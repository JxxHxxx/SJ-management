package com.jx.management.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Slf4j
public class WebInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        int status = response.getStatus();
//        log.debug("contextPath = {} servletPath = {} status={}", contextPath, servletPath, status);
        if (status == HttpStatus.NOT_FOUND.value()) {
            log.info("contextPath = {} servletPath = {} status={}", contextPath, servletPath, status);
            response.sendRedirect("/manage/saleRecord/statMain.do");
            return false;
        }

        return true;
    }
}
