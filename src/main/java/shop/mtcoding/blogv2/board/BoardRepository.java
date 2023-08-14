package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository에서 구현되어 있는 것 : save(), findById(), findAll(), count(), deleteById()
// 스프링이 실행될 때, BoardRepository의 구현체가 IoC 컨테이너에 생성된다
public interface BoardRepository extends JpaRepository<Board, Integer>{

}
