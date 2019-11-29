package com.tansha.library.bookshelf.login.configurations;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/bookimages/**").addResourceLocations("d:/bookimages/");
    }

}
