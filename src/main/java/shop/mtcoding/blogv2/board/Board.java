package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.dynamic.TypeResolutionStrategy.Lazy;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = true, length = 10000)
    private String content;

    // @ManyToOne Eager 전략(디폴트)
    // EAGER : 디폴트값, 연관관계 조회함
    // LAZY : 연관관계 조회안함
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 1 + n

    // @OneToMany Lazy 전략(디폴트)
    // @OneToMany : 양방향매핑
    // mappedBy = "board" : fk가 아니기 때문에 아니라는 설정이 필요하다 / "board" 는 변수명을 사용한다 / mappedBy를 했기 때문에 읽기 전용으로 바뀐다.
    // @JsonIgnoreProperties : 특정 필드들을 무시하고 무시할 필드들의 이름을 지정하는 데 사용
    // cascade = CascadeType.ALL : 댓글삭제시 게시글도 함께 삭제됨
    @JsonIgnoreProperties({"board"})
    //@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @CreationTimestamp // insert할때 자동으로 시간을 넣어줌
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }
}