package com.board.replay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.board.replay.member.handler.AuthFailureHandler;
import com.board.replay.member.handler.AuthSuccessHandler;
import com.board.replay.service.MemberService;

import lombok.RequiredArgsConstructor;

@Configuration
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
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)throws Exception{
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return memberService;
	}
	
	//시큐리티 필터 체인 설정
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http	
			.csrf(csrf ->csrf.disable())
			.authorizeHttpRequests(auth ->auth
					.requestMatchers("/", "/login/**", "/js/**", "/css/**", "/image/**").permitAll()
					.anyRequest().authenticated()
			)
			.formLogin(form -> form
					.loginPage("/login")
					.loginProcessingUrl("/login/action")
					.successHandler(authSucessHandler)
					.failureHandler(authFailureHandler)
					.permitAll()
			)
			.logout(logout ->logout
					.logoutRequestMatcher(new AntPathRequestMatcher("logout"))
					.logoutSuccessUrl("/login")
					.invalidateHttpSession(true)
					.deleteCookies("JESSIONID")
					.permitAll()
			)
			.sessionManagement(session ->session
					.maximumSessions(1)
					.maxSessionsPreventsLogin(false)
					.expiredUrl("/login?error=true&exception=Have been attempted to login from a new place. or session expired")
			)
			.rememberMe(rememberMe ->rememberMe
						.userDetailsService(memberService)
						.tokenValiditySeconds(43200)
						.rememberMeParameter("remember-me")
						.alwaysRemember(false)
			);
		return http.build();
	}
}
