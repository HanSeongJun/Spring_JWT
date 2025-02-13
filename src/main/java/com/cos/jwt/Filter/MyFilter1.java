package com.cos.jwt.Filter;


import jakarta.servlet.*;

import java.io.IOException;

public class MyFilter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter 1");
        filterChain.doFilter(request, response);
    }
}
