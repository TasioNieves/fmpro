package com.tmpro.controller;

import com.tmpro.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // Método para obtener el reporte de un jugador
    @GetMapping("/players/{id}")
    public ResponseEntity<byte[]> getPlayerReport(@PathVariable Long id) {
        // Se pasa el Long id directamente
        ByteArrayInputStream pdfContent = reportService.generatePlayerReport(String.valueOf(id));

        // Configuración de los encabezados para descargar el archivo PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=player_report_" + id + ".pdf");

        // Se devuelve el contenido del archivo PDF como respuesta
        return new ResponseEntity<>(pdfContent.readAllBytes(), headers, HttpStatus.OK);
    }
}
