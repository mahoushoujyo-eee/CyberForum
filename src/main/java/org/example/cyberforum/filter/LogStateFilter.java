package org.example.cyberforum.filter;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "LogStateFilter")
public class LogStateFilter implements Filter
{
    private static final Logger logger = LoggerFactory.getLogger(LogStateFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies)
            if (cookie.getName().equals("username"))
            {
                filterChain.doFilter(servletRequest, servletResponse);
                logger.info("There is cookie");
            }

        logger.info("There is no cookie");

    }

    @Override
    public void destroy()
    {
        Filter.super.destroy();
    }
}
