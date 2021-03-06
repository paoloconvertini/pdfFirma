package com.paolo.convertini.handler;

import com.paolo.convertini.PdfFirmaMain;
import com.paolo.convertini.model.Configuration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;

public class PdfHandler {

    private final Log log = LogFactory.getLog(PdfHandler.class);

    private final static String FILE_FIRMA = "/firma.png";

    private final static String DA_FIRMARE = "da_firmare";

    private final static String FIRMATI = "firmati/";

    private final static String FIRMATO = "_firmato";

    private final static String PDF_EXTENSION = "pdf";

    private final Configuration cfg;

    public PdfHandler(Configuration cfg) {
        this.cfg = cfg;
    }

    public void firmaBustaPaga() {
        try {
            log.info("Running pdfWrite");
            File folderToCheck = new File(cfg.getDir() + DA_FIRMARE);
            File[] files = folderToCheck.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String fileName = file.getName();
                    if(!StringUtils.equals(PDF_EXTENSION, FilenameUtils.getExtension(fileName))){
                        log.error("Il file " + fileName + " non è di tipo pdf");
                        continue;
                    }
                    PDDocument pdf = PDDocument.load(file);
                    PDPageTree pages = pdf.getDocumentCatalog().getPages();
                    PDPage page = pdf.getPage(pages.getCount() - 1);
                    float upperRightY = page.getCropBox().getUpperRightY();
                    float lowerLeftX = page.getCropBox().getLowerLeftX();
                    String path = PdfFirmaMain.class.getResource(FILE_FIRMA).toURI().getPath();
                    PDImageXObject pdImage = PDImageXObject.createFromFile(path, pdf);
                    PDPageContentStream contentStream = new PDPageContentStream(pdf, page, PDPageContentStream.AppendMode.APPEND, true);
                    contentStream.drawImage(pdImage, (lowerLeftX + cfg.gethPosition()), (upperRightY - cfg.getvPosition()), 70, 20);
                    contentStream.close();
                    String name = StringUtils.substringBefore(fileName, FilenameUtils.EXTENSION_SEPARATOR_STR + PDF_EXTENSION);
                    pdf.save(cfg.getDir() + FIRMATI + name + FIRMATO + FilenameUtils.EXTENSION_SEPARATOR_STR + PDF_EXTENSION);
                    pdf.close();
                    log.info(fileName + " creato correttamente.");
                    if (file.delete()) {
                        log.debug("File temporaneo rimosso");
                    }
                    log.info("Metodo pdfRead terminato correttamente");
                }
            } else {
                log.error("Errore nel controllo pdf");
            }
        } catch (Exception e) {
            log.error("Error creating pdf", e);
        }
    }

}
