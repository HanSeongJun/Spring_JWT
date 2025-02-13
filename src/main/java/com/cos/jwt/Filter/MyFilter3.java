package com.cos.jwt.Filter;


import jakarta.servlet.*;

import java.io.IOException;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter 3");
        filterChain.doFilter(request, response);
    }
}
