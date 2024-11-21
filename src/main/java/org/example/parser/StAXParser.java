package org.example.parser;

import org.example.Constants;
import org.example.entity.orders.*;
import org.example.entity.shoe.Manufacturer;
import org.example.entity.shoe.Price;
import org.example.entity.shoe.Shoe;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
public class StAXParser {
    private String currentElement;
    private String parentElement;
    private Orders orders;
    private List<Order> orderList;
    private Order order;
    private Buyer buyer;
    private Receiver receiver;
    private Delivery delivery;
    private List<OrderItem> orderItemList;
    private OrderItems orderItems;
    private OrderItem orderItem;
    private Shoe shoe;
    private Manufacturer manufacturer;
    private Price price;
    private Discount discount;
    private Total total;

    public Orders getOrders() {
        return orders;
    }


    public void handleStartElement(StartElement startElement) {
        String localName = startElement.getName().getLocalPart();
        currentElement = localName;
        switch (currentElement) {
            case Constants.Tags.TAG_ORDERS -> {
                orders = new Orders();
                orderList = new ArrayList<>();
            }
            case Constants.Tags.TAG_ORDER -> {
                order = new Order();
                order.setId(BigInteger.valueOf(Long.parseLong(startElement.getAttributeByName(new QName(Constants.Attributes.ORDER_ID_ATTRIBUTE)).getValue())));
                parentElement = Constants.Tags.TAG_ORDER;
            }
            case Constants.Tags.TAG_BUYER -> {
                buyer = new Buyer();
                buyer.setId(BigInteger.valueOf(Long.parseLong(startElement.getAttributeByName(new QName(Constants.Attributes.CONTACT_ID_ATTRIBUTE)).getValue())));
                parentElement = Constants.Tags.TAG_BUYER;
            }
            case Constants.Tags.TAG_RECEIVER -> {
                receiver = new Receiver();
                receiver.setId(BigInteger.valueOf(Long.parseLong(startElement.getAttributeByName(new QName(Constants.Attributes.CONTACT_ID_ATTRIBUTE)).getValue())));
                parentElement = Constants.Tags.TAG_RECEIVER;
            }
            case Constants.Tags.TAG_ORDER_ITEMS -> {
                orderItems = new OrderItems();
                orderItemList = new ArrayList<>();
                parentElement = Constants.Tags.TAG_ORDER_ITEMS;
            }
            case Constants.Tags.TAG_ORDER_ITEM -> {
                orderItem = new OrderItem();
                orderItem.setId(BigInteger.valueOf(Long.parseLong(startElement.getAttributeByName(new QName(Constants.Attributes.CONTACT_ID_ATTRIBUTE)).getValue())));
                parentElement = Constants.Tags.TAG_ORDER_ITEM;
            }
            case Constants.Tags.TAG_SHOE -> {
                shoe = new Shoe();
                shoe.setId(BigInteger.valueOf(Long.parseLong(startElement.getAttributeByName(new QName(Constants.Attributes.CONTACT_ID_ATTRIBUTE)).getValue())));
                parentElement = Constants.Tags.TAG_SHOE;
            }
            case Constants.Tags.TAG_SHOE_MANUFACTURER -> {
                manufacturer = new Manufacturer();
                manufacturer.setId(BigInteger.valueOf(Long.parseLong(startElement.getAttributeByName(new QName(Constants.Attributes.CONTACT_ID_ATTRIBUTE)).getValue())));
                parentElement = Constants.Tags.TAG_SHOE_MANUFACTURER;
            }
            case Constants.Tags.TAG_DISCOUNT -> {
                discount = new Discount();
                discount.setCurrency(startElement.getAttributeByName(new QName(Constants.Attributes.DISCOUNT_CURRENCY_ATTRIBUTE)).getValue());
            }
            case Constants.Tags.TAG_TOTAL -> {
                total = new Total();
                total.setCurrency(startElement.getAttributeByName(new QName(Constants.Attributes.TOTAL_CURRENCY_ATTRIBUTE)).getValue());
            }
            case Constants.Tags.TAG_SHOE_PRICE -> {
                price = new Price();
                price.setCurrency(startElement.getAttributeByName(new QName(Constants.Attributes.PRICE_CURRENCY_ATTRIBUTE)).getValue());
            }
            case Constants.Tags.TAG_DELIVERY -> {
                delivery = new Delivery();
                parentElement = Constants.Tags.TAG_DELIVERY;
            }
        }

    }

