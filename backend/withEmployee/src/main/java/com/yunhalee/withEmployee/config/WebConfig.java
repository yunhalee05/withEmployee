package com.yunhalee.withEmployee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//            .allowedOrigins("https://withemployee.n-e.kr")
            .allowedOrigins("http://localhost:3000")
            .allowedMethods(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name()

            );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        uploadFolder("profileUploads", registry);
        uploadFolder("messageUploads", registry);
    }

    private void uploadFolder(String dirName, ResourceHandlerRegistry registry) {
        Path photosDir = Paths.get(dirName);
        String photosPath = photosDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/" + dirName + "/**")
            .addResourceLocations("file:" + photosPath + "/");
    }
}
