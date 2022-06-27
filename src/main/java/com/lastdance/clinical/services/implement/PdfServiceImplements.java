package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.models.Factura;
import com.lastdance.clinical.models.PacienteProducto;
import com.lastdance.clinical.models.PacienteServicio;
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
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(18);

        Paragraph title = new Paragraph("Factura n°" + factura.getId(), fontTitle);
        title.setAlignment(Paragraph.ALIGN_MIDDLE);
        title.setSpacingAfter(5);

        Paragraph paragraphServicios = new Paragraph("Servicios tomados");
        paragraphServicios.setAlignment(Paragraph.ALIGN_LEFT);
        paragraphServicios.setSpacingAfter(5);

        PdfPTable tableServicios = new PdfPTable(6);
        tableServicios.setWidthPercentage(100);

        java.util.List<String> headersServicios = java.util.List.of("ID", "Servicio", "Description", "Amount", "Date", "Profesional");

        Font fontTabla = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(18);

        headersServicios.forEach(element -> {
            PdfPCell c1 = new PdfPCell(new Phrase(element,fontTabla));
            c1.setBackgroundColor(new Color(48,227,202));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableServicios.addCell(c1);
        });



        Paragraph paragraphProductos = new Paragraph("Productos comprados");
        paragraphProductos.setAlignment(Paragraph.ALIGN_LEFT);
        paragraphProductos.setSpacingAfter(5);


        PdfPTable tableProductos = new PdfPTable(5);
        tableProductos.setWidthPercentage(100);

        java.util.List<String> headersProductos = java.util.List.of("IdProducto", "Producto", "Cantidad", "Monto", "Fecha");

        headersProductos.forEach(element -> {
            PdfPCell c1 = new PdfPCell(new Phrase(element,fontTabla));
            c1.setBackgroundColor(new Color(48,227,202));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableProductos.addCell(c1);
        });

        document.add(title);
        document.add(paragraphServicios);
        document.add(tableServicios);
        document.add(paragraphProductos);
        document.add(tableProductos);
        document.close();
    }
}
