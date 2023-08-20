package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 Repository, EntityManager가 메모리에 띄운다
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void deleteById_test(){
        boardRepository.deleteById(1);
    }

    @Test
    public void findById_test(){
        Optional<Board> boardOP = boardRepository.findById(5);
        if(boardOP.isPresent()){ // Board가 존재한다면
            System.out.println("테스트 : board가 있습니다.");
        }
    }

    @Test
    public void findAll_paging_test() throws JsonProcessingException{
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Board> boardPG = boardRepository.findAll(pageable);
        ObjectMapper om = new ObjectMapper();

        // ObjectMapper는 boardPG객체의 getter를 호출하면서 json을 만들다
        String json = om.writeValueAsString(boardPG); // 자바객체를 JSON으로 변환
        System.out.println(json);
    }

    @Test
    public void findAll_test(){
        System.out.println("조회 직전");
        List<Board> boardList = boardRepository.findAll();
        System.out.println("조회 후 : Lazy");
        // 행 : 5개 -> 객체 : 5개
        // 각행 : Board (id=1, title=제목1, content=내용1, created_at=날짜, User(id=1))
        System.out.println(boardList.get(0).getId()); // 1번 (조회 X)
        System.out.println(boardList.get(0).getUser().getId()); // 1번 (조회 X)

        // Lazy Loading - 지연로딩
        // 연관된 객체에 null을 참조하려고 하면 조회가 일어남 (조회 O)
        System.out.println(boardList.get(0).getUser().getUsername()); // null -> ssar
        System.out.println(boardList.get(3).getUser().getUsername());
    }

    @Test
    public void mfindAll_test(){
        boardRepository.mFindAll();
    }

    // save 테스트
    @Test
    public void save_test(){
        // 비영속 객체
        // db에 넣을때는 user_id만 들어가면 되기 때문에 유저객체에다가 id만 넣으면 됨
        Board board = Board.builder()
            .title("제목6")
            .content("내용6")
            .user(User.builder().id(1).build())
            .build();

        // 영속 객체
        boardRepository.save(board); // insert 자동으로 실행됨
        // rollback
        // 비디데이터와 동기화됨
        System.out.println(board.getId());
    }
}
