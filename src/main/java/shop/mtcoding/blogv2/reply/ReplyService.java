package shop.mtcoding.blogv2.reply;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.board.BoardRepository;
import shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 댓글쓰기(SaveDTO saveDTO, Integer sessionid) {
        // 1. board id가 존재하는지 유무 체크
        Board board = Board.builder().id(saveDTO.getBoardId()).build();
        User user = User.builder().id(sessionid).build();

        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(board)
                .user(user)
                .build();
        replyRepository.save(reply); // entity : Reply객체
    }

    @Transactional
    public void 댓글작성(ReplyRequest.SaveDTO saveDTO, int sessionUserId) {
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(Board.builder().id(saveDTO.getBoardId()).build())
                .user(User.builder().id(sessionUserId).build())
                .build();
        replyRepository.save(reply);
    }

    @Transactional
    public void 댓글삭제(Integer id) {
        replyRepository.deleteById(id);
    }
}
