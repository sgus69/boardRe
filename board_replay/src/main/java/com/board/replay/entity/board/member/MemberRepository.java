package com.board.replay.entity.board.member;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	//마지막 로그인 시간 업데이트
	static final String UPDATE_MEMBER_LAST_LOGIN = "UPDATE Member "
				+ "SET LAST_LOGIN_TIME = :lastLoginTime "
				+ "WHERE EMAIL = :email";
	
	@Transactional
	@Modifying
	@Query(value=UPDATE_MEMBER_LAST_LOGIN, nativeQuery = true)
	public int updateMemberLastLogin(@Param("email")String email,
			@Param("lastLoginTime")LocalDateTime lastLoginTime);
	
	//이메일 기준 회원 조회(Optional로 반환)
	Optional<Member> findByEmail(String emfail);
}
