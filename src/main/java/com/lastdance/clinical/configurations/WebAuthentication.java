package com.lastdance.clinical.configurations;

import com.lastdance.clinical.models.Paciente;
import com.lastdance.clinical.repositories.PacienteRepository;
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

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName -> {

            Paciente paciente = pacienteRepository.findByEmail(inputName);

            if (paciente != null) {

                return new User(paciente.getEmail(), paciente.getContraseña(),

                        AuthorityUtils.createAuthorityList("PACIENTE", "ADMIN"));

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