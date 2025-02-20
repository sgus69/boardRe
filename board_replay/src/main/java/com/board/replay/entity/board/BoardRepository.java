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
	
	
	String UPDATE_BOARD = "UPDATE Board"+
			"SET TITLE = :#{#boardRequestDto.title},"+
			"CONTENT = :#{#boardRequestDto.content},"+
			"UPDATE_TIME = NOW()" + 
			"WHERE ID = :#{#boardRequestDto.id}";
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_BOARD, nativeQuery = true)
	public int updateBoard(@Param("boardRequestDto")BoardRequestDto boardREquestDto);
}
