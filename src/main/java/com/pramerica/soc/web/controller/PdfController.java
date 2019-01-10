package com.pramerica.soc.web.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pramerica.soc.persistence.services.IPDFService;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class PdfController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private IPDFService pdfService;

    private Counter pdfCounter;

    @Autowired
    public PdfController(IPDFService pdfService, MeterRegistry register) {
    	this.pdfService = pdfService;
    	pdfCounter = register.counter("pdf_service");
    }
   
    // PDF Controller

    /**
     * Call the buildPDF service
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/buildPDF", method = RequestMethod.POST)
    public byte[] buildPDF(@RequestBody String htmlContent) throws IOException {
    	pdfCounter.increment();
    	return pdfService.buildPDF(htmlContent);
	}    

}
