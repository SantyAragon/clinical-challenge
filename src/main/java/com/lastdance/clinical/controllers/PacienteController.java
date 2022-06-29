package com.lastdance.clinical.controllers;

import com.lastdance.clinical.DTOS.PacienteDTO;
import com.lastdance.clinical.models.Paciente;
import com.lastdance.clinical.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lastdance.clinical.utils.Pacienteutils.*;

@RestController
@RequestMapping("/api")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/pacientes")
    public Set<PacienteDTO> traerPacientesActivos() {
        return pacienteService.traerPacientes().stream().filter(paciente -> paciente.isActivo()).map(paciente -> new PacienteDTO(paciente)).collect(Collectors.toSet());
    }

    @GetMapping("/pacientes/all")
    public Set<PacienteDTO> traerTodosLosPacientes() {
        return pacienteService.traerPacientes().stream().map(paciente -> new PacienteDTO(paciente)).collect(Collectors.toSet());
    }

    @GetMapping("/pacientes/{id}")
    public PacienteDTO traerPaciente(@PathVariable Long id) {
        return pacienteService.traerPacienteDTO(id);
    }

    @GetMapping("/pacientes/autenticado")
    public PacienteDTO traerPaciente(Authentication authentication){
        return new PacienteDTO(pacienteService.traerPacientePorEmail(authentication.getName()));
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Object> registrarPaciente(@RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String contraseña, @RequestParam Long identificacion) throws MessagingException, UnsupportedEncodingException {

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || contraseña.isEmpty() || identificacion <= 0) {
            return new ResponseEntity<>("Información incompleta", HttpStatus.FORBIDDEN);
        }
        if (pacienteService.traerPacientePorEmail(email) != null) {
            return new ResponseEntity<>("Usuario ya registrado", HttpStatus.FORBIDDEN);
        }
        if (pacienteService.traerPacientePorIdentificacion(identificacion) != null) {
            return new ResponseEntity<>("El dni ya esta en uso", HttpStatus.FORBIDDEN);
        }

        Paciente paciente = new Paciente(nombre, apellido, email, passwordEncoder.encode(contraseña), identificacion, generarToken());
        pacienteService.guardarPaciente(paciente);

        enviarMailVerificacion(paciente.getEmail());

        return new ResponseEntity<>("Registrado exitosamente", HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping("/pacientes/verificacion")
    public ResponseEntity<Object> verificarEmail(@RequestParam String token) {
        if(pacienteService.traerPacientePorToken(token)==null)
            return new ResponseEntity<>("Token expirado.", HttpStatus.FORBIDDEN);

        Paciente paciente = pacienteService.traerPacientePorToken(token);
        if(paciente.getToken().isEmpty() || paciente.getToken().equals(""))
            return new ResponseEntity<>("Token expirado.", HttpStatus.FORBIDDEN);

        if(!token.equals(paciente.getToken()))
            return new ResponseEntity<>("Token no valido.", HttpStatus.FORBIDDEN);

        paciente.setActivo(true);
        paciente.setToken("");
        pacienteService.guardarPaciente(paciente);

        return new ResponseEntity<>("Verificacion exitosa", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/pacientes/contraseña")
    public ResponseEntity<Object> recuperarContraseña(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        if (email.isEmpty() || !email.contains("@"))
            return new ResponseEntity<>("Ingrese un email valido", HttpStatus.FORBIDDEN);
        if (pacienteService.traerPacientePorEmail(email) == null) {
            return new ResponseEntity<>("Email no vinculado a ninguna cuenta", HttpStatus.FORBIDDEN);
        }
        Paciente paciente = pacienteService.traerPacientePorEmail(email);
        paciente.setToken(generarToken());
        pacienteService.guardarPaciente(paciente);

        enviarMailCambioContraseña(email);

        return new ResponseEntity<>("Email enviado exitosamente", HttpStatus.ACCEPTED);
    }

    @Transactional
    @PostMapping("/pacientes/contraseña")
    public ResponseEntity<Object> cambiarContraseña(@RequestParam String token, @RequestParam String contraseña) {
        if (contraseña.length() < 6)
            return new ResponseEntity<>("Ingrese una contraseña de al menos 6 caracteres.", HttpStatus.FORBIDDEN);
        if(pacienteService.traerPacientePorToken(token)==null)
            return new ResponseEntity<>("Token expirado.", HttpStatus.FORBIDDEN);
        Paciente paciente = pacienteService.traerPacientePorToken(token);
        if(paciente.getToken().isEmpty()|| paciente.getToken().equals(""))
            return new ResponseEntity<>("Token expirado.", HttpStatus.FORBIDDEN);
        if(!token.equals(paciente.getToken()))
            return new ResponseEntity<>("Token no valido.", HttpStatus.FORBIDDEN);

        paciente.setToken("");
        paciente.setContraseña(contraseña);

        pacienteService.guardarPaciente(paciente);

        return new ResponseEntity<>("Cambio de contraseña exitoso", HttpStatus.ACCEPTED);
    }
    
    @PatchMapping("/pacientes/{id}/nombre")
    public ResponseEntity<Object> actualizarNombrePaciente(@PathVariable Long id, @RequestParam String nombre) {
        if (nombre.length() < 4) {
            return new ResponseEntity<>("Nombre demasiado corto", HttpStatus.FORBIDDEN);
        }
        Paciente paciente = pacienteService.traerPaciente(id);
        paciente.setNombre(nombre);
        pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<>("Modificacion de nombre exitosa", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/pacientes/{id}/apellido")
    public ResponseEntity<Object> actualizarApellidoPaciente(@PathVariable Long id, @RequestParam String apellido) {
        if (apellido.length() < 4) {
            return new ResponseEntity<>("Apellido demasiado corto", HttpStatus.FORBIDDEN);
        }
        Paciente paciente = pacienteService.traerPaciente(id);
        paciente.setApellido(apellido);
        pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<>("Modificacion de apellido exitosa", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/pacientes/{id}/email")
    public ResponseEntity<Object> actualizarEmailPaciente(@PathVariable Long id, @RequestParam String email) {

        Set<String> emails = pacienteService.traerPacientes().stream().map(Paciente::getEmail).collect(Collectors.toSet());

        if (emails.contains(email))
            return new ResponseEntity<>("Email ya en uso", HttpStatus.FORBIDDEN);
        if (!email.contains("@") || !email.endsWith(".com") || !email.endsWith(".net") || !email.endsWith(".es") || !email.endsWith(".com.ar"))
            return new ResponseEntity<>("Email ya en uso", HttpStatus.FORBIDDEN);


        Paciente paciente = pacienteService.traerPaciente(id);
        paciente.setEmail(email);
        pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<>("Modificacion de email exitosa", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/pacientes/{id}")
    public ResponseEntity<Object> desactivarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteService.traerPaciente(id);
        if (!paciente.isActivo()) {
            return new ResponseEntity<>("Paciente ya deshabiliado", HttpStatus.FORBIDDEN);
        }
        paciente.setActivo(false);
        pacienteService.guardarPaciente(paciente);
        return new ResponseEntity<>("Modificacion de estado exitosa", HttpStatus.ACCEPTED);
    }

//    @GetMapping("/autenticado")
//    public ResponseEntity<Object> estaAutenticado(Authentication authentication) {
//        if (authentication != null)
//            return new ResponseEntity<>("autenticado", HttpStatus.ACCEPTED);
//
//        return new ResponseEntity<>("no autenticado", HttpStatus.ACCEPTED);
//    }

    public void enviarMailVerificacion(String email) throws MessagingException, UnsupportedEncodingException {
        Paciente paciente = pacienteService.traerPacientePorEmail(email);

        String toAdress = email;
        String fromAddress = "clinicamedihub@gmail.com";
        String senderName = "Clinica MediHub";
        String subject = "Verificacion de email de registro";
        String content = generarEmailVerificacion();
        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);

        String urlToken = "http://localhost:8080/web/login.html?token=" + paciente.getToken();

        content = content.replace("[paciente]", paciente.getFullName());
        content = content.replace("[urlToken]", urlToken);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAdress);
        helper.setSubject(subject);
        helper.setText(content, true);
        javaMailSender.send(mensaje);
    }

    public void enviarMailCambioContraseña(String email) throws MessagingException, UnsupportedEncodingException {
        Paciente paciente = pacienteService.traerPacientePorEmail(email);

        String toAdress = email;
        String fromAddress = "clinicamedihub@gmail.com";
        String senderName = "Clinica MediHub";
        String subject = "Solicitud para restablecer su contraseña";
        String content = generarEmailRecuperacion();

        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);

        String urlToken = "http://localhost:8080/web/recuperar-contraseña.html?token=" + paciente.getToken();
        content = content.replace("[email]", paciente.getEmail());
        content = content.replace("[paciente]", paciente.getFullName());
        content = content.replace("[urlToken]", urlToken);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAdress);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(mensaje);
    }
}

