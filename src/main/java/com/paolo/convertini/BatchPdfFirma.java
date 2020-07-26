package com.paolo.convertini;

import com.paolo.convertini.handler.PdfHandler;
import com.paolo.convertini.model.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Classe batch
 *
 * @author Paolo Convertini
 */
public class BatchPdfFirma implements ApplicationContextAware {

    private final Log log = LogFactory.getLog(BatchPdfFirma.class);

    private final PdfHandler pdfHandler;

    private final String metodo;

    public BatchPdfFirma(Configuration configuration) {
        this.metodo = configuration.getMetodo();
        pdfHandler = new PdfHandler(configuration);
    }

    public void execute() {

        long startBatch = System.currentTimeMillis();
        try {
            log.debug("Inizio esecuzione pdfFirma");
            if(StringUtils.equals(metodo, "bustaPaga")){
                pdfHandler.firmaBustaPaga();
            }
        } catch (Exception e) {
            log.error("Errore pdfFirma ", e);
        } finally {
            long endBatch = System.currentTimeMillis();
            log.info("TERMINE ESECUZIONE IN " + ((endBatch - startBatch) / 1000) + " sec");
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

}
