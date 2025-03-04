package br.com.seduc.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Seduc2Application {

	public static void main(String[] args) {
		SpringApplication.run(Seduc2Application.class, args);
	}

	@Bean
	@Primary
	public PasswordEncoder getPasswordEnconder(){
		BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
		return enconder;
	}

}
