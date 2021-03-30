package fr.gantoin.bitchuteuploader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BitchuteUploaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitchuteUploaderApplication.class, args);
    }

}
