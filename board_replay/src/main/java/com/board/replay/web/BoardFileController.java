package com.board.replay.web;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.replay.dto.board.file.BoardFileResponseDto;
import com.board.replay.service.BoardFileService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardFileController {
	private final BoardFileService boardFileService;
	
	@GetMapping("/file/download")
	public void downloadFile(@RequestParam()Long id, HttpServletResponse response)throws Exception{
		try {
			//파일정보를 조회한다.
			BoardFileResponseDto fileInfo = boardFileService.findById(id);
			
			if(fileInfo == null)throw new FileNotFoundException("Empty FileData.");
			
			//경로와 파일명으로 파일 객체를 생성한다.
			File dFile = new File(fileInfo.getFilePath(), fileInfo.getSaveFileName());
			
			//파일 길이를 가져온다.
			int fSize = (int)dFile.length();
			
			//파일이 존재하면
			if(fSize > 0) {
				//파일명을 URLencoder 하여 attachment, Content - Disposition Header로 설정
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
}
