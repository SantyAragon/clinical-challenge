package com.lastdance.clinical.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity

@Configuration
class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/rest/**", "/h2-console/**").hasAuthority("ADMIN")
                .antMatchers("/web/login.html", "/web/scripts/login.js", "/web/styles/login.css", "/web/index.html", "/web/styles/custom.css", "/web/assets/**", "/web/scripts/index.js").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/pacientes").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/pacientes/verificacion").permitAll()

                .antMatchers(HttpMethod.GET, "/api/pacientes/autenticado").hasAnyAuthority("PACIENTE")
                .antMatchers(HttpMethod.PATCH, "/api/pacientes/contraseña").permitAll()
                .antMatchers(HttpMethod.POST, "/api/pacientes/contraseña").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/pacientes/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/pacientes/{id}", "/api/pacientes").hasAnyAuthority("ADMIN", "PROFESIONAL")

                .antMatchers(HttpMethod.GET, "/api/productos/{id}", "/api/productos").permitAll()
                .antMatchers(HttpMethod.POST, "/api/productos").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/productos/{id}/**").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/servicios/{id}", "/api/servicios").permitAll()
                .antMatchers(HttpMethod.POST, "/api/servicios").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/servicios/{id}/**").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.GET, "/api/profesional/{id}", "/api/profesional").permitAll()
                .antMatchers(HttpMethod.POST, "/api/profesional").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/profesional/{id}/**", "/api/profesional/{id}").hasAuthority("ADMIN")

                .antMatchers(HttpMethod.POST, "/api/facturas/create").hasAuthority("PACIENTE")
                .antMatchers(HttpMethod.POST, "/api/facturas/descargar/{id}").hasAnyAuthority("PACIENTE", "ADMIN")
                .antMatchers("/api/**").hasAuthority("ADMIN");


        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");


        http.logout().logoutUrl("/api/logout");
        // turn off checking for CSRF tokens

        http.csrf().disable();


        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
}


