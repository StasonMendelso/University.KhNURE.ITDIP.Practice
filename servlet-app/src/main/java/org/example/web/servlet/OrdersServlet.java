package org.example.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import org.example.entity.orders.Order;
import org.example.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.example.web.Constant.OBJECT_MAPPER_SERVLET_CONTEXT_ATTRIBUTE;
import static org.example.web.Constant.ORDER_SERVICE_SERVLET_CONTEXT_ATTRIBUTE;

/**
 * @author Stanislav Hlova
 */
public class OrdersServlet extends HttpServlet {
    public static final Logger logger = LoggerFactory.getLogger(OrdersServlet.class);

    private OrderService orderService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext servletContext = getServletContext();
        orderService = (OrderService) servletContext.getAttribute(ORDER_SERVICE_SERVLET_CONTEXT_ATTRIBUTE);
        logger.trace("Get attribute OrderService: {}", orderService);
        objectMapper = (ObjectMapper) servletContext.getAttribute(OBJECT_MAPPER_SERVLET_CONTEXT_ATTRIBUTE);
        logger.trace("Get attribute ObjectMapper: {}", objectMapper);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object minTotal = request.getParameter("minTotal");
        Object maxTotal = request.getParameter("maxTotal");
        List<Order> orders;
        String pathInfo = request.getPathInfo();
        if (minTotal != null && maxTotal != null) {
            double minTotal1 = Double.parseDouble(String.valueOf(minTotal));
            double maxTotal1 = Double.parseDouble(String.valueOf(maxTotal));
            orders = orderService.getFilteredOrders(minTotal1, maxTotal1);
        } else if (pathInfo != null && !pathInfo.isEmpty()) {
            String orderId = pathInfo.substring(1);
            long id = Long.parseLong(orderId);
            setOk(response);
            logger.debug("doGet started to write an answer to client");
            ServletOutputStream responseOutputStream = response.getOutputStream();
            Order orderById = orderService.getOrderById(id);
            if (orderById == null) {
                setInternalError(response, Map.of("message", "Can't find an order. Please, try again later."));
                logger.debug("doGet finished to write an answer to client");
                return;
            }
            objectMapper.writeValue(responseOutputStream, orderById);
            logger.debug("doGet finished to write an answer to client");
            return;
        } else {
            orders = orderService.getAllOrders();
        }

        setOk(response);
        logger.debug("doGet started to write an answer to client");
        if (orders != null) {
            ServletOutputStream responseOutputStream = response.getOutputStream();
            objectMapper.writeValue(responseOutputStream, orders);
        } else {
            setInternalError(response, Map.of("message", "Can't fetch orders. Please, try again later."));
        }
        logger.debug("doGet finished to write an answer to client");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty()) {
            String orderId = pathInfo.substring(1);
            long id = Long.parseLong(orderId);
            boolean deleted = orderService.deleteOrder(id);
            logger.debug("doDelete started to write an answer to client");
            if (deleted) {
                setOk(response);
            } else {
                setInternalError(response, Map.of("message", "Can't delete an order with id " + id));
            }
            logger.debug("doDelete finished to write an answer to client");
        } else {
            setMethodNotAllowed(response, Map.of("message", "Pass id of order to delete it in the patter /orders/{id}"));
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Order order;
        try {
            order = objectMapper.readValue(request.getInputStream(), Order.class);
        } catch (IOException exception) {
            logger.info("During reading an order exception was occurred.", exception);
            setBadRequest(response, Map.of("message", "It seems, that you pass an order with invalid structure of JSON. Check it."));
            return;
        }
        Order createdOrder = orderService.createOrder(order);
        logger.debug("doPost started to write an answer to client");
        if (createdOrder != null) {
            //PRG
            response.sendRedirect(request.getContextPath() + request.getServletPath() + "/" + createdOrder.getId());
        } else {
            setInternalError(response, Map.of("message", "Can't create a new order."));
        }

        logger.debug("doPost finished to write an answer to client");
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty()) {
            String orderId = pathInfo.substring(1);
            long id = Long.parseLong(orderId);
            Order order;
            try {
                order = objectMapper.readValue(request.getInputStream(), Order.class);
            } catch (IOException exception) {
                logger.info("During reading an order exception was occurred.", exception);
                setBadRequest(response, Map.of("message", "It seems, that you pass an order with invalid structure of JSON. Check it."));
                return;
            }
            Order updatedOrder = orderService.updateOrder(id, order);
            logger.debug("doPut started to write an answer to client");
            if (updatedOrder != null) {
                setOk(response, updatedOrder);
            } else {
                setInternalError(response, Map.of("message", "Can't update an old order."));
            }
            logger.debug("doPut finished to write an answer to client");
        } else {
            setMethodNotAllowed(response, Map.of("message", "Pass id of order to update it in the pattern /orders/{id}"));
        }
    }

    private void setOk(HttpServletResponse response, Object object) throws IOException {
        setOk(response);
        objectMapper.writeValue(response.getOutputStream(), object);
    }

    private void setOk(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON);
    }

    private void setBadRequest(HttpServletResponse response, Map<String, Object> message) throws IOException {
        setResponse(response, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    private void setInternalError(HttpServletResponse response, Map<String, Object> message) throws IOException {
        setResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }


    private void setMethodNotAllowed(HttpServletResponse response, Map<String, Object> message) throws IOException {
        setResponse(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, message);
    }

    private void setResponse(HttpServletResponse response, int status, Map<String, Object> message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON);
        objectMapper.writeValue(response.getOutputStream(), message);
    }
}
