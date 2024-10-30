package hlova.stanislav.shoe_shop.handler;

import hlova.stanislav.shoe_shop.Constants;
import hlova.stanislav.shoe_shop.service.orders.ObjectFactory;
import jakarta.xml.bind.*;
import jakarta.xml.soap.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.Set;

/**
 * @author Stanislav Hlova
 */
public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {
    private static final String SERVER_TOKEN_EXPIRED_AT_NAME = "tokenExpiresAt";
    private final Logger logger = LoggerFactory.getLogger(SecurityHandler.class);
    private static final String CLIENT_TOKEN = "baseTokenValue";
    private final JAXBContext jaxb;
    private final ObjectFactory factory;

    public SecurityHandler() throws JAXBException {
        factory = new ObjectFactory();
        jaxb = JAXBContext.newInstance("hlova.stanislav.shoe_shop");
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        boolean outbound = (boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        boolean result = true;
        if (outbound) {
            logger.info("Outbound message");
            result = createSecurityHeader(context.getMessage());
        } else {
            logger.info("Inbound message");
            logTokenExpireTime(context.getMessage());
        }
        return result;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        logger.info("Client handleFault()");
        try {
            SOAPFault soapFault = context.getMessage().getSOAPBody().getFault();
            logger.debug("Returned FaultCode: {}; FaultString: {}",  soapFault.getFaultCode(), soapFault.getFaultString());
        } catch (SOAPException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public void close(MessageContext context) {
        logger.debug("Client close()");
    }

    @Override
    public Set<QName> getHeaders() {
        return Set.of();
    }

    private boolean createSecurityHeader(SOAPMessage message) {
        try {
            SOAPHeader header = message.getSOAPHeader();
            header.extractAllHeaderElements();
            Marshaller marshaller = jaxb.createMarshaller();
            marshaller.setProperty("jaxb.fragment", true);

            marshaller.marshal(factory.createClientToken(CLIENT_TOKEN), header);
            message.saveChanges();
            logger.debug("Client's token was set into message header: {}", toString(header));
        } catch (SOAPException | JAXBException e) {
            String msg = e.getMessage();
            msg += e.getCause() != null ? "\nCause: " + e.getCause().getMessage() : "";
            logger.error(msg);
            return false;
        }
        return true;
    }

    private void logTokenExpireTime(SOAPMessage message) {
        try {
            QName tokenExpiredAtQName = new QName(Constants.ORDER_SERVICE_NAMESPACE, SERVER_TOKEN_EXPIRED_AT_NAME);
            SOAPHeader header = message.getSOAPHeader();
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            Node tokenExpiredAt = header.getChildElements(tokenExpiredAtQName).next();
            JAXBElement<String> tokenExpiredDate = unmarshaller.unmarshal(tokenExpiredAt, String.class);
            logger.debug("Security token expired date is {}", tokenExpiredDate.getValue());
        } catch (Exception e) {
            logger.error("The message does not contain a service token");
            String msg = e.getMessage() + (e.getCause() != null ? "\nCause: " + e.getCause().getMessage() : "");
            logger.error(msg);
        }
    }

    private String toString(Node header) {
        DOMSource source = new DOMSource(header);
        StringWriter stringResult = new StringWriter();
        try {
            TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));
        } catch (Exception exception) {
            logger.error("Exception during toString method.",exception);
        }
        return stringResult.toString().replaceFirst("<\\?xml.+\\?>", "");
    }
}
