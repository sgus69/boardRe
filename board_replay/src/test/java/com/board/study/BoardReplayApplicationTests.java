package com.board.study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.board.replay.dto.board.BoardRequestDto;
import com.board.replay.service.BoardService;

@SpringBootTest
class BoardReplayApplicationTests {

	@Autowired
	private BoardService boardservice;
	
	@Test
	void save(){
		BoardRequestDto boardSaveDto = new BoardRequestDto();
		
		
		boardSaveDto.setTitle("제목");
		boardSaveDto.setContent("내용");
		boardSaveDto.setRegisterId("작성자");
		
		Long result = boardservice.save(boardSaveDto);
		
		if(result > 0) {
			System.out.println("# Success save()~");
			findAll();
			findById(result);
		}else {
			System.out.println("# Fail Save()~");
		}
		
	}

	private void findById(Long result) {
		// TODO Auto-generated method stub
		
	}

	private void findAll() {
		// TODO Auto-generated method stub
		
	}

}
