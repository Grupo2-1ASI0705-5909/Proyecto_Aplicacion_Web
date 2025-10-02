package org.upc.trabajo_aplicaciones_web;


import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrabajoAplicacionesWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrabajoAplicacionesWebApplication.class, args);
    }


}
