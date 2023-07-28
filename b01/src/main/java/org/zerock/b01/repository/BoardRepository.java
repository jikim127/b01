package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {  //여기서 Board는 테이블 이름, Long은 기본키의 타입

    Board findByTitle(String title);
}
