package com.lastdance.clinical.services;

import com.lowagie.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public interface PdfService {
//    void export(HttpServletResponse response, Authentication authentication, String numberAccount, LocalDateTime since, LocalDateTime until) throws IOException, DocumentException;
    void export(HttpServletResponse response,Long id) throws IOException, DocumentException;

}
