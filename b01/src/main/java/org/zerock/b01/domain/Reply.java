package org.zerock.b01.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Reply", indexes = {@Index(name="idx_reply_board_bno", columnList = "board_bno")})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
public class Reply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)  // 내가(Reply) 다수이고 참조하고 있는 table(Board)가 one 
    private Board board;                // Fetch default값은 EAGER이지만 LAZY로 바꿔주는게 성능에 효과를 볼수 있음

    private String replyText;

    private String replyer;
}
