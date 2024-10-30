
package hlova.stanislav.shoe_shop.orders;

import java.math.BigInteger;
import hlova.stanislav.shoe_shop.shoe.Shoe;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
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
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://stanislav.hlova/shoe-shop/shoe}shoe"/&gt;
 *         &lt;element name="count" type="{http://stanislav.hlova/shoe-shop/orders}Count"/&gt;
 *         &lt;element name="discount" type="{http://stanislav.hlova/shoe-shop/orders}Discount" minOccurs="0"/&gt;
 *         &lt;element name="total" type="{http://stanislav.hlova/shoe-shop/orders}Total"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedLong" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "shoe",
    "count",
    "discount",
    "total"
})
@XmlRootElement(name = "orderItem")
public class OrderItem {

    @XmlElement(namespace = "http://stanislav.hlova/shoe-shop/shoe", required = true)
    protected Shoe shoe;
    protected long count;
    protected Discount discount;
    @XmlElement(required = true)
    protected Total total;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger id;

    /**
     * Gets the value of the shoe property.
     * 
     * @return
     *     possible object is
     *     {@link Shoe }
     *     
     */
    public Shoe getShoe() {
        return shoe;
    }

    /**
     * Sets the value of the shoe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Shoe }
     *     
     */
    public void setShoe(Shoe value) {
        this.shoe = value;
    }

    /**
     * Gets the value of the count property.
     * 
     */
    public long getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     * 
     */
    public void setCount(long value) {
        this.count = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link Discount }
     *     
     */
    public Discount getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Discount }
     *     
     */
    public void setDiscount(Discount value) {
        this.discount = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link Total }
     *     
     */
    public Total getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link Total }
     *     
     */
    public void setTotal(Total value) {
        this.total = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "shoe=" + shoe +
                ", count=" + count +
                ", discount=" + discount +
                ", total=" + total +
                ", id=" + id +
                '}';
    }
}
