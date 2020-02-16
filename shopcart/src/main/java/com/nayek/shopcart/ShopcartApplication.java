package com.nayek.shopcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.nayek.shopcart.user.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
public class ShopcartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopcartApplication.class, args);
	}

}
