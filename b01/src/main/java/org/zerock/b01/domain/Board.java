package org.zerock.b01.domain;

import lombok.*;
import javax.persistence.*;

/////////// BoardDTO는 자바에서 변수사용! / Board는 DB에서 테이블생성용!/////////

@Entity  // 이걸보고 DB에 테이블을 만들어 주는거임 (이름은 아래 class name, 만약 이름 설정하고 싶으면 (name="Board222") 이렇게 뒤에 붙여주면 됨.
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //넘버링해주는 구문! 오라클이면 .SEQUENCE로 선택하면 됨.
    private Long bno;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false, updatable = false)
    private String writer;

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
