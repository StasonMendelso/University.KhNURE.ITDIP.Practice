
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


@JsonDeserialize(using = DeliveryStatus.DeliveryStatusDeserializer.class)
@JsonSerialize(using = DeliveryStatus.DeliveryStatusSerializer.class)
public enum DeliveryStatus {

    PACKAGING("Пакується"),
    ON_DELIVERY("Доставляється"),
    DELIVERED("Доставлено"),
    RETURNED_TO_WAREHOUSE("Повернуто на склад"),
    RETURNING("Повертається");
    private final String value;

    DeliveryStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DeliveryStatus fromValue(String v) {
        for (DeliveryStatus c : DeliveryStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    public static class DeliveryStatusDeserializer extends JsonDeserializer<DeliveryStatus> {

        @Override
        public DeliveryStatus deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            String value = parser.getText();

            try {
                return DeliveryStatus.fromValue(value);
            } catch (IllegalArgumentException e) {
                String validValues = String.join(", ",
                        Arrays.stream(DeliveryStatus.values())
                                .map(DeliveryStatus::value)
                                .toArray(String[]::new)
                );
                throw new IOException("Invalid value for DeliveryStatus: '" + value + "'. Valid values are: " + validValues);
            }
        }
    }
    public static class DeliveryStatusSerializer extends JsonSerializer<DeliveryStatus> {
        @Override
        public void serialize(DeliveryStatus deliveryStatus, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(deliveryStatus.value());
        }
    }
}
