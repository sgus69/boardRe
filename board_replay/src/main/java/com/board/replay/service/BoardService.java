package com.board.replay.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	public boolean save(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		Board result = boardRepository.save(boardRequestDto.toEntity());
		boolean resultFlag = false;
		if(result != null) {
			boardFileService.uploadFile(multiRequest, result.getId());
			resultFlag = true;
		}
		return resultFlag;
	}
	
	/**
	 	트랜잭션에 readOnly=true 옵션을 주면 스프링 프레임워크가 하이버네이트 세션 플러시 모드를  manual로 설정한다.
	 	이렇게 하면 강제로 플러시를 호출하지 않는 한 플러시가 일어나지 않는다.
	 	따라서 트랜잭션을 커밋하더라도 영속성 컨텍스트가 플러시 되지 않아서
	 	엔티티의 등록, 수정, 삭제가 동작하지 않고, 또한 읽기전용으로, 영속성 컨텍스트는 변경감지를 위한
	 	스냅샷을 보관하지 않으므로 성능이 향상된다.
	 **/
	@Transactional(readOnly = true)
	public HashMap<String, Object> findAll(Integer page, Integer size){
		
		HashMap<String, Object> resultMap = new HashMap<String,Object>();
		
		Page<Board> list = boardRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"))); 
		
		resultMap.put("list", list.stream().map(BoardResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", list.getPageable());
		resultMap.put("totalCnt", list.getTotalElements());
		resultMap.put("totalPage", list.getTotalPages());
		
		return resultMap;
	
	}
	
	public HashMap<String, Object> findById(Long id)throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		boardRepository.updateBoardReadCntInc(id);
		
		BoardResponseDto info = new BoardResponseDto(boardRepository.findById(id).get());
		
		resultMap.put("info", info);
		resultMap.put("fileList", boardFileService.findByBoardId(info.getId()));
		
		return resultMap;
	}
	
	public boolean updateBoard(BoardRequestDto boardRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		int result = boardRepository.updateBoard(boardRequestDto);
		
		boolean resultFlag = false;
		
		if(result > 0) {
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

