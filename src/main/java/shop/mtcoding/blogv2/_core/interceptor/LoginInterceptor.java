package shop.mtcoding.blogv2._core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

// 인터셉터 생성
public class LoginInterceptor implements HandlerInterceptor {

    // return true이면 컨트롤러 메서드 진입
    // return false이면 요청이 종료됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("LoginInterceptor preHanlde");

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");

        System.out.println("테스트 : " + request.getRequestURI());
        // 요청 URI를 "/" 기준으로 분할하고, 그 중 두 번째 요소를 가져옵니다. 이 부분은 요청의 시작 지점을 추출하는 역할을 합니다.
        String startEndPoint = request.getRequestURI().split("/")[1];
        System.out.println("테스트 : " + startEndPoint);

        if (sessionUser == null) {
            // 시작 지점이 "api"인 경우에 해당하는 조건문
            if (startEndPoint.equals("api")) {
                // HTTP 응답 헤더에 콘텐츠 타입을 JSON 형식으로 설정
                response.setHeader("Content-Type", "application/json; charset=utf-8");
                // 응답 객체로부터 출력 스트림을 가져옵니다.
                PrintWriter out = response.getWriter();
                // ApiUtil 클래스의 객체를 생성합니다. 이 클래스는 JSON 응답을 생성하는 데 사용됩니다.
                ApiUtil<String> apiUtil = new ApiUtil<>(false, "인증이 필요합니다");
                // ObjectMapper를 사용하여 apiUtil 객체를 JSON 문자열로 변환
                String responseBody = new ObjectMapper().writeValueAsString(apiUtil);
                System.out.println("테스트 : "+responseBody);
                //  JSON 응답을 클라이언트로 전송
                out.println(responseBody);
            } else {
                // 시작 지점이 "api"가 아닌 경우에는 HTTP 응답 헤더의 콘텐츠 타입을 HTML 형식으로 설정
                response.setHeader("Content-Type", "text/html; charset=utf-8");
                // 응답 객체로부터 출력 스트림을 가져옵니다.
                PrintWriter out = response.getWriter();
                // 클라이언트로 HTML 응답을 전송
                out.println(Script.href("/loginForm", "인증이 필요합니다"));
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println("LoginInterceptor PostHandle");
    }

}
