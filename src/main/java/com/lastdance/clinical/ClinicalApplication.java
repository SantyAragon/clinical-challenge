package com.lastdance.clinical;

import com.lastdance.clinical.models.Paciente;
import com.lastdance.clinical.repositories.PacienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClinicalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicalApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PacienteRepository pacienteRepository) {
        return (args) -> {

            Paciente pacientePrueba1 = new Paciente("Santiago", "Aragon", "santy@mindhub.com","santy123", 87654321L);
            pacienteRepository.save(pacientePrueba1);
            Paciente pacientePrueba2 = new Paciente("Thomas", "Coutoune", "thomy@mindhub.com","thomi123", 12345678L);
            pacienteRepository.save(pacientePrueba2);

        };
    }
}
