package com.essobhi.bookscape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookScapeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookScapeApplication.class, args);
	}

}
