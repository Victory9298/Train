package com.example.app;

import com.example.app.entity.Train;
import com.example.app.repository.TrainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TrainRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Train(546, "234", 43)));
            log.info("Preloading " + repository.save(new Train(689, "546", 56)));
            log.info("Preloading " + repository.save(new Train(789, "897", 69)));
            log.info("Preloading " + repository.save(new Train(632, "964", 87)));
        };
    }
}
