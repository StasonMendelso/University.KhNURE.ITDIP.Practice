package hlova.stanislav.shoe_shop;

import hlova.stanislav.shoe_shop.orders.*;
import hlova.stanislav.shoe_shop.service.orders.*;
import hlova.stanislav.shoe_shop.shoe.Manufacturer;
import hlova.stanislav.shoe_shop.shoe.Price;
import hlova.stanislav.shoe_shop.shoe.Shoe;
import jakarta.xml.ws.Holder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Stanislav Hlova
 */
public class OrderClientRunner {
    private static final Logger logger = LoggerFactory.getLogger(OrderClientRunner.class);
    public static final String EXCEPTION_ON_THE_SERVER_SIDE = "Exception on the server side!!";

    public static void main(String[] args) {
        Orders service = new Orders();

        service.setHandlerResolver(new ClientHandlerResolver());
        Holder<String> tokenExpiresAt = new Holder<>();
        OrderService orderServicePort = service.getOrderPort();

        logger.info("========================ATTEMPTS TO GET EXCEPTIONS=================================");
        //attempt to pass a null - must catch a ServiceException
        try {
            FindByIdResponse findByIdResponse = orderServicePort.findById(new FindById(null), null, tokenExpiresAt);
            logger.info(findByIdResponse.getReturn().toString());
        } catch (ServiceException_Exception serviceExceptionException) {
            logger.error(EXCEPTION_ON_THE_SERVER_SIDE, serviceExceptionException);
        }

        //attempt to add a null to orders - must catch a ServiceException
        try {
            AddResponse addResponse = orderServicePort.add(new Add(null), null, tokenExpiresAt);
            logger.info(addResponse.getReturn().toString());
        } catch (ServiceException_Exception serviceExceptionException) {
            logger.error(EXCEPTION_ON_THE_SERVER_SIDE, serviceExceptionException);
        }
        //attempt to update an order with null order and id - must catch a ServiceException
        try {
            UpdateResponse updateResponse = orderServicePort.update(new Update(null, null), null, tokenExpiresAt);
            logger.info(updateResponse.getReturn().toString());
        } catch (ServiceException_Exception serviceExceptionException) {
            logger.error(EXCEPTION_ON_THE_SERVER_SIDE, serviceExceptionException);
        }
        //attempt to delete an order - must catch a ServiceException
        try {
            orderServicePort.delete(new Delete(null), null, tokenExpiresAt);
            logger.info("Order was deleted");
        } catch (ServiceException_Exception serviceExceptionException) {
            logger.error(EXCEPTION_ON_THE_SERVER_SIDE, serviceExceptionException);
        }

        logger.info("========================ATTEMPTS TO CALL METHODS=================================");

        try {
            logger.info("========================ATTEMPT TO FIND ALL ORDERS=================================");
            FindAllResponse findAllResponse = orderServicePort.findAll(new FindAll(), null, tokenExpiresAt);
            logger.info(findAllResponse.getReturn().toString());
            logger.info("Token expires at = {}", tokenExpiresAt.value);

            logger.info("========================ATTEMPTS TO ADD 2 NEW ORDERS=================================");
            AddResponse addResponse = orderServicePort.add(new Add(createRandomOrder()), null, tokenExpiresAt);
            logger.info("Added order is :{}", addResponse.getReturn().toString());
            addResponse = orderServicePort.add(new Add(createRandomOrder()), null, tokenExpiresAt);
            logger.info("Added order is :{}", addResponse.getReturn().toString());

            logger.info("========================ATTEMPT TO FIND ALL ORDERS AFTER ADDING 2 ORDERS=================================");
            findAllResponse = orderServicePort.findAll(new FindAll(), null, tokenExpiresAt);
            logger.info(findAllResponse.getReturn().toString());

            logger.info("========================ATTEMPT TO FIND AN ORDER BY ID = 1 =================================");
            FindByIdResponse findByIdResponse = orderServicePort.findById(new FindById(BigInteger.ONE), null, tokenExpiresAt);
            logger.info(findByIdResponse.getReturn().toString());

            logger.info("========================ATTEMPT TO UPDATE AN ORDER WITH ID = 1 =================================");
            UpdateResponse updateResponse = orderServicePort.update(new Update(BigInteger.ONE, createRandomOrder()), null, tokenExpiresAt);
            logger.info(updateResponse.getReturn().toString());

            logger.info("========================ATTEMPT TO FIND AN UPDATED ORDER BY ID = 1 =================================");
            findByIdResponse = orderServicePort.findById(new FindById(BigInteger.ONE), null, tokenExpiresAt);
            logger.info(findByIdResponse.getReturn().toString());

            logger.info("========================ATTEMPT TO DELETE UPDATED ORDER BY ID = 1 =================================");
            orderServicePort.delete(new Delete(BigInteger.ONE), null, tokenExpiresAt);

            logger.info("========================ATTEMPT TO FIND A DELETED ORDER BY ID = 1 =================================");
            findByIdResponse = orderServicePort.findById(new FindById(BigInteger.ONE), null, tokenExpiresAt);
            if (findByIdResponse.getReturn() == null) {
                logger.info("Order was deleted!!!");
            } else {
                logger.error("ORDER WASN'T DELETED!!!\n {}", findByIdResponse.getReturn());
            }
        } catch (ServiceException_Exception serviceExceptionException) {
            logger.error(EXCEPTION_ON_THE_SERVER_SIDE, serviceExceptionException);
        }

    }

