package com.board.replay.web;

import javax.management.RuntimeErrorException;

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
public class BoardController<MultipartHttpServeletRequest> {

	private final BoardService boardService;
	
	@GetMapping("/board/list")
	public String getBoardListPage(Model model 
			, @RequestParam(name="page", required=false, defaultValue="0")Integer page
			, @RequestParam(name="size", required=false, defaultValue="5")Integer size
			)throws Exception{
			
		System.out.println("파라미터:"+size +", page:"+page);
		
		  try { model.addAttribute("resultMap", boardService.findAll(page, size));
		  
		  } catch (Exception e) { throw new Exception(e.getMessage()); }
		 
		
		return "/board/list";
	}
	
	@GetMapping("/board/view")
	public String getBoardViewPage(Model model, BoardRequestDto boardRequestDto) throws Exception {
			try {
				if(boardRequestDto.getId()!=null)
				model.addAttribute("resultMap",boardService.findById(boardRequestDto.getId()));
			} catch (Exception e) {
				throw new RuntimeException("게시글 상세페이지 불러오기 실패",e);
			}
		
		return "/board/view";
	}
	
	@GetMapping("/boar/write")
	public String getBoardWritePage(Model model, BoardRequestDto boardRequestDto) {
		return "/board/write";
	}
	
	@PostMapping("/board/write/atcion")//글쓰기
	public String boardWriteAction(Model model, BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		try {
			if(!boardService.save(boardRequestDto, multiRequest)) {
				throw new Exception("#Exception boardWriteAtion!");
			}
		} catch (Exception e) {
			throw new RuntimeException("게시글 저장중 예외발생", e);
		}
		
		return "redirect:/board/list";
	}
	
	@PostMapping("/board/view/action")//수정
	public String boardViewAction(Model model, BoardRequestDto boardRequestDto, MultipartHttpServletRequest multi) {
		try {
			boolean result = boardService.updateBoard(boardRequestDto, multi);
			if(!result) {
				throw new Exception("#Exception boardViewAction");
			}
		} catch (Exception e) {
			throw new RuntimeException("게시글 수정중 예외발생", e);
		}
		
		return "redirct:/board/view";
	}
	@PostMapping("/board/view/delete")
	public String boardViewDelete(@RequestParam()Long id) {
		
		try {
			boardService.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("게시글 삭제중 예외발생", e);
		}
		return "redirect:/board/list";
	}
	@PostMapping("/board/delete")
	public String boardDelete(@RequestParam()Long[] id) {
		
		try {
			boardService.deleteAll(id);
		} catch (Exception e) {
			throw new RuntimeException("게시글 리스트 삭제중 예외 발생", e);
		}
		
		return "redirect:board/list";
	}
}
