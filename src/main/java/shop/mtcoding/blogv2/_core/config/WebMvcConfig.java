package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration // @Configuration : 설정파일에 붙인다. 
// implements WebMvcConfigurer : 기존 web.xml 파일에 오버라이드 된다
public class WebMvcConfig implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 기존에 하던 일은 그대로 둬야한다
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/images/**") // /images/** 라는 경로의 요청이 들어오면
            .addResourceLocations("file:"+"./images/") // 해당 경로로 가서 찾아라
            .setCachePeriod(10) // 캐시기간 : 10초
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }
}
