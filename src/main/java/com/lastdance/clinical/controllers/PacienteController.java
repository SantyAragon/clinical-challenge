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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lastdance.clinical.utils.Pacienteutils.generarToken;

@RestController
@RequestMapping("/api")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;
    @Autowired
    JavaMailSender javaMailSender;

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

        Paciente paciente = new Paciente(nombre, apellido, email, contraseña, identificacion, generarToken());
        pacienteService.guardarPaciente(paciente);

        enviarMailVerificacion(paciente.getEmail());

        return new ResponseEntity<>("Registrado exitosamente", HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping("/pacientes/verificacion")
    public ResponseEntity<Object> verificarEmail(@RequestParam String token) {

        Paciente paciente = pacienteService.traerPacientePorToken(token);
        paciente.setActivo(true);
        paciente.setToken("");
        pacienteService.guardarPaciente(paciente);

        return new ResponseEntity<>("Verificacion exitosa", HttpStatus.ACCEPTED);
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

    //    @PostMapping("/pacientes/enviarEmail")
    public void enviarMailVerificacion(String email) throws MessagingException, UnsupportedEncodingException {
        Paciente paciente = pacienteService.traerPacientePorEmail(email);

        String toAdress = email;
        String fromAddress = "clinicamedihub@gmail.com";
        String senderName = "Clinica MediHub";
        String subject = "Verificacion de email de registro";
        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "    xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\"> <!-- utf-8 works for most cases -->\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\"> <!-- Forcing initial-scale shouldn't be necessary -->\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\"> <!-- Disable auto-scale in iOS 10 Mail entirely -->\n" +
                "    <title></title> <!-- The title tag shows in email notifications, like Android 4.4. -->\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato:300,400,700\" rel=\"stylesheet\">\n" +
                "    <!-- CSS Reset : BEGIN -->\n" +
                "    <style>\n" +
                "        html,\n" +
                "        body {\n" +
                "            margin: 0 auto !important;\n" +
                "            padding: 0 !important;\n" +
                "            height: 100% !important;\n" +
                "            width: 100% !important;\n" +
                "            background: #f1f1f1;\n" +
                "        }\n" +
                "        /* What it does: Stops email clients resizing small text. */\n" +
                "        * {\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "        }\n" +
                "        /* What it does: Centers email on Android 4.4 */\n" +
                "        div[style*=\"margin: 16px 0\"] {\n" +
                "            margin: 0 !important;\n" +
                "        }\n" +
                "        /* What it does: Stops Outlook from adding extra spacing to tables. */\n" +
                "        table,\n" +
                "        td {\n" +
                "            mso-table-lspace: 0pt !important;\n" +
                "            mso-table-rspace: 0pt !important;\n" +
                "        }\n" +
                "        /* What it does: Fixes webkit padding issue. */\n" +
                "        table {\n" +
                "            border-spacing: 0 !important;\n" +
                "            border-collapse: collapse !important;\n" +
                "            table-layout: fixed !important;\n" +
                "            margin: 0 auto !important;\n" +
                "        }\n" +
                "        /* What it does: Uses a better rendering method when resizing images in IE. */\n" +
                "        img {\n" +
                "            -ms-interpolation-mode: bicubic;\n" +
                "        }\n" +
                "        /* What it does: Prevents Windows 10 Mail from underlining links despite inline CSS. Styles for underlined links should be inline. */\n" +
                "        a {\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "        /* What it does: A work-around for email clients meddling in triggered links. */\n" +
                "        *[x-apple-data-detectors],\n" +
                "        /* iOS */\n" +
                "        .unstyle-auto-detected-links *,\n" +
                "        .aBn {\n" +
                "            border-bottom: 0 !important;\n" +
                "            cursor: default !important;\n" +
                "            color: inherit !important;\n" +
                "            text-decoration: none !important;\n" +
                "            font-size: inherit !important;\n" +
                "            font-family: inherit !important;\n" +
                "            font-weight: inherit !important;\n" +
                "            line-height: inherit !important;\n" +
                "        }\n" +
                "        /* What it does: Prevents Gmail from displaying a download button on large, non-linked images. */\n" +
                "        .a6S {\n" +
                "            display: none !important;\n" +
                "            opacity: 0.01 !important;\n" +
                "        }\n" +
                "        /* What it does: Prevents Gmail from changing the text color in conversation threads. */\n" +
                "        .im {\n" +
                "            color: inherit !important;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <!-- CSS Reset : END -->\n" +
                "    <!-- Progressive Enhancements : BEGIN -->\n" +
                "    <style>\n" +
                "        .primary {\n" +
                "            background: #30e3ca;\n" +
                "        }\n" +
                "        .bg_white {\n" +
                "            background: #ffffff;\n" +
                "        }\n" +
                "        .bg_light {\n" +
                "            background: #fafafa;\n" +
                "        }\n" +
                "        .bg_black {\n" +
                "            background: #000000;\n" +
                "        }\n" +
                "        .bg_dark {\n" +
                "            background: rgba(0, 0, 0, .8);\n" +
                "        }\n" +
                "        .email-section {\n" +
                "            padding: 2.5em;\n" +
                "        }\n" +
                "        /*BUTTON*/\n" +
                "        .btn {\n" +
                "            padding: 10px 15px;\n" +
                "            display: inline-block;\n" +
                "        }\n" +
                "        .btn.btn-primary {\n" +
                "            border-radius: 5px;\n" +
                "            background: #30e3ca;\n" +
                "            color: #ffffff;\n" +
                "        }\n" +
                "        h1,h2,h3,h4,h5,h6 {\n" +
                "            font-family: 'Lato', sans-serif;\n" +
                "            color: #000000;\n" +
                "            margin-top: 0;\n" +
                "            font-weight: 400;\n" +
                "        }\n" +
                "        body {\n" +
                "            font-family: 'Lato', sans-serif;\n" +
                "            font-weight: 400;\n" +
                "            font-size: 15px;\n" +
                "            line-height: 1.8;\n" +
                "            color: rgba(0, 0, 0, .4);\n" +
                "        }\n" +
                "        a {\n" +
                "            color: #30e3ca;\n" +
                "        }\n" +
                "        /*LOGO*/\n" +
                "        .logo h1 {\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "        .logo h1 a {\n" +
                "            color: #30e3ca;\n" +
                "            font-size: 24px;\n" +
                "            font-weight: 700;\n" +
                "            font-family: 'Lato', sans-serif;\n" +
                "        }\n" +
                "        /*HERO*/\n" +
                "        .hero {\n" +
                "            position: relative;\n" +
                "            z-index: 0;\n" +
                "        }\n" +
                "        .hero .text {\n" +
                "            color: rgba(0, 0, 0, .3);\n" +
                "        }\n" +
                "        .hero .text h2 {\n" +
                "            color: #000;\n" +
                "            font-size: 40px;\n" +
                "            margin-bottom: 0;\n" +
                "            font-weight: 400;\n" +
                "            line-height: 1.4;\n" +
                "        }\n" +
                "        .hero .text h3 {\n" +
                "            font-size: 24px;\n" +
                "            font-weight: 300;\n" +
                "        }\n" +
                "        .hero .text h2 span {\n" +
                "            font-weight: 600;\n" +
                "            color: #30e3ca;\n" +
                "        }\n" +
                "        ul.social {\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        ul.social li {\n" +
                "            display: inline-block;\n" +
                "            margin-right: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body width=\"100%\" style=\"margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #f1f1f1;\">\n" +
                "    <center style=\"width: 100%; background-color: #f1f1f1;\">\n" +
                "\n" +
                "        <div style=\"max-width: 600px; margin: 0 auto;\" class=\"email-container\">\n" +
                "            <!-- BEGIN BODY -->\n" +
                "            <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\"\n" +
                "                style=\"margin: auto;\">\n" +
                "                <tr>\n" +
                "                    <td valign=\"top\" class=\"bg_white\" style=\"padding: 1em 2.5em 0 2.5em;\">\n" +
                "                        <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                            <tr>\n" +
                "                                <td class=\"logo\" style=\"text-align: center;\">\n" +
                "                                    <h1><a>Medihub</a></h1>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr><!-- end tr -->\n" +
                "                <tr>\n" +
                "                    <td valign=\"middle\" class=\"hero bg_white\" style=\"padding: 3em 0 2em 0;\">\n" +
                "                        <img src=\"https://cdn.filestackcontent.com/RgkX8AudQhGUjzXCLmyU\" alt=\"\"\n" +
                "                            style=\"width: 50px; max-width: 100px; height: auto; margin: auto; display: block;\">\n" +
                "                    </td>\n" +
                "                </tr><!-- end tr -->\n" +
                "                <tr>\n" +
                "                    <td valign=\"middle\" class=\"hero bg_white\" style=\"padding: 2em 0 4em 0;\">\n" +
                "                        <table>\n" +
                "                            <tr>\n" +
                "                                <td>\n" +
                "                                    <div class=\"text\" style=\"padding: 0 2.5em; text-align: center;\">\n" +
                "                                        <h2>Porfavor verifica tu email</h2>\n" +
                "                                        <h3>\n" +
                "                                            Estimado/a [paciente]:\n" +
                "                                            <p>\n" +
                "                                            Gracias por solicitar una cuenta de Medihub.\n" +
                "                                            Para ayudarnos a confirmar su identidad, verifique su dirección de correo\n" +
                "                                            electrónico.</p>\n" +
                "\n" +
                "                                        </h3>\n" +
                "                                        <p><a href=\"[urlToken]\" class=\"btn btn-primary\">Si! Confirmar email</a></p>\n" +
                "                                    </div>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr><!-- end tr -->\n" +
                "                <!-- 1 Column Text + Button : END -->\n" +
                "            </table>\n" +
                "        </div>\n" +
                "    </center>\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        MimeMessage mensaje = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje);

        String urlToken = "http://localhost:8080/web/login.html?token=" + paciente.getToken();

        content = content.replace("[paciente]", paciente.getFullName());
        content = content.replace("[urlToken]", urlToken);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAdress);
        helper.setSubject(subject);
//        helper.setText(content);
        helper.setText(content, true);
        javaMailSender.send(mensaje);
    }
}

