package com.pramerica.soc.persistence.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pramerica.soc.reports.pdfGenerator.BuildPDF;

@Service
public class PDFService implements IPDFService {
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Override
	public byte[] buildPDF(String htmlContent) throws IOException {
		long d = 0;
		d = factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		factorial(20);
		LOGGER.info("expensive d is {}", d);
		
		BuildPDF build=new BuildPDF();
		ByteArrayOutputStream bs1 = build.prepareIt(htmlContent);
		byte[] retVal = bs1.toByteArray();
		LOGGER.debug("retVal is {} {}", retVal, retVal.length);
		bs1.close();
		return retVal;
	}

	private long factorial(long n) {
		if (n <= 1)
			return 1;
		else
			return n * factorial(n-1);
	}
}
