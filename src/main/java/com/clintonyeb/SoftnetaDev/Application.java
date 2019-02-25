package com.clintonyeb.SoftnetaDev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Entry point to the Spring Application
 */
@SpringBootApplication
@EnableScheduling
public class Application {
    public static final Logger logger = LoggerFactory.getLogger("Softneta:::");

    public static void main(String[] args) {
        logger.info("=== Starting Softneta Application ===");
        SpringApplication.run(Application.class, args);
        logger.info("=== Started Softneta Application ===");
    }
}
