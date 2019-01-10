package com.pramerica.soc.reports.pdfGenerator;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.xhtmlrenderer.renderers.PagedRenderer;

import com.github.neoflyingsaucer.defaultuseragent.DefaultUserAgent;
import com.github.neoflyingsaucer.extend.output.DisplayList;
import com.github.neoflyingsaucer.pdf2dout.Pdf2FontContext;
import com.github.neoflyingsaucer.pdf2dout.Pdf2FontResolver;
import com.github.neoflyingsaucer.pdf2dout.Pdf2ImageResolver;
import com.github.neoflyingsaucer.pdf2dout.Pdf2Out;
import com.github.neoflyingsaucer.pdf2dout.Pdf2ReplacedElementResolver;
import com.github.neoflyingsaucer.pdf2dout.Pdf2Out.PdfOutMode;
public class BuildPDF {

		
	    private static final float PDF_DEFAULT_DOTS_PER_POINT = 20f * 4f / 3f;
	    private static final int PDF_DEFAULT_DOTS_PER_PIXEL = 20;
	    final  ByteArrayOutputStream bs = new ByteArrayOutputStream();
		
		public ByteArrayOutputStream prepareIt(String html)
		
		{
			PagedRenderer r3 = new PagedRenderer(new DefaultUserAgent(), PDF_DEFAULT_DOTS_PER_POINT * 72f, PDF_DEFAULT_DOTS_PER_PIXEL);
			
	    	Pdf2Out out = new Pdf2Out(PDF_DEFAULT_DOTS_PER_POINT, PdfOutMode.TEST_MODE);
	    	
	    	try {
				out.initializePdf(bs);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	    	
	    	r3.setDocumentHtml(html);
	    	r3.setImageResolver(new Pdf2ImageResolver(PDF_DEFAULT_DOTS_PER_PIXEL));
	    	r3.setFontContext(new Pdf2FontContext());
	    	r3.setFontResolver(new Pdf2FontResolver(out.getDocument()));
	    	r3.setReplacedElementResolver(new UserReplaceElement());
	    	r3.prepare();
	    	
	    	out.setDocumentInformationDictionary(r3.getHtmlMetadata());
	    	
	    	for (int i = 0; i < r3.getPageCount(); i++)
	    	{
	    		DisplayList dl = r3.renderToList(i);
	    		int height = r3.getPageHeight(i);
	    		int width = r3.getPageWidth(i);
	    	
	    		out.initializePage(width, height);
	    		out.render(dl);
	    		out.finishPage();
	    	}

	    	out.finish();
	    	
	    	return bs;
		}
		
		public String getUnencoded()
		{
			String unencoded;
			try {
				unencoded = new String(bs.toByteArray(), "windows-1252");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				unencoded = "";
			}	
			
			return unencoded;
		}
		
		public void printPDFFailure()
		{
			String unencoded = getUnencoded();
			
			System.err.println("GOT:");
			System.err.println(unencoded);
		}
		
		public boolean assertContains(String content)
		{
			String unencoded = getUnencoded();
			
			if (unencoded.contains(content))
				return true;
			
			printPDFFailure();
			
			throw new RuntimeException("PDF doesn't contain bytes");
		}

	}
	
	
	
	

