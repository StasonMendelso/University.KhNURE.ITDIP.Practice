package org.example;

public final class Constants {
    private Constants(){}
    public static final class Tags{
        public static final String TAG_ORDER_STATUS = "status";
        public static final String TAG_STATUS = "status";
        public static final String TAG_DELIVERY = "delivery";
        public static final String TAG_RECEIVER = "receiver";
        public static final String TAG_BUYER = "buyer";
        public static final String TAG_ORDER_ITEMS = "orderItems";
        public static final String TAG_DELIVERY_STATUS = "status";
        public static final String TAG_DELIVERY_SERVICE = "delivery-service";
        public static final String TAG_DELIVERY_ADDRESS = "address";
        public static final String TAG_DELIVERY_DEPARTMENT_NUMBER = "departmentNumber";
        public static final String TAG_CONTACT_EMAIL = "email";
        public static final String TAG_CONTACT_LAST_NAME = "lastName";
        public static final String TAG_CONTACT_FIRST_NAME = "firstName";
        public static final String TAG_CONTACT_MIDDLE_NAME = "middleName";
        public static final String TAG_CONTACT_TELEPHONE_NUMBER = "telephoneNumber";
        public static final String TAG_COUNT = "count";
        public static final String TAG_DISCOUNT = "discount";
        public static final String TAG_TOTAL = "total";
        public static final String TAG_SHOE = "shoe";
        public static final String TAG_SHOE_NAME = "name";
        public static final String TAG_SHOE_PRODUCT_MATERIAL = "productMaterial";
        public static final String TAG_SHOE_ARTICLE = "article";
        public static final String TAG_SHOE_MODEL = "model";
        public static final String TAG_SHOE_SIZE = "size";
        public static final String TAG_SHOE_PRICE = "price";
        public static final String TAG_SHOE_MANUFACTURER = "manufacturer";
        public static final String TAG_MANUFACTURER_NAME = "name";
        public static final String TAG_MANUFACTURER_ADDRESS = "address";
        public static final String TAG_ADDRESS = "address";
        public static final String TAG_ORDER_ITEM = "orderItem";
        public static final String TAG_NAME = "name";
        public static final String TAG_ORDER_ITEM_COUNT = "count";

        private Tags(){
        }
        public static final String TAG_ORDERS = "orders";
        public static final String TAG_ORDER = "order";
    }

    public static final Class<?> ORDERS_OBJECT_FACTORY_CLASS = org.example.entity.orders.ObjectFactory.class;
    public static final String ORDERS_XML_FILE = "src/main/resources/orders.xml";
    public static final String RESULT_ORDERS_XML_FILE = "src/main/resources/result-orders.xml";
    public static final String ORDERS_NON_VALID_XML_FILE = "src/main/resources/ordersNonValid.xml";
    public static final String ORDERS_XSD_FILE = "src/main/resources/orders.xsd";
    public static final  String ORDERS_NAMESPACE_URI = "http://stanislav.hlova/shoe-shop/orders";
    public static final  String SHOE_NAMESPACE_URI = "http://stanislav.hlova/shoe-shop/shoe";

    public abstract static class Attributes {
        public static final String CONTACT_ID_ATTRIBUTE = "id";
        public static final String ORDER_ID_ATTRIBUTE = "id";
        public static final String ORDER_ITEM_ID_ATTRIBUTE = "id";
        public static final String DISCOUNT_CURRENCY_ATTRIBUTE = "currency";
        public static final String TOTAL_CURRENCY_ATTRIBUTE = "currency";
        public static final String PRICE_CURRENCY_ATTRIBUTE = "currency";
        public static final String MANUFACTURER_ID_ATTRIBUTE = "id";
        public static final String SHOE_ID_ATTRIBUTE = "id";

        private Attributes(){}

    }
}
