package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import shop.mtcoding.blogv2._core.interceptor.LoginInterceptor;

@Configuration // @Configuration : 설정파일에 붙인다. 
// implements WebMvcConfigurer : 기존 web.xml 파일에 오버라이드 된다
public class WebMvcConfig implements WebMvcConfigurer{

    // 정적 리소스의 핸들링을 구성하기 위한 메서드 오버라이드입니다. 
    // 이 메서드를 통해 정적 리소스의 요청 경로와 실제 리소스 파일이 위치한 경로, 캐시 설정 등을 정의할 수 있습니다.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 기존에 하던 일은 그대로 둬야한다
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/images/**") // /images/** 라는 경로의 요청이 들어오면
            .addResourceLocations("file:"+"./images/") // ./images/ 해당 경로로 가서 찾아라
            .setCachePeriod(10) // 캐시기간 : 10초
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()) // interceptor 추가
                .addPathPatterns("/api/**") // 발동 조건
                .addPathPatterns("/user/update", "/user/updateForm") // 발동 조건
                .addPathPatterns("/board/**") // 발동 조건
                .excludePathPatterns("/board/{id:[0-9]+}"); // 발동 제외
    }

    
}
