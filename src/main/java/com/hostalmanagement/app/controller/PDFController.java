package com.hostalmanagement.app.controller;

import com.hostalmanagement.app.service.PDFReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class PDFController {

    @Autowired
    private PDFReportService pdfService;   


}
