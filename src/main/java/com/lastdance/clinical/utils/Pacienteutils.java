package com.lastdance.clinical.utils;

import net.bytebuddy.utility.RandomString;

import java.util.HashSet;
import java.util.Set;

public final class Pacienteutils {
    private static Set<String> tokensGenerados = new HashSet<>();

    public static String generarToken() {
        String token;
        do {
            token = RandomString.make(48);
        } while (tokensGenerados.contains(token));
        tokensGenerados.add(token);
        return token;
    }

    public static String generarEmailVerificacion() {
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
                "                                        <h2>Por favor verifica tu email</h2>\n" +
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
        return content;
    }

    public static String generarEmailRecuperacion() {
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
                "\n" +
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
                "                                        <h2>Recuperacion de contraseña</h2>\n" +
                "                                        <h3>\n" +
                "                                            Estimado/a [paciente]:\n" +
                "                                            <p>\n" +
                "                                                Ha recibido el siguiente email, en el cual solicita recuperar la\n" +
                "                                                contraseña de la cuenta vinculada a la dirección: [email]</p>\n" +
                "\n" +
                "                                        </h3>\n" +
                "                                        <p><a href=\"[urlToken]\" class=\"btn btn-primary\">Restablecer contraseña</a></p>\n" +
                "                                        <p>Si no puedes ver el botón, puedes acceder desde el siguiente enlace: <a\n" +
                "                                                href=\"[urlToken]\">Presionando aquí</a></p>\n" +
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
                "</html>";
        return content;
    }
}
