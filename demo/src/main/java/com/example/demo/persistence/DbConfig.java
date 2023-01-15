package com.example.demo.persistence;

import com.example.demo.persistence.entity.TodoEntryEty;
import com.example.demo.persistence.repository.TodoEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
@Slf4j
public class DbConfig {
    @Bean
    public CommandLineRunner createInitialDatabaseValues(TodoEntryRepository todoEntryRepository) {
        return args -> {
            Stream.of(new TodoEntryEty()
                            .withTitle("Homework"))
                    .forEach(person -> log.info("PRELOADING: {}", todoEntryRepository.save(person)));
        };
    }
}
