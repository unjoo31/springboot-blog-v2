package shop.mtcoding.blogv2._core.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter1 implements Filter {

    // doFilter 메서드는 필터의 핵심적인 로직이 포함되어 있는 메서드. 이 메서드는 요청과 응답을 가로채서 필터링 및 수정 작업을 수행
    // ServletRequest와 ServletResponse는 각각 요청과 응답을 나타냅니다. 
    // FilterChain은 필터 체인을 통해 다음 필터 또는 서블릿으로 제어를 전달하는 역할
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // 1. IP 로그 남기기
        // User-Agent는 클라이언트의 브라우저나 디바이스 정보를 나타내며, 이를 통해 어떤 브라우저나 디바이스에서 요청이 온 것인지 파악할 수 있음.
        System.out.println("접속자 ip : "+req.getRemoteAddr()); 
        System.out.println("접속자 user agent : "+req.getHeader("User-Agent"));

        // 2. 블랙리스트 추방
        // User-Agent 헤더를 확인하여 값에 "Chrome"이 포함되어 있다면, "크롬" 브라우저를 사용한 요청이라고 판단하고 특정 응답을 생성
        if(req.getHeader("User-Agent").contains("XBox")){
            //resp.setContentType("text/html; charset=utf-8");
            resp.setHeader("Content-Type", "text/html; charset=utf-8");
            PrintWriter out = resp.getWriter();

            req.setAttribute("name", "홍길동");
            out.println("<h1>나가세요 크롬이면 : "+req.getAttribute("name")+"</h1>");
            return;
        }
        chain.doFilter(request, response); // 다음 필터로 request, response 전달 ..필터 없으면 DS로 전달
    }

}
