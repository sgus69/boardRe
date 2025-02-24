package com.board.replay.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.board.replay.dto.board.BoardRequestDto;
import com.board.replay.service.BoardService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardService service;
	
	@GetMapping("/board/list")
	public String getBoardListPage(Model model
			, @RequestParam(value="page", required = false, defaultValue = "0") Integer page
			, @RequestParam(value="size", required = false, defaultValue = "5") Integer size) throws Exception {
		
		 try { 
			 
			 System.out.println("트라이"); 
			 model.addAttribute("resultMap",service.findAll(page, size)); 
			 
			 }catch(Exception e) {
				 System.out.println("캐치");
				 throw new Exception(e.getMessage()); 
		 }
		 
		
		return "/board/list";
	}
	
	@GetMapping("/board/write")
	public String getBoardWritePage(Model model, BoardRequestDto dto) {
		return "/board/write";
	}
	
	@GetMapping("/board/view")
	public String getBoardViewPage(Model model, BoardRequestDto dto)throws Exception{
		
		try {
			if(dto.getId() != null) {
				model.addAttribute("info", service.findById(dto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		
		return "/board/view";
	}
	
	@PostMapping("/board/write/aciton")
	public String boardWriteAction(Model model, BoardRequestDto dto)throws Exception{
		try {
			Long result = service.save(dto);
			
			if(result<0) {
				throw new Exception("#Exception boardWriteAction!");
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/board/list";
	}
	
}
