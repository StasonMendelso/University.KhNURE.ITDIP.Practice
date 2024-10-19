package org.example.parser;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.example.Constants;
import org.example.entity.orders.Orders;
import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

import static org.example.Constants.ORDERS_NAMESPACE_URI;
import static org.example.Constants.SHOE_NAMESPACE_URI;

public class JAXBParser {
    public Orders unmarshallOrders(String xmlFilePath, String xsdFilePath, Class<?> objectFactory) throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectFactory);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        if (xsdFilePath != null) {
            Schema schema = schemaFactory.newSchema(new File(xsdFilePath));
            unmarshaller.setSchema(schema);
        }

        unmarshaller.setEventHandler(event -> {
            System.err.println("====================================");
            System.err.println(xmlFilePath + " is NOT valid against " + xsdFilePath + ":\n" + event.getMessage());
            System.err.println("====================================");
            return false;
        });

        return (Orders) unmarshaller.unmarshal(new File(xmlFilePath));
    }

    public static void main(String[] args) throws JAXBException, SAXException {
        JAXBParser jaxbParser = new JAXBParser();
        System.out.println("--== JAXB Parser ==--");
        Orders orders = jaxbParser.unmarshallOrders(Constants.ORDERS_XML_FILE, Constants.ORDERS_XSD_FILE, Constants.ORDERS_OBJECT_FACTORY_CLASS);
        System.out.println("====================================");
        System.out.println("Here is the orders: \n" + orders);
        System.out.println("====================================");
        // jaxbParser.unmarshallOrders(Constants.ORDERS_NON_VALID_XML_FILE, Constants.ORDERS_XSD_FILE, Constants.ORDERS_OBJECT_FACTORY_CLASS);
        jaxbParser.saveOrdersToXml(orders, Constants.RESULT_ORDERS_XML_FILE, Constants.ORDERS_XSD_FILE);
    }

    public void saveOrdersToXml(Orders orders, String outputPath, String xsdFilePath) throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Orders.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdFilePath));
        marshaller.setSchema(schema);

        marshaller.setProperty("org.glassfish.jaxb.namespacePrefixMapper", new CustomNamespacePrefixMapper());
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.example.com/orders orders.xsd");
        marshaller.marshal(orders, new File(outputPath));

        System.out.println("Orders have been saved to XML file using JAXB.");
    }

    public class CustomNamespacePrefixMapper extends NamespacePrefixMapper {
        @Override
        public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
            if (ORDERS_NAMESPACE_URI.equals(namespaceUri)) {
                return "tns";
            } else if (SHOE_NAMESPACE_URI.equals(namespaceUri)) {
                return "sh";
            }
            return suggestion;
        }
        @Override
        public String[] getPreDeclaredNamespaceUris() {
            return new String[]{ORDERS_NAMESPACE_URI, SHOE_NAMESPACE_URI};
        }
    }
}
