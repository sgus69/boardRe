package com.board.replay.member.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.board.replay.entity.board.member.MemberRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final MemberRepository memberRepository = null;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)throws IOException, ServletException {
		
		memberRepository.updateMemberLastLogin(authentication.getName(), LocalDateTime.now());
		setDefaultTargetUrl("/board/list");
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
