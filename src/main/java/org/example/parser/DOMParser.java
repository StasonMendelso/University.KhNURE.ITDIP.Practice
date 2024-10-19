package org.example.parser;

import org.example.Constants;
import org.example.entity.orders.*;
import org.example.entity.shoe.Manufacturer;
import org.example.entity.shoe.Price;
import org.example.entity.shoe.Shoe;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class DOMParser {

    public Orders unmarshallOrders(String xmlFilePath, String xsdFilePath) throws SAXException, ParserConfigurationException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdFilePath));

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        // to be compliant, completely disable DOCTYPE declaration:
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        // or completely disable external entities declarations:
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        // make parser validating not against XSD, which internally referenced in XML doc
        // !!! OLD VULNERABLE FEATURE !!!
        documentBuilderFactory.setFeature("http://xml.org/sax/features/validation", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/validation/schema", false);
        // set the validation against schema
        documentBuilderFactory.setSchema(schema);

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        // setup validation error handler
        // the preferred way is the throwing an exception
        documentBuilder.setErrorHandler(new DefaultHandler() {
            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.err.println("====================================");
                System.err.println(xmlFilePath + " is NOT valid against " + xsdFilePath + ":\n" + exception.getMessage());
                System.err.println("====================================");
                throw exception;
            }
        });
        // get the top of the xml tree
        Document document = documentBuilder.parse(new FileInputStream(xmlFilePath));
        Orders orders = new Orders();
        List<Order> orderList = new ArrayList<>();
        // get the document element of the xml document
        Element root = document.getDocumentElement();
        NodeList xmlOrders = root.getElementsByTagNameNS(Constants.ORDERS_NAMESPACE_URI, Constants.Tags.TAG_ORDER);
        for (int i = 0; i < xmlOrders.getLength(); i++) {
            orderList.add(parseOrder((Element) xmlOrders.item(i)));
        }
        orders.setOrderList(orderList);
        return orders;
    }

    private Order parseOrder(Element orderElement) throws SAXException {
        Order order = new Order();
        order.setId(BigInteger.valueOf(Long.parseLong(orderElement.getAttribute(Constants.Attributes.ORDER_ID_ATTRIBUTE))));

        NodeList childrenNodeList = orderElement.getChildNodes();
        for (int i = 0; i < childrenNodeList.getLength(); i++) {
            Node childNode = childrenNodeList.item(i);
            if (childNode.getLocalName() == null) {
                continue;
            }
            switch (childNode.getLocalName()) {
                case Constants.Tags.TAG_ORDER_STATUS ->
                        order.setStatus(OrderStatus.fromValue(childNode.getTextContent()));
                case Constants.Tags.TAG_DELIVERY -> order.setDelivery(parseDelivery(childNode));
                case Constants.Tags.TAG_RECEIVER -> order.setReceiver(parseReceiver(childNode));
                case Constants.Tags.TAG_BUYER -> order.setBuyer(parseBuyer(childNode));
                case Constants.Tags.TAG_ORDER_ITEMS -> order.setOrderItems(parseOrderItems(childNode));
                default -> throw new SAXException("Couldn't find unknown element:" + childNode.getLocalName());
            }
        }
        return order;
    }

    private Receiver parseReceiver(Node receiverNode) throws SAXException {
        Receiver receiver = parseContact(receiverNode, Receiver.class);
        receiver.setId(BigInteger.valueOf(Long.parseLong(receiverNode.getAttributes().getNamedItem(Constants.Attributes.CONTACT_ID_ATTRIBUTE).getTextContent())));
        return receiver;
    }

    private Buyer parseBuyer(Node buyerNode) throws SAXException {
        Buyer buyer = parseContact(buyerNode, Buyer.class);
        buyer.setId(BigInteger.valueOf(Long.parseLong(buyerNode.getAttributes().getNamedItem(Constants.Attributes.CONTACT_ID_ATTRIBUTE).getTextContent())));
        return buyer;
    }


    private <T extends Contact> T parseContact(Node contactNode, Class<T> clazz) throws SAXException {
        T contact;
        try {
            contact = clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        NodeList childNodes = contactNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getLocalName() == null) {
                continue;
            }
            switch (childNode.getLocalName()) {
                case Constants.Tags.TAG_CONTACT_EMAIL -> contact.setEmail(childNode.getTextContent());
                case Constants.Tags.TAG_CONTACT_LAST_NAME -> contact.setLastName(childNode.getTextContent());
                case Constants.Tags.TAG_CONTACT_FIRST_NAME -> contact.setFirstName(childNode.getTextContent());
                case Constants.Tags.TAG_CONTACT_MIDDLE_NAME -> contact.setMiddleName(childNode.getTextContent());
                case Constants.Tags.TAG_CONTACT_TELEPHONE_NUMBER ->
                        contact.setTelephoneNumber(childNode.getTextContent());
                default -> throw new SAXException("Couldn't find unknown element:" + childNode.getLocalName());

            }
        }


        return contact;
    }

    private Delivery parseDelivery(Node deliveryNode) throws SAXException {
        Delivery delivery = new Delivery();

        NodeList childNodes = deliveryNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getLocalName() == null) {
                continue;
            }
            switch (childNode.getLocalName()) {
                case Constants.Tags.TAG_DELIVERY_STATUS ->
                        delivery.setStatus(DeliveryStatus.fromValue(childNode.getTextContent()));
                case Constants.Tags.TAG_DELIVERY_SERVICE -> delivery.setDeliveryService(childNode.getTextContent());
                case Constants.Tags.TAG_DELIVERY_ADDRESS -> delivery.setAddress(childNode.getTextContent());
                case Constants.Tags.TAG_DELIVERY_DEPARTMENT_NUMBER ->
                        delivery.setDepartmentNumber(childNode.getTextContent());
                default -> throw new SAXException("Couldn't find unknown element:" + childNode.getLocalName());

            }
        }

        return delivery;
    }

    private OrderItems parseOrderItems(Node orderItemsNode) throws SAXException {
        OrderItems orderItems = new OrderItems();
        List<OrderItem> orderItemList = new ArrayList<>();

        NodeList childNodes = orderItemsNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getLocalName() == null) {
                continue;
            }
            orderItemList.add(parseOrderItem(childNode));
        }

        orderItems.setOrderItem(orderItemList);
        return orderItems;
    }

    private OrderItem parseOrderItem(Node orderItemNode) throws SAXException {
        OrderItem orderItem = new OrderItem();
        NodeList childNodes = orderItemNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getLocalName() == null) {
                continue;
            }
            switch (childNode.getLocalName()) {
                case Constants.Tags.TAG_COUNT ->
                        orderItem.setCount(new Count(Long.parseLong(childNode.getTextContent())));
                case Constants.Tags.TAG_DISCOUNT ->
                        orderItem.setDiscount(new Discount(BigDecimal.valueOf(Double.parseDouble(childNode.getTextContent())).setScale(2),
                                childNode.getAttributes().getNamedItem(Constants.Attributes.DISCOUNT_CURRENCY_ATTRIBUTE).getTextContent()));
                case Constants.Tags.TAG_TOTAL ->
                        orderItem.setTotal(new Total(BigDecimal.valueOf(Double.parseDouble(childNode.getTextContent())).setScale(2),
                                childNode.getAttributes().getNamedItem(Constants.Attributes.TOTAL_CURRENCY_ATTRIBUTE).getTextContent()));
                case Constants.Tags.TAG_SHOE -> orderItem.setShoe(parseShoe(childNode));
                default -> throw new SAXException("Couldn't find unknown element:" + childNode.getLocalName());
            }
        }
        orderItem.setId(BigInteger.valueOf(Long.parseLong(orderItemNode.getAttributes().getNamedItem(Constants.Attributes.ORDER_ITEM_ID_ATTRIBUTE).getTextContent())));
        return orderItem;
    }

    private Shoe parseShoe(Node shoeNode) throws SAXException {
        Shoe shoe = new Shoe();
        NodeList childNodes = shoeNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getLocalName() == null) {
                continue;
            }
            switch (childNode.getLocalName()) {
                case Constants.Tags.TAG_SHOE_NAME -> shoe.setName(childNode.getTextContent());
                case Constants.Tags.TAG_SHOE_PRODUCT_MATERIAL -> shoe.setProductMaterial(childNode.getTextContent());
                case Constants.Tags.TAG_SHOE_ARTICLE -> shoe.setArticle(childNode.getTextContent());
                case Constants.Tags.TAG_SHOE_MODEL -> shoe.setModel(childNode.getTextContent());
                case Constants.Tags.TAG_SHOE_SIZE ->
                        shoe.setSize(BigDecimal.valueOf(Double.parseDouble(childNode.getTextContent())));
                case Constants.Tags.TAG_SHOE_PRICE ->
                        shoe.setPrice(new Price(BigDecimal.valueOf(Double.parseDouble(childNode.getTextContent())).setScale(2),
                                childNode.getAttributes().getNamedItem(Constants.Attributes.PRICE_CURRENCY_ATTRIBUTE).getTextContent()));
                case Constants.Tags.TAG_SHOE_MANUFACTURER -> shoe.setManufacturer(parseManufacturer(childNode));
                default -> throw new SAXException("Couldn't find unknown element:" + childNode.getLocalName());
            }
        }
        shoe.setId(BigInteger.valueOf(Long.parseLong(shoeNode.getAttributes().getNamedItem(Constants.Attributes.SHOE_ID_ATTRIBUTE).getTextContent())));
        return shoe;
    }

    private Manufacturer parseManufacturer(Node manufacturerNode) throws SAXException {
        Manufacturer manufacturer = new Manufacturer();
        NodeList childNodes = manufacturerNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getLocalName() == null) {
                continue;
            }
            switch (childNode.getLocalName()) {
                case Constants.Tags.TAG_MANUFACTURER_NAME -> manufacturer.setName(childNode.getTextContent());
                case Constants.Tags.TAG_MANUFACTURER_ADDRESS -> manufacturer.setAddress(childNode.getTextContent());
                default -> throw new SAXException("Couldn't find unknown element:" + childNode.getLocalName());
            }
        }
        manufacturer.setId(BigInteger.valueOf(Long.parseLong(manufacturerNode.getAttributes().getNamedItem(Constants.Attributes.MANUFACTURER_ID_ATTRIBUTE).getTextContent())));
        return manufacturer;
    }


    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DOMParser domParser = new DOMParser();
        System.out.println("--== DOM Parser ==--");
        Orders orders = domParser.unmarshallOrders(Constants.ORDERS_XML_FILE, Constants.ORDERS_XSD_FILE);
        System.out.println("====================================");
        System.out.println("Here is the orders: \n" + orders);
        System.out.println("====================================");
