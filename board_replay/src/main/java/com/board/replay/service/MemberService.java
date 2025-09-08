package com.board.replay.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.board.replay.entity.board.member.Member;
import com.board.replay.entity.board.member.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	
	/**
	 * 로그인 시 실행됨
	 * @param email 로그인 폼에서 입력한 이메일
	 * @return UserDetails(Member)
	 **/
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return memberRepository.findByEmail(email).
				orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}
	
	/**
     * 회원가입 처리
     */
    public Member register(String email, String rawPassword) {
        // 중복 이메일 체크
        memberRepository.findByEmail(email).ifPresent(m -> {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        });

        // 비밀번호 암호화
        String encodedPwd = passwordEncoder.encode(rawPassword);

        Member member = Member.builder()
                .email(email)
                .pwd(encodedPwd)
                .role("ROLE_USER")
                .build();

        return memberRepository.save(member);
    }
}
