package com.hostalmanagement.app.service;

import com.hostalmanagement.app.DTO.RoomDTO;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.element.Cell;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
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
}
