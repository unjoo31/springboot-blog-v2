package shop.mtcoding.blogv2.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시글 작성 화면 보여주기
    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }

    // 게시글 작성하기 기능
    // 1. 데이터 받기
    // 2. 인증 체크 (TODO)
    // 3. 유효성 검사 (TODO)
    // 4. 핵심로직 호출 (서비스)
    // 5. view or data 응답
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        boardService.글쓰기(saveDTO, 1);
        return "redirect:/";
    }
}
