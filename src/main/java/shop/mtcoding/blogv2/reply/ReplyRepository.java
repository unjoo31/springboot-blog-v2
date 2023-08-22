package shop.mtcoding.blogv2.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{

    @Query("select r from Reply r where r.board.id = :boardId")
    List<Reply> findByBoardId(@Param("boardId") Integer boardId);
}
