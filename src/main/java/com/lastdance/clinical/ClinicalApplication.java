package com.lastdance.clinical;

import com.lastdance.clinical.models.*;
import com.lastdance.clinical.repositories.*;
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
    public CommandLineRunner initData(PacienteRepository pacienteRepository, PacienteServicioRepository pacienteServicioRepository, ProfesionalRepository profesionalRepository, ServicioRepository servicioRepository, ProductoRepository productoRepository, PacienteProductoRepository pacienteProductoRepository,FacturaRepository facturaRepository) {
        return (args) -> {

            Paciente pacientePrueba1 = new Paciente("Santiago", "Aragon", "santy@mindhub.com", "santy123", 87654321L);
            pacientePrueba1.setActivo(false);
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

            //CREAR SERVICIO (DUEÑO RELACION)

            Servicio servicio1 = new Servicio(TipoServicio.CIRUGIAS,50000d);
            Servicio servicio2 = new Servicio(TipoServicio.LABORATORIO,6000d);
            Servicio servicio3 = new Servicio(TipoServicio.OBSTETRICIA,1500d);
            Servicio servicio4 = new Servicio(TipoServicio.NEUROLOGIA,8000d);
            Servicio servicio5 = new Servicio(TipoServicio.DENTISTA, 7000d);
            Servicio servicio6 = new Servicio(TipoServicio.OFTALMOLOGIA, 12000d);
            Servicio servicio7 = new Servicio(TipoServicio.PEDIATRIA, 5000d);
            Servicio servicio8 = new Servicio(TipoServicio.URGENCIAS, 2000d);


            //CREO PROFESIONALES QUE VAN A PERTENER A UN SERVICIO

            Profesional profesional1 = new Profesional("Ema", "Leiva", CIRUJANO, servicio1);
            Profesional profesional2 = new Profesional("Guille", "Bonutto", CARDIOLOGO, servicio1);
            Profesional profesional3 = new Profesional("Guille", "Cornetti", ANESTESIOLOGO, servicio2);
            Profesional profesional4 = new Profesional("Guille", "Bergessio", NEUROLOGO, servicio4);
            Profesional profesional5 = new Profesional("Facu", "Araujo", NEUROLOGO, servicio4);
            Profesional profesional6 = new Profesional("Santi", "Aragon",OBSTETRA,servicio3);
            Profesional profesional7 = new Profesional("Thomas", "Coutoune", CIRUJANO, servicio1);
            Profesional profesional8 = new Profesional("Lucio", "Fernandez", PEDIATRA, servicio7);
            Profesional profesional9 = new Profesional("Facundo", "Lorenzo", DENTISTA, servicio5);
            Profesional profesional10 = new Profesional("Pablo", "Figueroa", OFTALMOLOGO, servicio6);
            Profesional profesional11 = new Profesional("Flavio", "Ambroggio", NEUROLOGO, servicio7);


            servicio1.addProfesional(profesional1);
            servicio3.addProfesional(profesional6);
            //GUARDO LOS DATOS
            servicioRepository.save(servicio1);
            servicioRepository.save(servicio2);
            servicioRepository.save(servicio3);
            servicioRepository.save(servicio4);
            servicioRepository.save(servicio5);
            servicioRepository.save(servicio6);
            servicioRepository.save(servicio7);
            servicioRepository.save(servicio8);
            profesionalRepository.save(profesional1);
            profesionalRepository.save(profesional2);
            profesionalRepository.save(profesional3);
            profesionalRepository.save(profesional4);
            profesionalRepository.save(profesional5);
            profesionalRepository.save(profesional6);
            profesionalRepository.save(profesional7);
            profesionalRepository.save(profesional8);
            profesionalRepository.save(profesional9);
            profesionalRepository.save(profesional10);
            profesionalRepository.save(profesional11);

            //CREANDO PRODUCTOS
            Producto producto1 = new Producto("Jeringa", TipoProducto.PRODUCTOS, 100, 105d,"https://propato.com.ar/wp-content/uploads/jeringa-thomey-60cc-neojet-11851-1.jpg");
            Producto producto2 = new Producto("Kit quirurgico", TipoProducto.INSUMOS_QUIRURGICOS, 50, 500d,"http://www.goatindumentaria.com.ar/wp-content/uploads/2020/08/008_Kit-Descartable_Goat-Indumentaria-1.jpg");
            Producto producto3 = new Producto("Test rapido Covid", TipoProducto.TEST_RAPIDOS, 200, 250d,"https://1.bp.blogspot.com/-QFng5CA0lJs/YHbmIJIFZKI/AAAAAAACa2g/jqW0x920jTM6j7FDnKCgA0XXOi9lMi_owCLcBGAsYHQ/s1024/product_17845eb508654870b7af8376fcccd9f6_637533208097428237_0_l.jpeg");
            Producto producto4 = new Producto("Paracetamol 1000mg x50u ", TipoProducto.PRODUCTOS, 200, 250d,"https://farmalife.vteximg.com.br/arquivos/ids/170440-1200-1200/34399822-7e5a-4213-af1e-65a8cb9de0d1.jpg?v=637369026880370000");
            Producto producto5 = new Producto("Mascarillas Descartables x50u", TipoProducto.PRODUCTOS, 100, 1100d, "https://images-na.ssl-images-amazon.com/images/I/61ihbeIDGVL._AC_SL1001_.jpg");
            Producto producto6 = new Producto("Camisolines Hemorepelentes x10", TipoProducto.PRODUCTOS, 300, 1200d, "https://http2.mlstatic.com/D_NQ_NP_896515-MLA49400238394_032022-V.webp");
            Producto producto7 = new Producto("Tijera Metzembaum 16cm", TipoProducto.INSUMOS_QUIRURGICOS, 200, 1100d, "https://http2.mlstatic.com/D_NQ_NP_791969-MLA46542345670_062021-V.webp");
            Producto producto8 = new Producto("Insumo Guantes Latex x100", TipoProducto.INSUMOS_QUIRURGICOS, 500, 1600d, "https://http2.mlstatic.com/D_NQ_NP_974905-MLA45283398291_032021-V.webp" );
            Producto producto9 = new Producto("Evatest Easy Plus", TipoProducto.TEST_RAPIDOS, 50, 700d, "https://http2.mlstatic.com/D_NQ_NP_684221-MLA48050711576_102021-V.webp");
            Producto producto10 = new Producto("Tambor acero inoxidable", TipoProducto.INSUMOS_QUIRURGICOS, 10, 9000d, "https://http2.mlstatic.com/D_NQ_NP_943950-MLA46383742434_062021-V.webp");
            Producto producto11 = new Producto("Serum La Roche-Posay", TipoProducto.PRODUCTOS, 250, 9500d, "https://http2.mlstatic.com/D_NQ_NP_916302-MLA49424414969_032022-V.webp");
            Producto producto12 = new Producto("Protectores de oreja", TipoProducto.PRODUCTOS, 500, 250d, "https://http2.mlstatic.com/D_NQ_NP_979647-MLA47738719533_102021-V.webp");
            Producto producto13 = new Producto("Pinza Lahey Pasahilo", TipoProducto.INSUMOS_QUIRURGICOS, 100, 4800d, "https://http2.mlstatic.com/D_NQ_NP_724614-MLA49878762415_052022-V.webp");
            Producto producto14 = new Producto("Rama para Laringoscopio", TipoProducto.INSUMOS_QUIRURGICOS, 50, 2800d, "https://http2.mlstatic.com/D_NQ_NP_920226-MLA50247409626_062022-V.webp");
            Producto producto15 = new Producto("Cepillo Mano (seco esteril)", TipoProducto.PRODUCTOS, 500, 550d, "https://http2.mlstatic.com/D_NQ_NP_749032-MLA48421851901_122021-V.webp");

            //GUARDO LOS DATOS
            productoRepository.save(producto1);
            productoRepository.save(producto2);
            productoRepository.save(producto3);
            productoRepository.save(producto4);
            productoRepository.save(producto5);
            productoRepository.save(producto6);
            productoRepository.save(producto7);
            productoRepository.save(producto8);
            productoRepository.save(producto9);
            productoRepository.save(producto10);
            productoRepository.save(producto11);
            productoRepository.save(producto12);
            productoRepository.save(producto13);
            productoRepository.save(producto14);
            productoRepository.save(producto15);

            //CREO Y GUARDO LA FACTURA
            Factura factura1 = new Factura(pacientePrueba1);
            facturaRepository.save(factura1);

            //EL PACIENTE SOLICITA UN SERVICIO CON UN PROFESIONAL
            PacienteServicio pacienteServicio1 = new PacienteServicio(servicio1.getMonto(), LocalDateTime.now(), factura1, servicio1);
            pacienteServicioRepository.save(pacienteServicio1);

            factura1.addPacienteServicio(pacienteServicio1);

            //CREO COMPRAS A LOS CLIENTES
            PacienteProducto compra1 = new PacienteProducto(25, LocalDateTime.now(), factura1, producto1);
            pacienteProductoRepository.save(compra1);
            PacienteProducto compra2 = new PacienteProducto( 4, LocalDateTime.now(), factura1,producto2);
            pacienteProductoRepository.save(compra2);

            factura1.addPacienteProducto(compra1);
            factura1.addPacienteProducto(compra2);
            factura1.setMonto(pacienteServicio1.getMonto()+ compra1.getMonto()+ compra2.getMonto());

            //GUARDO LA FACTURA CON SUS COMPRAS DE PRODUCTOS Y SERVICIOS
            facturaRepository.save(factura1);


            Factura factura2 = new Factura(pacientePrueba8);
            facturaRepository.save(factura2);

            PacienteServicio pacienteServicio2 = new PacienteServicio(servicio3.getMonto(), LocalDateTime.now().minusDays(1), factura2, servicio3);
            PacienteServicio pacienteServicio3= new PacienteServicio(servicio3.getMonto(), LocalDateTime.now().minusHours(6), factura2, servicio3);
            pacienteServicioRepository.save(pacienteServicio2);
            pacienteServicioRepository.save(pacienteServicio3);

            factura1.addPacienteServicio(pacienteServicio2);
            factura1.addPacienteServicio(pacienteServicio3);
            factura2.setMonto(pacienteServicio2.getMonto()+ pacienteServicio3.getMonto());
            facturaRepository.save(factura2);
        };
    }
}
