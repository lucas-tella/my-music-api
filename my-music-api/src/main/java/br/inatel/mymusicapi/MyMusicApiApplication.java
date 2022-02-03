package br.inatel.mymusicapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MyMusicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyMusicApiApplication.class, args);
	}
}