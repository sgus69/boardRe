package com.board.replay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing//jpa auditing기능 활성화
@SpringBootApplication
public class BoardReplayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardReplayApplication.class, args);
	}

}
