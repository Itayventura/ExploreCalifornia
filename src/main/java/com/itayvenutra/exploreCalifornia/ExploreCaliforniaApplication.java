package com.itayvenutra.exploreCalifornia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itayvenutra.exploreCalifornia.domain.Difficulty;
import com.itayvenutra.exploreCalifornia.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
public class ExploreCaliforniaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExploreCaliforniaApplication.class, args);
	}

}
