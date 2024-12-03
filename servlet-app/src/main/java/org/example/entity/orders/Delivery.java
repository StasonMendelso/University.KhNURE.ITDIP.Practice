package org.example.entity.orders;


import java.math.BigInteger;

public class Delivery {
    protected BigInteger id;

    protected String address;
    protected String departmentNumber;
    protected String deliveryService;
    protected DeliveryStatus status;

    public Delivery() {
    }

    public Delivery(String address, String departmentNumber, String deliveryService, DeliveryStatus status) {
        this.address = address;
        this.departmentNumber = departmentNumber;
        this.deliveryService = deliveryService;
        this.status = status;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Delivery(BigInteger id, String address, String departmentNumber, String deliveryService, DeliveryStatus status) {
        this.id = id;
        this.address = address;
        this.departmentNumber = departmentNumber;
        this.deliveryService = deliveryService;
        this.status = status;
    }

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
