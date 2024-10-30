package org.example.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.Holder;
import org.example.entity.orders.Order;
import org.example.exception.ServiceException;

import java.math.BigInteger;
import java.util.List;

import static org.example.Constants.ORDERS_NAMESPACE_URI;
import static org.example.Constants.ORDER_SERVICE_NAMESPACE;

@WebService(targetNamespace = ORDER_SERVICE_NAMESPACE)
public interface OrderService {

    @WebMethod()
    @WebResult(targetNamespace = ORDERS_NAMESPACE_URI)
    List<Order> findAll(@WebParam(name = "clientToken", header = true)
                        String clientToken,
                        @WebParam(name = "tokenExpiresAt", header = true, mode = WebParam.Mode.OUT)
                        Holder<String> tokenExpiresAt) throws ServiceException;

    @WebMethod()
    @WebResult(targetNamespace = ORDERS_NAMESPACE_URI)
    Order findById(@WebParam(name = "orderId") @XmlElement(required = true)
                             BigInteger orderId,
                             @WebParam(name = "clientToken", header = true)
                             String clientToken,
                             @WebParam(name = "tokenExpiresAt", header = true, mode = WebParam.Mode.OUT)
                             Holder<String> tokenExpiresAt) throws ServiceException;

    @WebMethod()
    @WebResult(targetNamespace = ORDERS_NAMESPACE_URI)
    Order add(@WebParam(name = "order") @XmlElement(required = true)
              Order order,
              @WebParam(name = "clientToken", header = true)
              String clientToken,
              @WebParam(name = "tokenExpiresAt", header = true, mode = WebParam.Mode.OUT)
              Holder<String> tokenExpiresAt) throws ServiceException;

    @WebMethod()
    @WebResult(targetNamespace = ORDERS_NAMESPACE_URI)
    Order update(@WebParam(name = "orderId") @XmlElement(required = true)
                 BigInteger orderId,
                 @WebParam(name = "order")  @XmlElement(required = true)
                 Order order,
                 @WebParam(name = "clientToken", header = true)
                 String clientToken,
                 @WebParam(name = "tokenExpiresAt", header = true, mode = WebParam.Mode.OUT)
                 Holder<String> tokenExpiresAt) throws ServiceException;

    @WebMethod()
    void delete(@WebParam(name = "orderId") @XmlElement(required = true)
                BigInteger orderId,
                @WebParam(name = "clientToken", header = true)
                String clientToken,
                @WebParam(name = "tokenExpiresAt", header = true, mode = WebParam.Mode.OUT)
                Holder<String> tokenExpiresAt) throws ServiceException;
}
