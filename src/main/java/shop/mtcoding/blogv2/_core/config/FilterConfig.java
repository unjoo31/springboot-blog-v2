package shop.mtcoding.blogv2._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.blogv2._core.filter.MyFilter1;

@Configuration
public class FilterConfig {

    @Bean //  @Bean : 리턴되는 것을 IoC에 띄운다
    // FilterRegistrationBean으로 해야 DS에 Filter가 띄워진다
    public FilterRegistrationBean<MyFilter1> myFilter1(){
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*"); // 슬래시로 시작하는 모든 주소에 발동됨
        bean.setOrder(0); // 낮은 번호부터 실행됨. 0번에 제일 우선순위임.
        return bean;
    }
}
