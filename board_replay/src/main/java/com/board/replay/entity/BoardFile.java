package com.board.replay.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BoardFile {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long boardId;
	private String origFileName;
	private String saveFileName;
	private int fileSize;
	private String fileExt;
	private String filePath;
	private String deleteYn;
	
	@CreatedDate
	private LocalDateTime registerTime;
	
}
