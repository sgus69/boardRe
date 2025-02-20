package com.board.replay.dto.board;

import java.time.LocalDateTime;

import com.board.replay.entity.board.Board;

import lombok.Getter;

@Getter
public class BoardResponseDto {
	//게시판 응답 데이터를 담당
	//게시물 목록, 게시물 상세 조회에 필요한 필드 정의.
	private Long id;
	private String title;
	private String content;
	private int readCnt;
	private String registerId;
	private LocalDateTime registerTime;
	
	public BoardResponseDto(Board entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.readCnt = entity.getReadCnt();
		this.registerId = entity.getRegisterId();
		this.registerTime = entity.getRegisterTime();
	}

	@Override
	public String toString() {
		return "BoardResponseDto [id=" + id + ", title=" + title + ", content=" + content + ", readCnt=" + readCnt
				+ ", registerId=" + registerId + ", registerTime=" + registerTime + "]";
	}
	
	
}
