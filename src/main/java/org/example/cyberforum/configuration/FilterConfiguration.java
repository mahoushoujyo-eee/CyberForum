package org.example.cyberforum.configuration;

import org.example.cyberforum.filter.LogStateFilter;
import org.example.cyberforum.filter.NewLogStateFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfiguration
{
    public LogStateFilter logStateFilter()
    {
        return new LogStateFilter();
    }

    @Bean
    public FilterRegistrationBean<NewLogStateFilter> logStateFilterRegistration()
    {
        FilterRegistrationBean<NewLogStateFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new NewLogStateFilter());
        registration.addUrlPatterns("/*");
        registration.setName("LogStateFilter");
        registration.setOrder(1);
        return registration;
    }
}