    public void handleCharacters(Characters characters) {
        String value = characters.getData().trim();
        if (value.isBlank()) {
            return;
        }
        switch (currentElement) {
            case Constants.Tags.TAG_STATUS -> {
                if (parentElement.equals(Constants.Tags.TAG_ORDER)) {
                    order.setStatus(OrderStatus.fromValue(value));
                }
                if (parentElement.equals(Constants.Tags.TAG_DELIVERY)) {
                    delivery.setStatus(DeliveryStatus.fromValue(value));
                }
            }
            case Constants.Tags.TAG_DELIVERY_SERVICE -> delivery.setDeliveryService(value);
            case Constants.Tags.TAG_DELIVERY_DEPARTMENT_NUMBER -> delivery.setDepartmentNumber(value);
            case Constants.Tags.TAG_ADDRESS -> {
                if (parentElement.equals(Constants.Tags.TAG_DELIVERY)) {
                    delivery.setAddress(value);
                }
                if (parentElement.equals(Constants.Tags.TAG_SHOE_MANUFACTURER)) {
                    manufacturer.setAddress(value);
                }
            }
            case Constants.Tags.TAG_CONTACT_EMAIL -> {
                if (parentElement.equals(Constants.Tags.TAG_RECEIVER)) {
                    receiver.setEmail(value);
                }
                if (parentElement.equals(Constants.Tags.TAG_BUYER)) {
                    buyer.setEmail(value);
                }
            }
            case Constants.Tags.TAG_CONTACT_LAST_NAME -> {
                if (parentElement.equals(Constants.Tags.TAG_RECEIVER)) {
                    receiver.setLastName(value);
                }
                if (parentElement.equals(Constants.Tags.TAG_BUYER)) {
                    buyer.setLastName(value);
                }
            }
            case Constants.Tags.TAG_CONTACT_FIRST_NAME -> {
                if (parentElement.equals(Constants.Tags.TAG_RECEIVER)) {
                    receiver.setFirstName(value);
                }
                if (parentElement.equals(Constants.Tags.TAG_BUYER)) {
                    buyer.setFirstName(value);
                }
            }
            case Constants.Tags.TAG_CONTACT_MIDDLE_NAME -> {
                if (parentElement.equals(Constants.Tags.TAG_RECEIVER)) {
                    receiver.setMiddleName(value);
                }
                if (parentElement.equals(Constants.Tags.TAG_BUYER)) {
                    buyer.setMiddleName(value);
                }
            }
            case Constants.Tags.TAG_CONTACT_TELEPHONE_NUMBER -> {
                if (parentElement.equals(Constants.Tags.TAG_RECEIVER)) {
                    receiver.setTelephoneNumber(value);
                }
                if (parentElement.equals(Constants.Tags.TAG_BUYER)) {
                    buyer.setTelephoneNumber(value);
                }
            }
            case Constants.Tags.TAG_NAME -> {
                if (parentElement.equals(Constants.Tags.TAG_SHOE_MANUFACTURER)) {
                    manufacturer.setName(value);
                }
                if (parentElement.equals(Constants.Tags.TAG_SHOE)) {
                    shoe.setName(value);
                }
            }
            case Constants.Tags.TAG_SHOE_ARTICLE -> shoe.setArticle(value);
            case Constants.Tags.TAG_SHOE_MODEL -> shoe.setModel(value);
            case Constants.Tags.TAG_SHOE_PRODUCT_MATERIAL -> shoe.setProductMaterial(value);
            case Constants.Tags.TAG_SHOE_SIZE -> shoe.setSize(BigDecimal.valueOf(Double.parseDouble(value)));
            case Constants.Tags.TAG_ORDER_ITEM_COUNT -> orderItem.setCount(new Count(Integer.valueOf(value)));
            case Constants.Tags.TAG_SHOE_PRICE -> price.setValue(BigDecimal.valueOf(Double.parseDouble(value)));
            case Constants.Tags.TAG_DISCOUNT ->  discount.setValue(BigDecimal.valueOf(Double.parseDouble(value)));
            case Constants.Tags.TAG_TOTAL ->  total.setValue(BigDecimal.valueOf(Double.parseDouble(value)));

        }
    }

