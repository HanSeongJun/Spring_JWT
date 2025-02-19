package com.cos.jwt.Config;

import com.cos.jwt.Filter.MyFilter1;
import com.cos.jwt.Filter.MyFilter2;
import com.cos.jwt.Filter.MyFilter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter1> filter1() {

        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");
        bean.setOrder(1); // 낮은 벊오가 필터중에서 가장 먼저 실행됨
        return bean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> filter2() {

        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); // 낮은 벊오가 필터중에서 가장 먼저 실행됨
        return bean;
    }

//    @Bean
//    public FilterRegistrationBean<MyFilter3> filter3() {
//
//        FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<>(new MyFilter3());
//        bean.addUrlPatterns("/*");
//        bean.setOrder(0); // 낮은 벊오가 필터중에서 가장 먼저 실행됨
//        return bean;
//    }
}
