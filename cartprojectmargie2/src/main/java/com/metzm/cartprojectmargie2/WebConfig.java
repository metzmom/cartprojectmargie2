package com.metzm.cartprojectmargie2;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("home");//this is the home page
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
            .addResourceHandler("/media/**")
            .addResourceLocations("file:/C:/Users/richa/Documents/footprint/cartprojectmargie2/src/main/resources/static/media/**");
    }

}
