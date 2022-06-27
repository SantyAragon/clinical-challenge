package com.lastdance.clinical.services.implement;

import com.lastdance.clinical.services.PdfService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class PdfServiceImplements implements PdfService {

    @Override
    public void export(HttpServletResponse response) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(18);
        Paragraph title = new Paragraph("Primer pdf de la clinica", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(5);

        Paragraph paragraph = new Paragraph("probando equisde");
        title.setAlignment(Paragraph.ALIGN_RIGHT);
        title.setSpacingAfter(5);

        document.add(title);
        document.add(paragraph);
        document.close();
    }
}
