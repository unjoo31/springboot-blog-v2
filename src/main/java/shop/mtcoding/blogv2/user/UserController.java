package shop.mtcoding.blogv2.user;

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
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO){
        User sessionUser = userService.로그인(loginDTO);
        if(sessionUser == null){
            return Script.back("로그인 실패");
        }

        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/");
    }
}
