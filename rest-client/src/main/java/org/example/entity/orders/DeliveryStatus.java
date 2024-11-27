//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.10.16 at 12:05:38 AM EEST 
//


package org.example.entity.orders;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

import java.io.IOException;
import java.util.Arrays;


/**
 * <p>Java class for DeliveryStatus.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="DeliveryStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Пакується"/&gt;
 *     &lt;enumeration value="Доставляється"/&gt;
 *     &lt;enumeration value="Доставлено"/&gt;
 *     &lt;enumeration value="Повернуто на склад"/&gt;
 *     &lt;enumeration value="Повертається"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "DeliveryStatus")
@XmlEnum
@JsonDeserialize(using = DeliveryStatus.DeliveryStatusDeserializer.class)
public enum DeliveryStatus {

    @XmlEnumValue("Пакується")
    PACKAGING("Пакується"),
    @XmlEnumValue("Доставляється")
    ON_DELIVERY("Доставляється"),
    @XmlEnumValue("Доставлено")
    DELIVERED("Доставлено"),
    @XmlEnumValue("Повернуто на склад")
    RETURNED_TO_WAREHOUSE("Повернуто на склад"),
    @XmlEnumValue("Повертається")
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
}