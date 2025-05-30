package com.board.replay.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
	
	@PostMapping("/board/write/action")
	public String boardWriteAction(Model model, BoardRequestDto dto, MultipartHttpServletRequest multi)throws Exception{
		try {
			boolean result = service.save(dto, multi);
			
			if(result == false) {
				throw new Exception("#Exception boardWriteAction!");
			}
			System.out.println("글쓰기+");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/board/list";
	}
	@GetMapping("/board/view")
	public String getBoardViewPage(Model model, BoardRequestDto dto)throws Exception{
		System.out.println("dto.getid: "+ dto.getId());
		try {
			if(dto.getId() != null) {
				model.addAttribute("resultMap", service.findById(dto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "/board/view";
	}
	@PostMapping("/board/view/action")
	public String boardViewAction(Model model, BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest)throws Exception{

		try {
			boolean result = service.updateBoard(boardRequestDto, multiRequest);
			
			if(!result) {
				throw new Exception("#Exception boardViewAction");
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/board/view/delete")
	public String boardViewDeleteAction(Model model, @RequestParam()Long id)throws Exception{
		
		try {
			service.deleteById(id);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/board/list";
	}
	
	
}
