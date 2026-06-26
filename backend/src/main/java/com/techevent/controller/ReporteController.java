package com.techevent.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.techevent.model.Evento;
import com.techevent.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/eventos-pdf")
    public ResponseEntity<byte[]> generarPdf() throws Exception {

        List<Evento> eventos = eventoRepository.findAll();

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        // Titulo
        Font fTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Paragraph titulo = new Paragraph("Listado de Eventos - TechEvent", fTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));

        // Tabla con 4 columnas
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);

        // Encabezados
        tabla.addCell("Nombre del Evento");
        tabla.addCell("Tipo");
        tabla.addCell("Fecha");
        tabla.addCell("Ponente");

        // Datos
        for (Evento e : eventos) {
            tabla.addCell(e.getNombre());
            tabla.addCell(e.getTipo());
            tabla.addCell(e.getFecha());
            if (e.getPonente() != null) {
                tabla.addCell(e.getPonente().getNombre());
            } else {
                tabla.addCell("Sin ponente");
            }
        }

        document.add(tabla);
        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "eventos.pdf");

        return ResponseEntity.ok().headers(headers).body(out.toByteArray());
    }
}
