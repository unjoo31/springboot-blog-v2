package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// JpaRepository에서 구현되어 있는 것 : save(), findById(), findAll(), count(), deleteById()
// 스프링이 실행될 때, BoardRepository의 구현체가 IoC 컨테이너에 생성된다
public interface BoardRepository extends JpaRepository<Board, Integer>{

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
    
    @Query("select b from Board b join fetch b.user where b.title like :keyword")
    List<Board> FindKewordAll(@Param("keyword") String keyword);

    // native query : select id, title, content, user_id, created_at from board_tb b inner join user_tb u on b.user_id = u.id;
    // jpql
    // fetch를 붙여야 전체를 프로젝션한다 ( * ), 안붙이면 join할때 user_id만 조회한다
    @Query("select b from Board b join fetch b.user")
    List<Board> mFindAll();

    // 게시글 댓글 조인
    // 댓글이 없어도 게시글이 나와야하기 때문에 left join으로 사용해야 한다
    @Query("select b from Board b left join fetch b.replies r left join fetch r.user ru where b.id = :id")
    Optional <Board> mFindByIdJoinRepliesInUser(@Param("id") Integer id);
}
