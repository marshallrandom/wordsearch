package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class WordlookupApplication {



	public static void main(String[] args) {
		new WordDatabase();
		SpringApplication.run(WordlookupApplication.class, args);

	}
}
