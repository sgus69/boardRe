package com.board.replay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.board.replay.dto.board.BoardRequestDto;
import com.board.replay.dto.board.BoardResponseDto;
import com.board.replay.entity.board.Board;
import com.board.replay.entity.board.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	//게시판@Service
	
	private final BoardRepository boardRepository;
	private final BoardFileService boardFileService;
	
	
	@Transactional
	public boolean save(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multi) throws Exception {
		
		Board result = boardRepository.save(boardRequestDto.toEntity());
		
		boolean resultFlag = false;
		
		if(result != null) {
			boardFileService.uploadFile(multi, boardRequestDto.getId());
			resultFlag = true;
		}
				
		return resultFlag;
	}
	
	@Transactional(readOnly = true)
	public HashMap<String, Object>findAll(){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<Board> list = boardRepository.findAll();
		
		if(list != null) {
			System.out.println("# Success findAll():"+ list.toString());
		}else {
			System.out.println("# Fail findAll()~");
		}
		
		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		
		return resultMap;
	}
	
	@Transactional(readOnly = true)//readOnly = true는 성능향상
	public HashMap<String, Object>findAll(Integer page, Integer size){
		System.out.println("서비스");
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Page<Board> list = boardRepository.findAll(PageRequest.of(page, size));
		if(list != null) {
			System.out.println("# Success findAll(page, size):"+ list.toString());
		}else {
			System.out.println("# Fail findAll(page, size)~");
		}
		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());		
				
		return resultMap;	
	}
	
	public HashMap<String, Object> findById(Long id) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		BoardResponseDto info = new BoardResponseDto(boardRepository.findById(id).get());
		System.out.println("인포: "+ info);
		
		resultMap.put("info", info);
		resultMap.put("fileList", boardFileService.findByBoardId(info.getId()));
		
		return resultMap;
	}
	
	public boolean updateBoard(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		int result = boardRepository.updateBoard(boardRequestDto);
		
		boolean resultFlag = false;
		
		if(result >0) {
			boardFileService.uploadFile(multiRequest, boardRequestDto.getId());
			resultFlag = true;
		}
		
		return resultFlag;
	}
	
	public void deleteById(Long id) throws Exception {
		Long[] idArr = {id};
		boardFileService.deleteBoardFileYn(idArr);
		boardRepository.deleteById(id);
	}
	public void deleteAll(Long[] deleteId) throws Exception {
		boardFileService.deleteBoardFileYn(deleteId);
		boardRepository.deleteBoard(deleteId);
	}

	
}
