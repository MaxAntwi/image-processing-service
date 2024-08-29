package org.max.imageprocessingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ImageProcessingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageProcessingServiceApplication.class, args);
    }

}
