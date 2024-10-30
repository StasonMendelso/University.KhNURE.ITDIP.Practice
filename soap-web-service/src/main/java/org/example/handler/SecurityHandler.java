package org.example.handler;

import jakarta.xml.bind.*;
import jakarta.xml.soap.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.example.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * @author Stanislav Hlova
 */
public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {
    private final Logger logger = LoggerFactory.getLogger(SecurityHandler.class);
    public static final String EXPIRES_AT_NAME = "tokenExpiresAt";
    public static final String CLIENT_SECURITY_TOKEN_NAME = "clientToken";
    public static final String CLIENT_SECURITY_TOKEN_VALUE = "baseTokenValue";
    public static final LocalDateTime EXPIRES_AT_VALUE = LocalDateTime.now().plus(Duration.ofMinutes(5));
    private final JAXBContext jaxb;

    public SecurityHandler() throws JAXBException {
        jaxb = JAXBContext.newInstance("org.example.handler");
    }

    @Override
    public Set<QName> getHeaders() {
        return Set.of();
    }

    @Override
    public boolean handleMessage(SOAPMessageContext soapMessageContext) {
        logger.info("Handling message...");
        boolean outbound = (boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        boolean result = true;
        if (outbound) {
            logger.info("Outbound message");
            result = createSecurityHeader(soapMessageContext.getMessage());
        } else {
            logger.info("Inbound message");
            result = checkSecurityHeader(soapMessageContext.getMessage());
        }
        return result;
    }

    @Override
    public boolean handleFault(SOAPMessageContext soapMessageContext) {
        logger.info("Handling fault...");
        return true;
    }

    @Override
    public void close(MessageContext messageContext) {
        logger.debug("Close");
    }

    private boolean createSecurityHeader(SOAPMessage message) {
        try {
            SOAPHeader header = message.getSOAPHeader();
            header.extractAllHeaderElements();
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty("jaxb.fragment", true);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            String formattedDateTime = EXPIRES_AT_VALUE.format(formatter);
            marshaller.marshal(new JAXBElement<>(new QName(Constants.ORDER_SERVICE_NAMESPACE, EXPIRES_AT_NAME), String.class, formattedDateTime), header);
            message.saveChanges();
            logger.debug("Service token: \n{}", toString(header));
        } catch (SOAPException | JAXBException e) {
            String msg = e.getCause() != null ? e.getMessage() + "\nCause: " + e.getCause().getMessage() : e.getMessage();
            logger.error(msg);
            return false;
        }
        return true;
    }

    private boolean checkSecurityHeader(SOAPMessage message) {
        JAXBElement<String> securityToken;
        try {
            QName clientTokenQName = new QName(Constants.ORDER_SERVICE_NAMESPACE, CLIENT_SECURITY_TOKEN_NAME);
            SOAPHeader header = message.getSOAPPart().getEnvelope().getHeader();
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            Node clientToken = header.getChildElements(clientTokenQName).next();
            securityToken = unmarshaller.unmarshal(clientToken, String.class);
        } catch (Exception e) {
            String faultString = "Client token not found.";
            logger.error(faultString, e);
            throw new SOAPFaultException(createFault(message, faultString));
        }
        if (isValidToken(securityToken.getValue())) {
            return true;
        }
        throw new SOAPFaultException(createFault(message, "Client token is invalid."));
    }

    private boolean isValidToken(String token) {
        if (token == null || token.isEmpty() || !token.equals(CLIENT_SECURITY_TOKEN_VALUE)) {
            logger.error("Client token is incorrect: {}", token);
            return false;
        }
        logger.debug("Client token is correct");
        return true;
    }

    private SOAPFault createFault(SOAPMessage message, String faultString) {
        SOAPFault fault = null;
        try {
            SOAPEnvelope env = message.getSOAPPart().getEnvelope();
            QName faultCode = null;
            String soapProtocol = SOAPConstants.SOAP_1_1_PROTOCOL;
            String code = "Client";
            String prefix = env.lookupPrefix(SOAPConstants.URI_NS_SOAP_1_1_ENVELOPE);
            if (prefix == null) {
                soapProtocol = SOAPConstants.SOAP_1_2_PROTOCOL;
                code = "Sender";
                prefix = env.lookupPrefix(SOAPConstants.URI_NS_SOAP_1_2_ENVELOPE);
            }
            faultCode = env.createQName(code, prefix);
            fault = SOAPFactory.newInstance(soapProtocol).createFault(faultString, faultCode);
        } catch (SOAPException exception) {
            logger.error("Can't create a fault", exception);
        }
        return fault;
    }

    private String toString(Node header) {
        DOMSource source = new DOMSource(header);
        StringWriter stringResult = new StringWriter();
        try {
            TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return stringResult.toString().replaceFirst("<\\?xml.+\\?>", "");
    }
}