    private static final Random RANDOM = new Random();

    public static Order createRandomOrder() {
        Order order = new Order();

        List<OrderItem> orderItemList = new ArrayList<>();
        int itemCount = RANDOM.nextInt(3) + 1;
        for (int i = 0; i < itemCount; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId(BigInteger.valueOf(RANDOM.nextInt(1000)));

            Shoe shoe = new Shoe();
            shoe.setId(BigInteger.valueOf(RANDOM.nextInt(1000)));
            shoe.setName("Random Shoe " + (i + 1));
            shoe.setSize(BigDecimal.valueOf(RANDOM.nextDouble() * 5 + 36).setScale(0, RoundingMode.HALF_UP));
            shoe.setProductMaterial("Material " + (i + 1));
            shoe.setModel("Model" + (RANDOM.nextInt(99999)));
            shoe.setArticle("ART" + (RANDOM.nextInt(99999)));

            Price price = new Price();
            price.setCurrency("UAH");
            price.setValue(BigDecimal.valueOf(RANDOM.nextDouble() * 500 + 100).setScale(2, RoundingMode.HALF_UP));
            shoe.setPrice(price);

            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(BigInteger.valueOf(RANDOM.nextInt(100)));
            manufacturer.setName("Manufacturer " + (i + 1));
            manufacturer.setAddress("Address " + (i + 1));
            shoe.setManufacturer(manufacturer);

            orderItem.setShoe(shoe);
            Total total = new Total();
            total.setCurrency("UAH");
            orderItem.setCount(RANDOM.nextLong(3) + 1);
            if (RANDOM.nextBoolean()) {
                Discount discount = new Discount();
                discount.setCurrency("UAH");
                discount.setValue(BigDecimal.valueOf(RANDOM.nextDouble() * 50).setScale(2, RoundingMode.HALF_UP));
                orderItem.setDiscount(discount);
                total.setValue(price.getValue().multiply(BigDecimal.valueOf(orderItem.getCount())).subtract(discount.value).setScale(2, RoundingMode.HALF_UP));
            } else {
                total.setValue(price.getValue().multiply(BigDecimal.valueOf(orderItem.getCount())).setScale(2, RoundingMode.HALF_UP));
            }
            orderItem.setTotal(total);

            orderItemList.add(orderItem);
        }
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderItem(orderItemList);
        order.setOrderItems(orderItems);

        Buyer buyer = new Buyer();
        buyer.setId(BigInteger.valueOf(RANDOM.nextInt(100)));
        buyer.setFirstName("BuyerFirst" + RANDOM.nextInt(100));
        buyer.setLastName("BuyerLast" + RANDOM.nextInt(100));
        buyer.setMiddleName("BuyerMiddle" + RANDOM.nextInt(100));
        buyer.setEmail("buyer" + RANDOM.nextInt(100) + "@example.com");
        buyer.setTelephoneNumber("+380" + (RANDOM.nextInt(10000000) + 30000000));
        order.setBuyer(buyer);

        Receiver receiver = new Receiver();
        receiver.setId(BigInteger.valueOf(RANDOM.nextInt(100)));
        receiver.setFirstName("ReceiverFirst" + RANDOM.nextInt(100));
        receiver.setLastName("ReceiverLast" + RANDOM.nextInt(100));
        receiver.setMiddleName("ReceiverMiddle" + RANDOM.nextInt(100));
        receiver.setEmail("receiver" + RANDOM.nextInt(100) + "@example.com");
        receiver.setTelephoneNumber("+380" + (RANDOM.nextInt(10000000) + 30000000));
        order.setReceiver(receiver);


        if (RANDOM.nextBoolean()) {
            order.setStatus(OrderStatus.IN_PROCESS);
        } else {
            order.setStatus(OrderStatus.DONE);
            Delivery delivery = new Delivery();
            delivery.setAddress("Address " + RANDOM.nextInt(100));
            delivery.setDepartmentNumber((RANDOM.nextInt(100) + 1) + "-A");
            delivery.setDeliveryService("Service " + RANDOM.nextInt(5));
            delivery.setStatus(DeliveryStatus.DELIVERED);
            order.setDelivery(delivery);
        }

        return order;
    }
}
