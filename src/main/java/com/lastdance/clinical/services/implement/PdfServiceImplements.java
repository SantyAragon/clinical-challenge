package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.models.Factura;
import com.lastdance.clinical.models.PacienteProducto;
import com.lastdance.clinical.models.PacienteServicio;
import com.lastdance.clinical.models.Profesional;
import com.lastdance.clinical.services.FacturaService;
import com.lastdance.clinical.services.PdfService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.List;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@Service
public class PdfServiceImplements implements PdfService {

    @Autowired
    FacturaService facturaService;

    @Override
    public void export(HttpServletResponse response, Long id) throws IOException, DocumentException {
        Factura factura = facturaService.traerFactura(id);

        Set<PacienteServicio> servicios = factura.getServicios();
        Set<PacienteProducto> productos = factura.getProductos();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontFactura = FontFactory.getFont(FontFactory.COURIER_BOLD);
        fontFactura.setSize(20);

        Paragraph textoFactura = new Paragraph("COMPROBANTE", fontFactura);
        textoFactura.setAlignment(Element.ALIGN_TOP);
        textoFactura.setAlignment(Element.ALIGN_RIGHT);
        textoFactura.setSpacingAfter(5);

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(14);

        Paragraph title = new Paragraph("Comprobante n°" + factura.getId(), fontTitle);
        title.setAlignment(Paragraph.ALIGN_MIDDLE);
        title.setSpacingAfter(5);

        Paragraph paragraphServicios = new Paragraph("Servicios tomados");
        paragraphServicios.setAlignment(Paragraph.ALIGN_LEFT);
        paragraphServicios.setSpacingAfter(5);


        document.add(textoFactura);
        document.add(title);

        Font fontTabla = FontFactory.getFont(FontFactory.HELVETICA);
        fontTabla.setSize(12);
        fontTabla.setColor(new Color(20, 189, 165));

        if (servicios.size() > 0) {
            PdfPTable tableServicios = new PdfPTable(5);
            tableServicios.setWidthPercentage(100);

            java.util.List<String> headersServicios = java.util.List.of("ID", "Servicio", "Monto", "Fecha", "Profesional");

            headersServicios.forEach(element -> {
                PdfPCell c1 = new PdfPCell(new Phrase(element, fontTabla));
//            c1.setBackgroundColor(new Color(48, 227, 202));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBorderWidthLeft(0);
                c1.setBorderWidthRight(0);
                tableServicios.addCell(c1);
            });

            Comparator<PacienteServicio> idComparatorServ = Comparator.comparing(PacienteServicio::getId);

            servicios.stream().sorted(idComparatorServ).forEach(servicio -> {
                PdfPCell c1 = new PdfPCell(new Phrase(servicio.getId() + ""));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBorderWidth(0);
                tableServicios.addCell(c1);

                PdfPCell c2 = new PdfPCell(new Phrase(servicio.getServicio().getTipoServicio() + ""));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                c2.setBorderWidth(0);
                tableServicios.addCell(c2);

                PdfPCell c3 = new PdfPCell(new Phrase(servicio.getServicio().getMonto() + ""));
                c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                c3.setBorderWidth(0);
                tableServicios.addCell(c3);

                PdfPCell c4 = new PdfPCell(new Phrase(servicio.getFecha().format(ISO_LOCAL_DATE) + ""));
                c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                c4.setBorderWidth(0);
                tableServicios.addCell(c4);

                PdfPCell c5 = new PdfPCell(new Phrase(servicio.getProfesional().getFullName() + ""));
                c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                c5.setBorderWidth(0);
                tableServicios.addCell(c5);

            });

            PdfPCell subtotalServicios = new PdfPCell(new Phrase("Subtotal servicios:", fontTabla));
            subtotalServicios.setColspan(4);
            subtotalServicios.setPaddingTop(4);
            subtotalServicios.setBorderWidth(0);
//        subtotalProductos.setBackgroundColor(new Color(48, 227, 202));
            subtotalServicios.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableServicios.addCell(subtotalServicios);

            Double totalServicios = servicios.stream().map(PacienteServicio::getMonto).reduce(Double::sum).orElse(0d);
            PdfPCell precioServicios = new PdfPCell(new Phrase(totalServicios + "", fontTabla));
            precioServicios.setPaddingTop(4);
            precioServicios.setBorderWidth(0);
//        precioProductos.setBackgroundColor(new Color(217, 217, 217));
            precioServicios.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableServicios.addCell(precioServicios);


            document.add(paragraphServicios);
            document.add(tableServicios);
        }

        if (productos.size() > 0) {
            Paragraph paragraphProductos = new Paragraph("Productos comprados");
            paragraphProductos.setAlignment(Paragraph.ALIGN_LEFT);
            paragraphProductos.setSpacingAfter(5);

            PdfPTable tableProductos = new PdfPTable(5);
            tableProductos.setWidthPercentage(100);

            java.util.List<String> headersProductos = java.util.List.of("Id Producto", "Producto", "Cantidad", "Precio unitario", "Monto");

            headersProductos.forEach(element -> {
                PdfPCell c1 = new PdfPCell(new Phrase(element, fontTabla));
                //c1.setBackgroundColor(new Color(48, 227, 202));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBorderWidthLeft(0);
                c1.setBorderWidthRight(0);
                tableProductos.addCell(c1);
            });

            Integer number = 1;

            Comparator<PacienteProducto> idComparatorProd = Comparator.comparing(PacienteProducto::getId);

            productos.stream().sorted(idComparatorProd).forEach(producto -> {

                PdfPCell c1 = new PdfPCell(new Phrase(producto.getProducto().getId() + ""));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBorderWidth(0);
                tableProductos.addCell(c1);

                PdfPCell c2 = new PdfPCell(new Phrase(producto.getProducto().getNombre() + ""));
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                c2.setBorderWidth(0);
                tableProductos.addCell(c2);

                PdfPCell c3 = new PdfPCell(new Phrase(producto.getCantidad() + ""));
                c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                c3.setBorderWidth(0);
                tableProductos.addCell(c3);

                PdfPCell c4 = new PdfPCell(new Phrase(producto.getProducto().getPrecio() + ""));
                c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                c4.setBorderWidth(0);
                tableProductos.addCell(c4);

                PdfPCell c5 = new PdfPCell(new Phrase(producto.getMonto() + ""));
                c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                c5.setBorderWidth(0);
                tableProductos.addCell(c5);

            });

            PdfPCell subtotalProductos = new PdfPCell(new Phrase("Subtotal productos:", fontTabla));
            subtotalProductos.setColspan(4);
            subtotalProductos.setPaddingTop(4);
            subtotalProductos.setBorderWidth(0);
            //subtotalProductos.setBackgroundColor(new Color(48, 227, 202));
            subtotalProductos.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableProductos.addCell(subtotalProductos);

            Double totalProductos = productos.stream().map(PacienteProducto::getMonto).reduce(Double::sum).orElse(0d);
            PdfPCell precioProductos = new PdfPCell(new Phrase(totalProductos + "", fontTabla));
            precioProductos.setPaddingTop(4);
            precioProductos.setBorderWidth(0);
            //precioProductos.setBackgroundColor(new Color(217, 217, 217));
            precioProductos.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableProductos.addCell(precioProductos);

            document.add(paragraphProductos);
            document.add(tableProductos);
        }

        document.close();
    }

//    public static boolean esPar(Integer number) {
//        return number % 2 == 0;
//    }
//
//    public static void changeColor(Integer number, PdfPCell newCell, Font font) {
//        if (esPar(number)) {
//            newCell.setBackgroundColor(Color.getHSBColor(0.23251028F, 0.3266129F, 0.972549F));
//            font.setColor(Color.DARK_GRAY);
//        } else {
//            newCell.setBackgroundColor(Color.WHITE);
//            font.setColor(Color.BLACK);
//        }
//    }
}
