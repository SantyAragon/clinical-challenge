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
            Servicio servicio2 = new Servicio(TipoServicio.LABORATORIOS,6000d);
            Servicio servicio3 = new Servicio(TipoServicio.CONSULTA,1500d);
            Servicio servicio4 = new Servicio(TipoServicio.ELECTROCARDIOGRAMA,8000d);

            //CREO PROFESIONALES QUE VAN A PERTENER A UN SERVICIO
            Profesional profesional1 = new Profesional("Ema", "Leiva", CIRUGÍA, servicio1);
            Profesional profesional2 = new Profesional("Guille", "Bonutto", CARDIOLOGÍA, servicio1);
            Profesional profesional3 = new Profesional("Guille", "Cornetti", ANESTESIOLOGÍA, servicio2);
            Profesional profesional4 = new Profesional("Guille", "Bergesio", ALERGIA, servicio4);
            Profesional profesional5 = new Profesional("Facu", "Araujo", CARDIOLOGÍA, servicio4);
            Profesional profesional6=new Profesional("Santi", "Aragon",CLÍNICA_MEDICA,servicio3);
            servicio1.addProfesional(profesional1);
            servicio3.addProfesional(profesional6);
            //GUARDO LOS DATOS
            servicioRepository.save(servicio1);
            servicioRepository.save(servicio2);
            servicioRepository.save(servicio3);
            servicioRepository.save(servicio4);
            profesionalRepository.save(profesional1);
            profesionalRepository.save(profesional2);
            profesionalRepository.save(profesional3);
            profesionalRepository.save(profesional4);
            profesionalRepository.save(profesional5);
            profesionalRepository.save(profesional6);

            //CREANDO PRODUCTOS
            Producto producto1 = new Producto("Jeringa", TipoProducto.PRODUCTOS, 100, 105d);
            Producto producto2 = new Producto("Kit quirurgico", TipoProducto.INSUMOS_QUIRUJICOS, 50, 500d);
            Producto producto3 = new Producto("Test rapido Covid", TipoProducto.TEST_RAPIDOS, 200, 250d);
            Producto producto4 = new Producto("Paracetamol 1000mg x50u ", TipoProducto.PRODUCTOS, 200, 250d);

            //GUARDO LOS DATOS
            productoRepository.save(producto1);
            productoRepository.save(producto2);
            productoRepository.save(producto3);
            productoRepository.save(producto4);


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
