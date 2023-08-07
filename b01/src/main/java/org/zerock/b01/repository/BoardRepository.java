package org.zerock.b01.repository;

import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {  //여기서 Board는 테이블 이름, Long은 기본키의 타입

    //Board findByTitle(String title);

    //Querymethod start
    List<Board> findByWriter(String writer);



    List<Board> findByWriterAndContent(String writer, String content);    // 쿼리스트링!!

    List<Board> findByBnoBetween(Long start, Long end);

    List<Board> findByWriterLikeAndContent(String name, String content);

    List<Board> findByWriterContaining(String name);

    List<Board> findByBnoLessThanOrderByContentDesc(Long bno);

    //Querymethod end


    //JPQL start
    ////// JPQL --> @Query로 날려줌 ////////
    @Query("select b from Board b where b.title like %:user% order by b.bno desc") //Board는 Board 클래스를 나타내고 별칭(b)을 만들어줘야함. user에 테스트에서 정의해논 "2"가 들어감.
    List<Board> aaa(@Param("user") String user); //@Query로 날릴때는 쿼리스트링 문법으로 안써도 상관없음

    @Query("select board.bno, board.title, board.writer " +
            "from Board board where board.writer like %:user% and board.title like %:title%")
    List<Object[]> findByWriterTitleDetail(@Param("user") String user, @Param("title")String title);

    @Query("select b from Board b where b.title like concat('%', :keyword, '%')")
    Page<Board> findKeyword(String keyword, Pageable pageable);

    @Query(value = "select * from board where bno = :bno", nativeQuery = true)
    Board searchBno(@Param("bno") Long bno);

    @Query("select b from Board b where b.title like %?1%")
    List<Board> findByTitle(String title);
    //JQPL end


}
