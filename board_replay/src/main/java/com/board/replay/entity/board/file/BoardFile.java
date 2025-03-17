package com.board.replay.entity.board.file;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
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
	
	@Builder
	public BoardFile(Long id, Long boardId, String origFileName, String saveFileName, int fileSize, String fileExt, String filePath, String deleteYn, LocalDateTime registerTime) {
		this.id = id;
		this.boardId = boardId;
		this.origFileName = origFileName;
		this.saveFileName = saveFileName;
		this.fileSize = fileSize;
		this.fileExt = fileExt;
		this.filePath = filePath;
		this.deleteYn = deleteYn;
		this.registerTime = registerTime;
	}
	
}
