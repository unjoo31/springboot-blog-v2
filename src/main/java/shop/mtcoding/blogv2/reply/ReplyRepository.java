package shop.mtcoding.blogv2.reply;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO;

public interface ReplyRepository extends JpaRepository<Reply, Integer>{


}