//        domParser.unmarshallOrders(Constants.ORDERS_NON_VALID_XML_FILE, Constants.ORDERS_XSD_FILE);
        domParser.saveOrdersToXml(orders, Constants.RESULT_ORDERS_XML_FILE, Constants.ORDERS_XSD_FILE);
    }

    public void saveOrdersToXml(Orders orders, String outputPath, String xsdFilePath) throws ParserConfigurationException, TransformerException, SAXException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdFilePath));
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        dbFactory.setSchema(schema);

        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElementNS(Constants.ORDERS_NAMESPACE_URI, "tns:orders");
        rootElement.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:tns", Constants.ORDERS_NAMESPACE_URI);
        rootElement.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:sh", Constants.SHOE_NAMESPACE_URI);
        rootElement.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:schemaLocation", "http://stanislav.hlova/shoe-shop/orders orders.xsd");
        doc.appendChild(rootElement);

        for (Order order : orders.getOrder()) {
            Element orderElement = doc.createElementNS(Constants.ORDERS_NAMESPACE_URI, Constants.Tags.TAG_ORDER);
            orderElement.setAttribute(Constants.Attributes.ORDER_ID_ATTRIBUTE, String.valueOf(order.getId()));


            if (order.getOrderItems() != null) {
                appendOrderItems(doc, orderElement, order.getOrderItems());
            }
            if (order.getBuyer() != null) {
                appendBuyer(doc, orderElement, order.getBuyer());
            }
            if (order.getReceiver() != null) {
                appendReceiver(doc, orderElement, order.getReceiver());
            }
            if (order.getDelivery() != null) {
                appendDelivery(doc, orderElement, order.getDelivery());
            }

            appendTextElement(doc, orderElement, Constants.Tags.TAG_ORDER_STATUS, order.getStatus().value());
            rootElement.appendChild(orderElement);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(outputPath));
        transformer.transform(source, result);

        System.out.println("Orders have been saved to XML file.");
    }

    private void appendBuyer(Document doc, Element orderElement, Buyer buyer) {
        Element contactElement = createContactElement(doc, buyer, Constants.Tags.TAG_BUYER);
        contactElement.setAttribute(Constants.Attributes.CONTACT_ID_ATTRIBUTE, String.valueOf(buyer.getId()));
        orderElement.appendChild(contactElement);
    }
    private void appendReceiver(Document doc, Element orderElement, Receiver receiver) {
        Element contactElement = createContactElement(doc, receiver, Constants.Tags.TAG_RECEIVER);
        contactElement.setAttribute(Constants.Attributes.CONTACT_ID_ATTRIBUTE, String.valueOf(receiver.getId()));
        orderElement.appendChild(contactElement);
    }

    private void appendTextElement(Document doc, Element parent, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        parent.appendChild(element);
    }

    private void appendDelivery(Document doc, Element parent, Delivery delivery) {
        Element deliveryElement = doc.createElementNS(Constants.ORDERS_NAMESPACE_URI, Constants.Tags.TAG_DELIVERY);

        appendTextElement(doc, deliveryElement, Constants.Tags.TAG_DELIVERY_STATUS, delivery.getStatus().value());
        appendTextElement(doc, deliveryElement, Constants.Tags.TAG_DELIVERY_SERVICE, delivery.getDeliveryService());
        appendTextElement(doc, deliveryElement, Constants.Tags.TAG_DELIVERY_ADDRESS, delivery.getAddress());
        appendTextElement(doc, deliveryElement, Constants.Tags.TAG_DELIVERY_DEPARTMENT_NUMBER, delivery.getDepartmentNumber());

        parent.appendChild(deliveryElement);
    }

    private Element createContactElement(Document doc, Contact contact, String tagName) {
        Element contactElement = doc.createElementNS(Constants.ORDERS_NAMESPACE_URI, tagName);

        appendTextElement(doc, contactElement, Constants.Tags.TAG_CONTACT_EMAIL, contact.getEmail());
        appendTextElement(doc, contactElement, Constants.Tags.TAG_CONTACT_LAST_NAME, contact.getLastName());
        appendTextElement(doc, contactElement, Constants.Tags.TAG_CONTACT_FIRST_NAME, contact.getFirstName());
        appendTextElement(doc, contactElement, Constants.Tags.TAG_CONTACT_MIDDLE_NAME, contact.getMiddleName());
        appendTextElement(doc, contactElement, Constants.Tags.TAG_CONTACT_TELEPHONE_NUMBER, contact.getTelephoneNumber());
        return contactElement;
    }

    private void appendOrderItems(Document doc, Element parent, OrderItems orderItems) {
        Element orderItemsElement = doc.createElementNS(Constants.ORDERS_NAMESPACE_URI, Constants.Tags.TAG_ORDER_ITEMS);

        for (OrderItem orderItem : orderItems.getOrderItem()) {
            Element orderItemElement = doc.createElementNS(Constants.ORDERS_NAMESPACE_URI, Constants.Tags.TAG_ORDER_ITEM);
            orderItemElement.setAttribute(Constants.Attributes.ORDER_ITEM_ID_ATTRIBUTE, String.valueOf(orderItem.getId()));

            if (orderItem.getShoe() != null) {
                appendShoe(doc, orderItemElement, orderItem.getShoe());
            }
            appendTextElement(doc, orderItemElement, Constants.Tags.TAG_COUNT, String.valueOf(orderItem.getCount().getValue()));

            if (orderItem.getDiscount() != null) {
                Element discountElement = doc.createElementNS(Constants.ORDERS_NAMESPACE_URI, Constants.Tags.TAG_DISCOUNT);
                discountElement.setAttribute(Constants.Attributes.DISCOUNT_CURRENCY_ATTRIBUTE, orderItem.getDiscount().getCurrency());
                discountElement.setTextContent(orderItem.getDiscount().getValue().toString());
                orderItemElement.appendChild(discountElement);
            }

            if (orderItem.getTotal() != null) {
                Element totalElement = doc.createElementNS(Constants.ORDERS_NAMESPACE_URI, Constants.Tags.TAG_TOTAL);
                totalElement.setAttribute(Constants.Attributes.TOTAL_CURRENCY_ATTRIBUTE, orderItem.getTotal().getCurrency());
                totalElement.setTextContent(orderItem.getTotal().getValue().toString());
                orderItemElement.appendChild(totalElement);
            }


            orderItemsElement.appendChild(orderItemElement);
        }

        parent.appendChild(orderItemsElement);
    }

    private void appendShoe(Document doc, Element parent, Shoe shoe) {
        Element shoeElement = doc.createElement( "sh:shoe");
        shoeElement.setAttribute(Constants.Attributes.SHOE_ID_ATTRIBUTE, String.valueOf(shoe.getId()));
        if (shoe.getManufacturer() != null) {
            appendManufacturer(doc, shoeElement, shoe.getManufacturer());
        }
        appendTextElement(doc, shoeElement, "sh:name", shoe.getName());
        appendTextElement(doc, shoeElement, "sh:size", shoe.getSize().toString());
        appendTextElement(doc, shoeElement, "sh:productMaterial", shoe.getProductMaterial());
        appendTextElement(doc, shoeElement, "sh:model", shoe.getModel());
        appendTextElement(doc, shoeElement, "sh:article", shoe.getArticle());

        if (shoe.getPrice() != null) {
            Element priceElement = doc.createElementNS(Constants.SHOE_NAMESPACE_URI, "sh:price");
            priceElement.setAttribute(Constants.Attributes.PRICE_CURRENCY_ATTRIBUTE, shoe.getPrice().getCurrency());
            priceElement.setTextContent(shoe.getPrice().getValue().toString());
            shoeElement.appendChild(priceElement);
        }
        parent.appendChild(shoeElement);
    }

    private void appendManufacturer(Document doc, Element parent, Manufacturer manufacturer) {
        Element manufacturerElement = doc.createElement("sh:manufacturer");
        manufacturerElement.setAttribute(Constants.Attributes.MANUFACTURER_ID_ATTRIBUTE, String.valueOf(manufacturer.getId()));

        appendTextElement(doc, manufacturerElement, "sh:name", manufacturer.getName());
        appendTextElement(doc, manufacturerElement, "sh:address", manufacturer.getAddress());


        parent.appendChild(manufacturerElement);
    }


}
