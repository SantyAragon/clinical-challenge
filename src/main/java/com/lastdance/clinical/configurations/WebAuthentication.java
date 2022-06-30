package com.lastdance.clinical.configurations;

import com.lastdance.clinical.models.Paciente;
import com.lastdance.clinical.models.Profesional;
import com.lastdance.clinical.repositories.PacienteRepository;
import com.lastdance.clinical.repositories.ProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    PacienteRepository pacienteRepository;
    @Autowired
    ProfesionalRepository profesionalRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName -> {

            Paciente paciente = pacienteRepository.findByEmail(inputName).orElse(null);
            Profesional profesional = profesionalRepository.findByEmail(inputName);

            if (paciente != null || profesional != null) {


                if (paciente != null) {
                    if (paciente.getEmail().contains("@admin.medihub.com"))
                        return new User(paciente.getEmail(), paciente.getContraseña(),
                                AuthorityUtils.createAuthorityList("ADMIN", "PACIENTE", "PROFESIONAL"));

                }
                if (profesional != null) {
                    if (profesional.getEmail().contains("@Medihub.com")) {

                        return new User(profesional.getEmail(), profesional.getContraseña(),
                                AuthorityUtils.createAuthorityList("PROFESIONAL"));
                    }

                }
                return new User(paciente.getEmail(), paciente.getContraseña(),
                        AuthorityUtils.createAuthorityList("PACIENTE"));

            } else {

                throw new UsernameNotFoundException("Paciente desconocido: " + inputName);

            }
        });
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}