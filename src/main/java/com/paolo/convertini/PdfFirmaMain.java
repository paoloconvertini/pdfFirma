package com.paolo.convertini;


import com.paolo.convertini.model.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class PdfFirmaMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("pdffirma-spring.xml");
        Configuration configuration = (Configuration) context.getBean("configuration");
        JFrame frame = new JFrame("Firma pdf");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,400);//400 width and 500 height
        JButton btnFirma = new JButton("Firma");
        btnFirma.setBounds(420,150,100, 40);//x axis, y axis, width, height
        frame.add(btnFirma);//adding button in JFrame
        JButton btnSave = new JButton("Salva");
        btnSave.setBounds(280,150,100, 40);//x axis, y axis, width, height

        frame.add(btnSave);//adding button in JFrame
        JLabel lbMetodo = new JLabel("Metodo da eseguire");
        lbMetodo.setBounds(50,50, 180,30);
        JLabel lbMetodoHelper =
                new JLabel("Inserire \"bp\" per firma busta paga. " +
                        "Inserire \"as\" per firma documento assunzione");
        lbMetodoHelper.setBounds(50,60, 600,60);
        Font font = lbMetodoHelper.getFont();
        lbMetodoHelper.setFont(new Font(font.getName(), Font.ITALIC, font.getSize()));
        JTextField txtMetodo = new JTextField();
        txtMetodo.setText(configuration.getMetodo());
        txtMetodo.setBounds(190, 50, 40, 30);
        btnSave.addActionListener(e-> {
            String text = txtMetodo.getText();
            configuration.setMetodo(text);
        });
        frame.add(txtMetodo);
        frame.add(lbMetodo);
        frame.add(lbMetodoHelper);
        frame.setLayout(null);//using no layout managers
        frame.setVisible(true);
        btnFirma.addActionListener(e -> {
            BatchPdfFirma batchPdfFirma = new BatchPdfFirma(configuration);
            batchPdfFirma.execute();
        });
    }
}
