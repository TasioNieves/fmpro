package com.tmpro.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.tmpro.model.Player;
import com.tmpro.model.Statistic;
import com.tmpro.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private StatisticRepository statisticRepository;

    // Generar informe para un jugador específico
    public ByteArrayInputStream generatePlayerReport(String playerName) {
        // Obtener el jugador por nombre
        Player player = playerService.findByName(playerName)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));

        // Obtener las estadísticas del jugador
        List<Statistic> statistics = statisticRepository.findByPlayerId(player.getId());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Agregar contenido al PDF
            document.add(new Paragraph("Informe de Jugador").setFontSize(18).setBold());
            document.add(new Paragraph("Nombre: " + player.getName()));
            document.add(new Paragraph("Posición: " + player.getPosition()));
            document.add(new Paragraph("Dorsal: " + player.getDorsal()));
            document.add(new Paragraph("Estadísticas del Jugador:"));

            // Crear una tabla para mostrar las estadísticas del jugador
            Table table = new Table(4); // Columnas para goles, asistencias, tarjetas y minutos jugados
            table.addHeaderCell("Partido ID");
            table.addHeaderCell("Goles");
            table.addHeaderCell("Asistencias");
            table.addHeaderCell("Minutos Jugados");

            // Agregar datos de estadísticas a la tabla
            for (Statistic stat : statistics) {
                table.addCell(String.valueOf(stat.getMatch().getId())); // ID del partido
                table.addCell(String.valueOf(stat.getGoals()));         // Goles
                table.addCell(String.valueOf(stat.getAssists()));       // Asistencias
                table.addCell(String.valueOf(stat.getMinutesPlayed())); // Minutos jugados
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el informe");
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
