package shop.mtcoding.blogv2.board;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blogv2.user.User;

// 1. 비지니스 로직 처리(핵신 로직)
// 2. 트랜잭션 관리
// 3. 예외처리
// 4. DTO변환 (나중에)
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Board 상세보기(Integer id) {
        // board 만 가져오면 된다!!
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new RuntimeException(id + "는 찾을 수 없습니다");
        }
    }

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build())
                .build();
        boardRepository.save(board);
    }

    public Page<Board> 게시글목록보기(Integer page){
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findAll(pageable);
    }

    @Transactional
    public void 삭제하기(Integer id) {
        boardRepository.deleteById(id);
    }
}
