package br.inatel.mymusicapi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication
@EnableCaching
public class MyMusicApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyMusicApiApplication.class, args);
		log.info("The application is runnig.");
	}
}