    public void handleEndElement(EndElement endElement) {
        String localName = endElement.getName().getLocalPart();
        switch (localName) {
            case Constants.Tags.TAG_ORDERS -> orders.setOrderList(orderList);
            case Constants.Tags.TAG_ORDER -> orderList.add(order);
            case Constants.Tags.TAG_DELIVERY -> {
                order.setDelivery(delivery);
                parentElement = Constants.Tags.TAG_ORDER;
            }
            case Constants.Tags.TAG_RECEIVER -> order.setReceiver(receiver);
            case Constants.Tags.TAG_BUYER -> order.setBuyer(buyer);
            case Constants.Tags.TAG_TOTAL -> orderItem.setTotal(total);
            case Constants.Tags.TAG_DISCOUNT -> orderItem.setDiscount(discount);
            case Constants.Tags.TAG_SHOE_PRICE -> shoe.setPrice(price);
            case Constants.Tags.TAG_ORDER_ITEM -> orderItemList.add(orderItem);
            case Constants.Tags.TAG_ORDER_ITEMS -> {
                orderItems.setOrderItem(orderItemList);
                order.setOrderItems(orderItems);
            }
            case Constants.Tags.TAG_SHOE_MANUFACTURER -> {
                shoe.setManufacturer(manufacturer);
                parentElement = Constants.Tags.TAG_SHOE;
            }
            case Constants.Tags.TAG_SHOE -> orderItem.setShoe(shoe);
        }
    }

    public Orders parseOrders(String xmlFilePath, String xsdFilePath) throws SAXException, ParserConfigurationException, IOException, XMLStreamException {

        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdFilePath));

        Validator validator = schema.newValidator();
        validator.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) {
                System.err.println("Warning: " + exception.getMessage());
            }

            @Override
            public void error(SAXParseException exception) {
                logValidationError(exception);
                throw new RuntimeException(exception);
            }

            @Override
            public void fatalError(SAXParseException exception) {
                logValidationError(exception);
                throw new RuntimeException(exception);
            }

            private void logValidationError(SAXParseException exception) {
                System.err.println("====================================");
                System.err.println(xmlFilePath + " is NOT valid against " + xsdFilePath + ":\n" + exception.getMessage());
                System.err.println("====================================");

            }
        });
        validator.validate(new StreamSource(new File(xmlFilePath)));

        XMLInputFactory xmlInputFactory  = XMLInputFactory.newInstance();

        xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
        xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);

        XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xmlFilePath));

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement()) {
                handleStartElement(event.asStartElement());
            } else if (event.isEndElement()) {
                handleEndElement(event.asEndElement());
            } else if (event.isCharacters()) {
                handleCharacters(event.asCharacters());
            }
        }

        return orders;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {
        StAXParser saxParser = new StAXParser();
        System.out.println("--== StAX Parser ==--");
        Orders orders = saxParser.parseOrders(Constants.ORDERS_XML_FILE, Constants.ORDERS_XSD_FILE);
        System.out.println("====================================");
        System.out.println("Here is the orders: \n" + orders);
        System.out.println("====================================");
        saxParser.parseOrders(Constants.ORDERS_NON_VALID_XML_FILE, Constants.ORDERS_XSD_FILE);

    }
}
