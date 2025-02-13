package com.cos.jwt.Filter;


import jakarta.servlet.*;

import java.io.IOException;

public class MyFilter2 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter 2");
        filterChain.doFilter(request, response);
    }
}
