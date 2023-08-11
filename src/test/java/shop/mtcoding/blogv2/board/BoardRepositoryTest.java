package shop.mtcoding.blogv2.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 Repository, EntityManager가 메모리에 띄운다
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

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
