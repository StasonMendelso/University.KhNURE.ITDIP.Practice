
package hlova.stanislav.shoe_shop.orders;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


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
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "delivery")
public class Delivery {

    @XmlElement(required = true)
    protected String address;
    @XmlElement(required = true)
    protected String departmentNumber;
    @XmlElement(name = "delivery-service", required = true)
    protected String deliveryService;
    @XmlElement(required = true)
    protected DeliveryStatus status;

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * Gets the value of the departmentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartmentNumber() {
        return departmentNumber;
    }

    /**
     * Sets the value of the departmentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartmentNumber(String value) {
        this.departmentNumber = value;
    }

    /**
     * Gets the value of the deliveryService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryService() {
        return deliveryService;
    }

    /**
     * Sets the value of the deliveryService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryService(String value) {
        this.deliveryService = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DeliveryStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
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
