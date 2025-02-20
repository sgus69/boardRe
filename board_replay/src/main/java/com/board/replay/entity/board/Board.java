package com.board.replay.entity.board;

import java.time.LocalDateTime;

import com.board.replay.entity.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor //기본생성자를 자동생성
@Getter //모든 필드에 getter메서드를 자동생성
@Entity //jpa엔티티임을 선언하는 어노테이션,board테이블과 연결됨
public class Board extends BaseTimeEntity{
	//board테이블의 @Entity
	//테이블의 모든 필드와 Builder생성자를 구현한다.
	
	@Id //primary key로 사용할 필드에 적용
	@GeneratedValue(strategy = GenerationType.IDENTITY)//id값을 자동증가시키는 방식 지정, GenerationType.IDENTITY는 db의 auto_increment기능을 그대로 사용, id값을 개발자가 직접 설정할 필요 없이 db가 자동 증가시킴
	private Long id;
	private String title;
	private String content;
	private int readCnt;
	private String registerId;
	//private LocalDateTime registerTime;
	
	@Builder //생성자 레벨 어노테이션. 객체를 생성할 때 builder 패턴을 사용할 수 있도록 지원.
	public Board(Long id, String title, String content, int readCnt, String registerId) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.readCnt = readCnt;
		this.registerId = registerId;
	}
	
}
