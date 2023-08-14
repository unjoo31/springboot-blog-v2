package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;

// 핵심로직 처리, 트랜잭션 관리, 예외처리
@Service
public class UserService {
    // Service는 Repository를 의존한다

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 회원가입(JoinDTO joinDTO) {
        // UserRepository에서 User객체를 관리하기 때문에 User로 받아야한다
        User user = User.builder()
            .username(joinDTO.getUsername())
            .password(joinDTO.getPassword())
            .email(joinDTO.getEmail())
            .build();
        userRepository.save(user); // 내부적으로 em.persist가 일어난다
    }

    public User 로그인(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 1. 유저네임 검증
        if(user == null){
            return null;
        }

        // 2. 패스워드 검증 (입력한 비밀번호와 db에 있는 비밀번호를 비교)
        if(!user.getPassword().equals(loginDTO.getPassword())){
            return null;
        }

        // 3. 로그인 성공
        return user;
    }
}
