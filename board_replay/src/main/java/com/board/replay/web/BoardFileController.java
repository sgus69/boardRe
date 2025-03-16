package com.board.replay.web;

import org.springframework.stereotype.Controller;

import com.board.replay.service.BoardFileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardFileController {
	private final BoardFileService fileService;
}
