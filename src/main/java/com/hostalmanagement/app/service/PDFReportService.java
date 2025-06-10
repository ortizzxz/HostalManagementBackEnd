package com.hostalmanagement.app.service;

import com.hostalmanagement.app.DTO.RoomDTO;
import com.hostalmanagement.app.model.Reservation;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PDFReportService {

    public byte[] generateRoomReport(List<RoomDTO> rooms) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("Hostal Room Report").setBold().setFontSize(16));
        document.add(new Paragraph("Generated on: " + LocalDate.now()));
        document.add(new Paragraph("\n"));

        // Table: 6 columns (Number, Type, Capacity, Rate, State, Occupied)
        Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 2, 1, 1, 1, 1 }))
                .useAllAvailableWidth();

        // Table Header
        table.addHeaderCell(new Cell().add(new Paragraph("Room #")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Type")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Capacity")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Rate")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("State")).setBold().setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Occupied")).setBold().setTextAlignment(TextAlignment.CENTER));

        int occupiedCount = 0;

        for (RoomDTO room : rooms) {
            table.addCell(String.valueOf(room.getNumber()));
            table.addCell(room.getType());
            table.addCell(String.valueOf(room.getCapacity()));
            table.addCell("$" + room.getBaseRate());
            table.addCell(room.getState().toString());

            boolean isOccupied = room.getTenantDTO() != null;
            table.addCell(isOccupied ? "Yes" : "No");

            if (isOccupied)
                occupiedCount++;
        }

        document.add(table);

        // Summary section
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Total Rooms: " + rooms.size()));
        document.add(new Paragraph("Occupied: " + occupiedCount));
        document.add(new Paragraph("Vacant: " + (rooms.size() - occupiedCount)));

        document.close();
        return out.toByteArray();
    }

    public byte[] generateReservationBill(Reservation reservation) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // 1. Agregar Logo
        try (InputStream is = getClass().getResourceAsStream("/static/EasyHostal.png")) {
            if (is != null) {
                byte[] imageBytes = is.readAllBytes();
                ImageData logoData = ImageDataFactory.create(imageBytes);
                Image logo = new Image(logoData).scaleToFit(100, 100);
                document.add(logo);
            } else {
            }
        } catch (IOException e) {
        }

        // 2. Título centrado
        Paragraph title = new Paragraph("Factura de Reserva")
                .setBold()
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        document.add(title);

        // 3. Fecha y ID
        document.add(new Paragraph("Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        document.add(new Paragraph("ID de Reserva: " + reservation.getId()));
        document.add(new Paragraph("\n"));

        // 4. Datos de la reserva
        document.add(new Paragraph("Check-in: " + formatDateTime(reservation.getInDate())));
        document.add(new Paragraph("Check-out: " + formatDateTime(reservation.getOutDate())));
        document.add(new Paragraph("Habitación: " + reservation.getRoom().getNumber()));
        document.add(new Paragraph("Tarifa por noche: €" + reservation.getRoom().getBaseRate()));
        document.add(new Paragraph("\n"));

        // 5. Total en negrita
        Paragraph total = new Paragraph("Total: €" + calculateTotal(reservation))
                .setBold()
                .setFontSize(14);
        document.add(total);

        document.close();
        return out.toByteArray();
    }

    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    private double calculateTotal(Reservation reservation) {
        long days = ChronoUnit.DAYS.between(reservation.getInDate(), reservation.getOutDate());
        return Math.max(days, 1) * reservation.getRoom().getBaseRate();
    }

}
