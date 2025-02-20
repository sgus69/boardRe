package com.board.replay.dto.board;

import com.board.replay.entity.board.Board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardRequestDto {
	//게시판 요청 데이터를 담당
	//게시물 등록, 수정, 상세조회에 필요한 필드 정의
	
	private Long id;//게시글아이디?
	private String title;//제목
	private String content;//내용
	private String registerId;//등록날짜?
	
	public Board toEntity() {
		return Board.builder()
					.title(title)
					.content(content)
					.registerId(registerId)
					.build();
	}
	
	
	
}
