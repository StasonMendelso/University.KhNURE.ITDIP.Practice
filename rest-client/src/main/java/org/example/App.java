package org.example;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.example.entity.orders.*;
import org.example.entity.shoe.Manufacturer;
import org.example.entity.shoe.Price;
import org.example.entity.shoe.Shoe;
import org.example.http.rest.Constants;
import org.example.http.rest.JAXBContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static final URI BASE_URI = getBaseURI(Constants.BASE_URI, Constants.PORT, Constants.APPLICATION_PATH, Constants.ORDER_CONTROLLER_PATH);

    public static void main(String[] args) {
        Orders orders;
        List<Order> orderList;
        Order order;

        Client client = ClientBuilder.newClient()
                .register(JAXBContextProvider.class);


        WebTarget target = client.target(BASE_URI);


        logger.info("---All orders---");
        logger.info("--- XML to String ---");
        Invocation.Builder request = target.request()
                .accept(MediaType.APPLICATION_XML);

        Response response = request.get();

        String body;
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            body = response.readEntity(String.class);
            logger.info(body);
        }
        logger.info("--- JSON to String ---");
        request = target.request()
                .accept(MediaType.APPLICATION_JSON);

        response = request.get();
        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            body = response.readEntity(String.class);
            logger.info(body);
        }
        logger.info("--- XML to Objects ---");
        request = target.request()
                .accept(MediaType.APPLICATION_XML);
        response = request.get();

        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            orders = response.readEntity(Orders.class);
            logger.info(String.valueOf(orders));
        }

        logger.info("--- JSON to Objects ---");
        request = target.request()
                .accept(MediaType.APPLICATION_JSON);
        response = request.get();

        if (Response.Status.OK.getStatusCode() == response.getStatus()) {
            orderList = response.readEntity(new GenericType<List<Order>>() {});
            logger.info(String.valueOf(orderList));
        }


        logger.info("Fetching filtered orders...");
        Response filteredResponse = target
                .queryParam("minTotal", "3455.55")
                .queryParam("maxTotal", "17344.5")
                .request(MediaType.APPLICATION_XML)
                .get();
        if (filteredResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            String filteredXmlBody = filteredResponse.readEntity(String.class);
            logger.info(filteredXmlBody);
        }

        logger.info("Fetching specific order...");
        Response specificOrderResponse = target.path("/88991")
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (specificOrderResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            logger.info("Fetched order: {}", specificOrderResponse.readEntity(String.class));
        }else if(specificOrderResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode()){
            logger.warn("Order wasn't found by passed id. Response from server : {}", specificOrderResponse.readEntity(String.class));
        }
        logger.info("Fetching specific order...");
         specificOrderResponse = target.path("/1")
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (specificOrderResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            logger.info("Fetched order: {}", specificOrderResponse.readEntity(String.class));
        }else if(specificOrderResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode()){
            logger.warn("Order wasn't found by passed id. Response from server : {}", specificOrderResponse.readEntity(String.class));
        }

        logger.info("Creating a new order (XML)...");
        Order newOrder = new Order();
        newOrder.setBuyer(new Buyer("newEmail","newLastName","newFirstName","newMiddleName","+380123456789"));
        newOrder.setReceiver(new Receiver("newEmail","newLastName","newFirstName","newMiddleName","+380123456789"));
        newOrder.setStatus(OrderStatus.WAIT_FOR_CONSIDERATION);
        Shoe shoe = new Shoe(new Manufacturer("newManufactuer","newAddress", BigInteger.ONE),"newShoe",BigDecimal.valueOf(36.5),"newProductMaterial","newModel","AR1234",new Price(BigDecimal.valueOf(500),"UAH"), BigInteger.ONE);
        OrderItem orderItem = new OrderItem(shoe, new Count(3),new Discount(BigDecimal.ZERO,"UAH"),new Total(BigDecimal.valueOf(1500),"UAH"), BigInteger.TEN);
        newOrder.setOrderItems(new OrderItems(List.of(orderItem)));
        order = target.request(MediaType.APPLICATION_XML)
                .post(Entity.entity(newOrder, MediaType.APPLICATION_XML), Order.class);
        logger.info("Created order: {}", order);

        Order updatedOrder = new Order();
        updatedOrder.setBuyer(new Buyer("updatedEmail","updatedLastName","updatedFirstName","updatedMiddleName","+380123456789"));
        updatedOrder.setReceiver(new Receiver("updatedEmail","updatedLastName","updatedFirstName","updatedMiddleName","+380987654321"));
        updatedOrder.setStatus(OrderStatus.IN_PROCESS);
        Shoe updatedShoe = new Shoe(new Manufacturer("updatedManufactuer","updatedAddress", BigInteger.ONE),"updatedShoe",BigDecimal.valueOf(36.5),"updatedProductMaterial","updatedModel","AR12234234",new Price(BigDecimal.valueOf(500),"UAH"), BigInteger.ONE);
        OrderItem updatedOrderItem = new OrderItem(updatedShoe, new Count(3),new Discount(BigDecimal.ZERO,"UAH"),new Total(BigDecimal.valueOf(1500),"UAH"), BigInteger.TEN);
        updatedOrder.setOrderItems(new OrderItems(List.of(updatedOrderItem)));
        updatedOrder.setDelivery(new Delivery("deliveredAddress", "departmentNumber","deliveryService", DeliveryStatus.PACKAGING));
        order = target.path("/{id}")
                .resolveTemplate("id", order.getId().longValue())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updatedOrder, MediaType.APPLICATION_JSON), Order.class);
        logger.info("Updated order: {}", order);
//        logger.info("Patching order...");
//        String patchOrderJson = "{ \"buyer\": {\"id\": 14, \"email\": \"newbuyer@example.com\"} }";
//        Response patchOrderResponse = target.path("/"+order.getId())
//                .request(MediaType.APPLICATION_JSON)
//                .put(Entity.entity(patchOrderJson, MediaType.APPLICATION_JSON));
//        if (patchOrderResponse.getStatus() == Response.Status.OK.getStatusCode()) {
//            logger.info("Patched order: {}", patchOrderResponse.readEntity(String.class));
//        }
        logger.info("Deleting an order...");
        Response deleteOrderResponse = target.path("/1")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        if (deleteOrderResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            logger.info("Order deleted successfully. Response from server : {}", deleteOrderResponse.readEntity(String.class));
        }else if(specificOrderResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode()){
            logger.warn("Order wasn't found by passed id for deleting. Response from server : {}", deleteOrderResponse.readEntity(String.class));
        }

        deleteOrderResponse = target.path("/144453")
                .request(MediaType.APPLICATION_JSON)
                .delete();
        if (deleteOrderResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            logger.info("Order deleted successfully. Response from server : {}", deleteOrderResponse.readEntity(String.class));
        }else if(specificOrderResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode()){
            logger.warn("Order wasn't found by passed id for deleting. Response from server : {}", deleteOrderResponse.readEntity(String.class));
        }
    }

    public static URI getBaseURI(String basePath, int port, String... path) {
        UriBuilder builder = UriBuilder.fromUri(basePath).port(port);
        for (String part : path) {
            builder.path(part);
        }
        URI uri = builder.build();
        System.out.println("uri: " + uri);
        return uri;
    }
}
