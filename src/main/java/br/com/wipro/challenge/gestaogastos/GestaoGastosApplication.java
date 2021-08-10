package br.com.wipro.challenge.gestaogastos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication()
@EnableMongoRepositories
public class GestaoGastosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestaoGastosApplication.class, args);
    }

}
