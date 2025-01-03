//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.10.16 at 12:05:38 AM EEST 
//


package org.example.entity.orders;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.*;

import java.math.BigDecimal;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="address" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="departmentNumber" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="delivery-service" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="status" type="{http://stanislav.hlova/shoe-shop/orders}DeliveryStatus"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "delivery")
public class Delivery {
    protected BigDecimal id;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Delivery() {
    }

    public Delivery(BigDecimal id, String address, String departmentNumber, String deliveryService, DeliveryStatus status) {
        this.id = id;
        this.address = address;
        this.departmentNumber = departmentNumber;
        this.deliveryService = deliveryService;
        this.status = status;
    }

    @XmlElement(required = true)
    protected String address;
    @XmlElement(required = true)
    protected String departmentNumber;
    @XmlElement(name = "delivery-service", required = true)
    @JsonProperty("deliveryService")
    protected String deliveryService;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected DeliveryStatus status;

    /**
     * Gets the value of the address property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the departmentNumber property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDepartmentNumber() {
        return departmentNumber;
    }

    /**
     * Sets the value of the departmentNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDepartmentNumber(String value) {
        this.departmentNumber = value;
    }

    /**
     * Gets the value of the deliveryService property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDeliveryService() {
        return deliveryService;
    }

    /**
     * Sets the value of the deliveryService property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDeliveryService(String value) {
        this.deliveryService = value;
    }

    /**
     * Gets the value of the status property.
     *
     * @return possible object is
     * {@link DeliveryStatus }
     */
    public DeliveryStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     *
     * @param value allowed object is
     *              {@link DeliveryStatus }
     */
    public void setStatus(DeliveryStatus value) {
        this.status = value;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "address='" + address + '\'' +
                ", departmentNumber='" + departmentNumber + '\'' +
                ", deliveryService='" + deliveryService + '\'' +
                ", status=" + status +
                '}';
    }
}
