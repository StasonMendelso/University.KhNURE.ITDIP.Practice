package org.example;

import javax.xml.transform.TransformerException;

public class App {
    public static void main(String[] args) throws TransformerException {
        XSLTransform.main(new String[]{"src/main/resources/orders.xsl", "src/main/resources/orders.xml", "src/main/resources/orders.html"});
    }
}
