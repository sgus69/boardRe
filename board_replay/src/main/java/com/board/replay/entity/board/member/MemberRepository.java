package com.board.replay.entity.board.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	static final String UPDATE_MEMBER_LAST_LOGIN = "UPDATE Member "
				+ "SET LAST_LOGIN_TIME = :lastLoginTime "
				+ "WHERE EMAIL = :email";
	

}
