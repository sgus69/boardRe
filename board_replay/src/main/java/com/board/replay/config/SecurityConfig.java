package com.board.replay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.board.replay.member.handler.AuthFailureHandler;
import com.board.replay.member.handler.AuthSuccessHandler;
import com.board.replay.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity //시큐리티 필터 등록
@EnableMethodSecurity(prePostEnabled = true)//특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 권한 및 인증을 미리 체크하겠다는 설정을 활성화한다.
public class SecurityConfig {
	
	private final MemberService memberService;
	private final AuthSuccessHandler authSucessHandler;
	private final AuthFailureHandler authFailureHandler;
	
	// BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체 (BCrypt라는 해시 함수를 이용하여 패스워드를 암호화 한다.)
	// 회원 비밀번호 등록시 해당 메서드를 이용하여 암호화해야 로그인 처리시 동일한 해시로 비교한다.
		
	@Bean
	public BCryptPasswordEncoder encryptPassword() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 로그인 과정에서 password를 가로챌때 해당 해쉬로 암호화해서 비교한다.
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService).passwordEncoder(encryptPassword());
	}
	protected void configure(HttpSecurity http)throws Exception{
		/*
		 csrf 토큰 활성화시 사용
		 쿠키를 생성할 때 HttpOnly 태그를 사용하면 클라이언트 스크립트가 보호된 쿠키에 액세스하는 위험을 줄일 수 있으므로 쿠키의 보안을 강화할 수 있다.
		*/
		//http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		http.csrf().disable()	//csrf 토큰을 비 활성화
			.authorizeHttpRequests() // 요청 url에 따라 접근 권한을 설정
			.anyRequest()//다른 모든 요청은
			.authenticated()//인증된 유저만 접근을 허용
		.and()
			.formLogin()//로그인 폼은
			.loginPage("/longin")//해당 주소로 로그인 페이지를 호출한다.
			.loginProcessingUrl("/login/action")//해당 url로 요청이 오면 스프링 시큐리티가 가로채서 로그인 처리를 한다. -> loadUserByName
			.successHandler(authSucessHandler)//성공시 요청을 처리할 핸들러
			.failureHandler(authFailureHandler)//실패시 요청을 처리할 핸들러
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//로그아웃 url
			.logoutSuccessUrl("/login")//성공시 리턴 url
			.invalidateHttpSession(true)//인증정보를 지우고 세션을 무효화
			.deleteCookies("JSESSIONID")//JSESSIONID  쿠키 삭제
			.permitAll()
	}
}
