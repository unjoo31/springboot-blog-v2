package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

@Controller
public class UserController {
    // Controller는 Service를 의존한다

    @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    // 브라우저 get /logout 요청을 함 (request)
    // 서버는 /주소를 응답의 헤더에 담음 (location), 상태코드 302
    // 브라우저는 get / 로 재요청을 한 (request 2)
    // index 페이지 응답받고 렌더링함
    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO){
        userService.회원가입(joinDTO);
        return "user/loginForm"; // persist 초기화 = clear()
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    // @ResponseBody : 데이터를 응답받기 위해서 붙임
    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        if (sessionUser == null) {
            return Script.back("로그인 실패");
        }
        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/");
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request){
        User sessionUser = (User)session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO){
        // 1. 회원수정(서비스)
        // 2. 세션 동기화

        // 세션을 가지고 온다
        User sessionUser = (User)session.getAttribute("sessionUser");
        // 회원 수정이 된 객체를 받는다
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        // 수정이 된 객체로 동기화 시켜준다 (로그인했을 때와 회원수정 후 비밀번호가 달라지기 때문에 동기화가 필요하다)
        session.setAttribute("sessionUser", user);
        return "redirect:/";
    }
}
