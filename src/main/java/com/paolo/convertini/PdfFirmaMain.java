package com.paolo.convertini;


import com.paolo.convertini.model.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PdfFirmaMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("pdffirma-spring.xml");
        Configuration configuration = (Configuration) context.getBean("configuration");
        BatchPdfFirma batchPdfFirma = new BatchPdfFirma(configuration);
        batchPdfFirma.execute();
    }
}
