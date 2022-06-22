package com.lastdance.clinical;

import com.lastdance.clinical.models.*;
import com.lastdance.clinical.repositories.PacienteRepository;
import com.lastdance.clinical.repositories.PacienteServicioRepository;
import com.lastdance.clinical.repositories.ProfesionalRepository;
import com.lastdance.clinical.repositories.ServicioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.lastdance.clinical.models.TipoEspecialidad.*;

@SpringBootApplication
public class ClinicalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicalApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PacienteRepository pacienteRepository, PacienteServicioRepository pacienteServicioRepository, ProfesionalRepository profesionalRepository, ServicioRepository servicioRepository) {
        return (args) -> {

            Paciente pacientePrueba1 = new Paciente("Santiago", "Aragon", "santy@mindhub.com", "santy123", 87654321L);
            pacienteRepository.save(pacientePrueba1);
            Paciente pacientePrueba2 = new Paciente("Thomas", "Coutoune", "thomy@mindhub.com", "bocayoteamo", 12345678L);
            pacienteRepository.save(pacientePrueba2);

            Paciente pacientePrueba3 = new Paciente("Mario", "Illia", "marioillia@hotmail.com", "marito123", 26867955L);
            pacienteRepository.save(pacientePrueba3);
            Paciente pacientePrueba4 = new Paciente("Alberto", "Kempes", "alberto.kempes@gmail.com", "kempes", 31546872L);
            pacienteRepository.save(pacientePrueba4);
            Paciente pacientePrueba5 = new Paciente("Sofia", "Gomez", "sofiag@hotmail.com", "sofi456", 27485201L);
            pacienteRepository.save(pacientePrueba5);
            Paciente pacientePrueba6 = new Paciente("Mariana", "Albertengo", "marualb@hotmail.com", "riverdelab", 37485201L);
            pacienteRepository.save(pacientePrueba6);
            Paciente pacientePrueba7 = new Paciente("Liliana", "Diaz", "lili.d@gmail.com", "myoutube", 13457999L);
            pacienteRepository.save(pacientePrueba7);
            Paciente pacientePrueba8 = new Paciente("Facundo", "Dambolena", "facu.dambo@gmail.com", "responsive", 50218743L);
            pacienteRepository.save(pacientePrueba8);
            Paciente pacientePrueba9 = new Paciente("Martha", "Wayne", "mart.w@gmail.com", "turuleca", 42182172L);
            pacienteRepository.save(pacientePrueba9);
            Paciente pacientePrueba10 = new Paciente("Gabriela", "Medina", "gabimedina@hotmail.com", "medinam123", 17245712L);
            pacienteRepository.save(pacientePrueba10);

            //CREO EL SERVICIO SIN PROFESIONAL
            Profesional profesional1 = new Profesional("Ema", "Leiva", CIRUGÍA);

            Servicio servicio1 = new Servicio(TipoServicio.CIRUGIAS,profesional1);
//            servicioRepository.save(servicio1);
            // CREO EL PROFESIONAL CON EL SERVICIO PREVIAMENTE CREADO

            // ASIGNO AL SERVICIO CREADO, EL PROFESIONAL CREADO EN EL PASO ANTERIOR


//            Servicio servicioAct1 = servicioRepository.findById(1L).orElse(null);

            servicio1.addProfesional(profesional1);
            servicioRepository.save(servicio1);
            profesionalRepository.save(profesional1);
            //GUARDO LOS DATOS
//            servicioRepository.save(servicio1);
//            profesionalRepository.save(profesional1);

            //EL PACIENTE SOLICITA UN SERVICIO CON UN PROFESIONAL
            PacienteServicio pacienteServicio = new PacienteServicio(100D, LocalDateTime.now(), pacientePrueba1, servicio1);
            pacienteServicioRepository.save(pacienteServicio);

        };
    }
}
