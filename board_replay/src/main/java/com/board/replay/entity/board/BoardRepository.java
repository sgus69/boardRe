package com.board.replay.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.board.replay.dto.board.BoardRequestDto;
import jakarta.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long> {
	//jpaRepository를 상속받아 curd의 기능을 담당하는 인터페이스를 생성한다.
	//그리고 @Query를 사용한 JPQL방식의 updateBoard()메서드도 구현해본다.
	
	
	String UPDATE_BOARD = "UPDATE BOARD "+// BOARD 와 SET사이에 공백이 있어야 하므로 띄어쓰기 꼭 하기
			"SET TITLE = :#{#boardRequestDto.title}, "+
			"CONTENT = :#{#boardRequestDto.content}, "+
			"UPDATE_TIME = TO_DATE(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') " + 
			"WHERE ID = :#{#boardRequestDto.id}";
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_BOARD, nativeQuery = true)
	public int updateBoard(@Param("boardRequestDto")BoardRequestDto boardREquestDto);
}
