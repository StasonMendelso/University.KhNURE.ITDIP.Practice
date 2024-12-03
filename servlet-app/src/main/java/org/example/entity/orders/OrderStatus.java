
package org.example.entity.orders;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;
import java.util.Arrays;


@JsonDeserialize(using = OrderStatus.OrderStatusDeserializer.class)
@JsonSerialize(using = OrderStatus.OrderStatusSerializer.class)
public enum OrderStatus {

    WAIT_FOR_CONSIDERATION("Очікує розгляд"),
    ACCEPTED("Підтверджено"),
    DECLINED("Відхилено"),
    CANCELLED("Скасовано"),
    DONE("Виконано"),
    IN_PROCESS("В обробці"),
    ON_DELIVERY("На доставці");
    private final String value;

    OrderStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OrderStatus fromValue(String v) {
        for (OrderStatus c : OrderStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public static class OrderStatusDeserializer extends JsonDeserializer<OrderStatus> {
        public OrderStatusDeserializer() {
        }

        @Override
        public OrderStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String value = p.getText();
            try {
                return OrderStatus.fromValue(value);
            } catch (IllegalArgumentException e) {
                String validValues = String.join(", ",
                        Arrays.stream(OrderStatus.values())
                                .map(OrderStatus::value)
                                .toArray(String[]::new)
                );
                throw new IOException("Invalid value for OrderStatus: '" + value + "'. Valid values are: " + validValues);
            }
        }
    }

    public static class OrderStatusSerializer extends JsonSerializer<OrderStatus> {
        @Override
        public void serialize(OrderStatus orderStatus, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(orderStatus.value());
        }
    }
}
