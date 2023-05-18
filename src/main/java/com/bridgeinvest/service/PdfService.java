package com.bridgeinvest.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
@Service
public class PdfService {
    private Logger logger = LoggerFactory.getLogger(PdfService.class);
    public ByteArrayInputStream createPdf(){
        logger.info("Create PDF Start: ");

        String title="Json To Pdf";
        String content="try to convert json data into the pdf";

        ByteArrayOutputStream out =new ByteArrayOutputStream();

        Document document=new Document();

        PdfWriter.getInstance(document,out);

        HeaderFooter footer = new HeaderFooter(true, new Phrase(" page"));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setBorderWidthBottom(0);
        document.setFooter(footer);

        document.open();

        Font titleFont= FontFactory.getFont(FontFactory.HELVETICA_BOLD,40);
        Paragraph titlePara = new Paragraph(title,titleFont);
        titlePara.setAlignment(Element.ALIGN_CENTER);

        document.add(titlePara);

        Font paraFont = FontFactory.getFont(FontFactory.HELVETICA,20);
        Paragraph paragraph= new Paragraph(content);
        paragraph.add(new Chunk("  Wow this text is added after Creating paragraph "));
        document.add(paragraph);


        document.close();
        return new ByteArrayInputStream(out.toByteArray());
        }
    }

