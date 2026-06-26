package com.techevent.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.techevent.model.Evento;
import com.techevent.repository.EventoRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final EventoRepository eventoRepository;

    public ReporteController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @GetMapping("/eventos-pdf")
    public ResponseEntity<byte[]> generarPdf() throws Exception {
        List<Evento> eventos = eventoRepository.findAll();

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Título
        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("Listado de Eventos - TechEvent", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        document.add(titulo);

        // Tabla
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{3, 2, 2, 3});

        // Encabezados
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
        tabla.addCell(new PdfPCell(new Phrase("Nombre del Evento", headerFont)));
        tabla.addCell(new PdfPCell(new Phrase("Tipo", headerFont)));
        tabla.addCell(new PdfPCell(new Phrase("Fecha", headerFont)));
        tabla.addCell(new PdfPCell(new Phrase("Ponente", headerFont)));

        // Filas
        Font cellFont = new Font(Font.FontFamily.HELVETICA, 10);
        for (Evento e : eventos) {
            tabla.addCell(new PdfPCell(new Phrase(e.getNombre(), cellFont)));
            tabla.addCell(new PdfPCell(new Phrase(e.getTipo(), cellFont)));
            tabla.addCell(new PdfPCell(new Phrase(e.getFecha(), cellFont)));
            String nombrePonente = e.getPonente() != null ? e.getPonente().getNombre() : "Sin ponente";
            tabla.addCell(new PdfPCell(new Phrase(nombrePonente, cellFont)));
        }

        document.add(tabla);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "eventos.pdf");

        return ResponseEntity.ok().headers(headers).body(out.toByteArray());
    }
}
