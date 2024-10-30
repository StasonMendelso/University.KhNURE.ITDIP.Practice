package hlova.stanislav.shoe_shop;

import hlova.stanislav.shoe_shop.handler.SecurityHandler;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.PortInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislav Hlova
 */
public class ClientHandlerResolver implements HandlerResolver {
    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        List<Handler> list = new ArrayList<>();
        try {
            list.add(new SecurityHandler());
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
