package com.pramerica.soc.persistence.services;

import java.io.IOException;

public interface IPDFService {
	
	public byte[] buildPDF(String htmlContent) throws IOException;

}
