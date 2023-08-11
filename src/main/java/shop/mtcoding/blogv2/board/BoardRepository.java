package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository에서 구현되어 있는 것 : save(), findById(), findAll(), count(), deleteById()
public interface BoardRepository extends JpaRepository<Board, Integer>{

}
