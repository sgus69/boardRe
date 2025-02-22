package com.board.replay;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.board.replay.dto.board.BoardRequestDto;
import com.board.replay.dto.board.BoardResponseDto;
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
	void findAll() {
		List<BoardResponseDto> list = boardservice.findAll();
		
		if(list != null) {
			System.out.println("# Success findAll():"+ list.toString());
		}else {
			System.out.println("# Fail findAll()~");
		}
		
	}
	
	void findById(Long id) {
		BoardResponseDto info = boardservice.findById(id);
		if(info != null) {
			System.out.println("# Success findById()"+info.toString());
			updateBoard(id);
		}
	}

	void updateBoard(Long id) {
		BoardRequestDto boardRequestDto = new BoardRequestDto();
		boardRequestDto.setId(id);
		boardRequestDto.setTitle("업데이트 제목");
		boardRequestDto.setContent("업데이트 내용");
		boardRequestDto.setRegisterId("작성자");
		
		int result = boardservice.updateBoard(boardRequestDto);
		
		if(result >0) {
			System.out.println("# Success updateBoard()~");
		}else {
			System.out.println("# Fail updateBoard()~");
		}
				
	}

	

}
