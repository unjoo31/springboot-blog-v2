package shop.mtcoding.blogv2.board;

import lombok.Getter;
import lombok.Setter;

public class BoardRequest {

    // 게시글 저장 DTO
    @Getter
    @Setter
    public static class SaveDTO{
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private String title;
        private String content;
    }
}
