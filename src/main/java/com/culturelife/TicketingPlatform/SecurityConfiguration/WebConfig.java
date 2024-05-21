package com.culturelife.TicketingPlatform.SecurityConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/medias/**")
                .addResourceLocations("classpath:/medias/")
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {

                    protected MediaType getMediaType(Resource resource) {
                        if (resource.getFilename().endsWith(".png")) {
                            return MediaType.IMAGE_PNG;
                        } else {
                            return MediaType.APPLICATION_JSON; // 기본적으로 JSON으로 설정
                        }
                    }
                });
    }
}