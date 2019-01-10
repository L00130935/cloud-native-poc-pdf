package com.pramerica.soc.reports.pdfGenerator;

import com.github.neoflyingsaucer.extend.controller.error.FSError.FSErrorLevel;
import com.github.neoflyingsaucer.extend.controller.error.FSError;
import com.github.neoflyingsaucer.extend.controller.error.FSErrorController;
import com.github.neoflyingsaucer.extend.controller.error.LangId;
import com.github.neoflyingsaucer.extend.output.FSImage;
import com.github.neoflyingsaucer.extend.output.ImageResolver;
import com.github.neoflyingsaucer.extend.output.ReplacedElement;
import com.github.neoflyingsaucer.extend.output.ReplacedElementResolver;
import com.github.neoflyingsaucer.extend.useragent.ImageResourceI;
import com.github.neoflyingsaucer.extend.useragent.Optional;
import com.github.neoflyingsaucer.extend.useragent.ResourceCache;
import com.github.neoflyingsaucer.extend.useragent.UserAgentCallback;

import java.awt.Point;

import org.w3c.dom.Element;

import com.github.neoflyingsaucer.pdf2dout.Pdf2Image;
import com.github.neoflyingsaucer.pdf2dout.Pdf2ReplacedElementResolver;

public class UserReplaceElement
  extends  Pdf2ReplacedElementResolver
{
  public ReplacedElement createReplacedElement(Element e, String baseUri, UserAgentCallback uac, ImageResolver imgResolver, float cssWidth, float cssHeight)
  {
	  
    if ("img".equals(e.getTagName()))
    {
      return replaceImage(uac, baseUri, e, imgResolver, (int)cssWidth, (int)cssHeight);
    }

    return null;
  }

  protected ReplacedElement replaceImage(UserAgentCallback uac, String baseUri, Element elem, ImageResolver imgResolver, int cssWidth, int cssHeight)
  {
    String imageSrc = elem.getAttribute("src");
    
    if (imageSrc.isEmpty())
    {
      FSErrorController.log(UserReplaceElement.class, FSError.FSErrorLevel.ERROR, LangId.NO_IMAGE_SRC_PROVIDED, new Object[0]);
      return new Pdf2ImageReplacedElement(null, cssWidth, cssHeight);
    }

    Optional ruri = uac.resolveURI(baseUri, imageSrc);
    
    if (!ruri.isPresent())
    {
      return new Pdf2ImageReplacedElement(null, cssWidth, cssHeight);
    }

    Optional fsImage = uac.getResourceCache().getImage((String)ruri.get(), Pdf2Image.class);

    
    if (fsImage.isPresent()) {
      return new Pdf2ImageReplacedElement((FSImage)fsImage.get(), cssWidth, cssHeight);
    }

    Optional img = uac.getImageResource((String)ruri.get());

    
    if (img.isPresent())
    {
      FSImage image = imgResolver.resolveImage((String)ruri.get(), ((ImageResourceI)img.get()).getImage());
      
      
      uac.getResourceCache().putImage((String)ruri.get(), Pdf2Image.class, image);
      
      
      if(elem.getAttribute("width") != null )
    	  cssWidth = Integer.parseInt(elem.getAttribute("width"));
      if(elem.getAttribute("height") != null )
    	  cssHeight = Integer.parseInt(elem.getAttribute("height"));
     
     
      return new Pdf2ImageReplacedElement(image, cssWidth, cssHeight);
    }

    return new Pdf2ImageReplacedElement(null, cssWidth, cssHeight);
  }

  public void reset()
  {
  }

  public static class Pdf2ImageReplacedElement
    implements ReplacedElement
  {
    private final FSImage img;
    private Point location = new Point(0, 0);

    public Pdf2ImageReplacedElement(FSImage img, int cssWidth, int cssHeight)
    {
      this.img = img;
    }

    public int getIntrinsicWidth()
    {
      if (this.img == null) {
        return 0;
      }
      return this.img.getWidth();
    }

    public int getIntrinsicHeight()
    {
      if (this.img == null) {
        return 0;
      }
      return this.img.getHeight();
    }

    public Point getLocation()
    {
      return this.location;
    }

    public void setLocation(int x, int y)
    {
      this.location = new Point(x, y);
    }

    public void detach()
    {
    }

    public boolean hasBaseline()
    {
      return false;
    }

    public int getBaseline()
    {
      return 0;
    }

    public FSImage getImage()
    {
      return this.img;
    }
  }
}