package br.com.compasso.avaliacao.avaliacaobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AvaliacaoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AvaliacaoBackendApplication.class, args);
    }

}
