
package hlova.stanislav.shoe_shop.shoe;

import java.math.BigDecimal;
import java.math.BigInteger;
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
 *         &lt;element ref="{http://stanislav.hlova/shoe-shop/shoe}manufacturer"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="productMaterial" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="article" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="price" type="{http://stanislav.hlova/shoe-shop/shoe}Price"/&gt;
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
    "manufacturer",
    "name",
    "size",
    "productMaterial",
    "model",
    "article",
    "price"
})
@XmlRootElement(name = "shoe")
public class Shoe {

    @XmlElement(required = true)
    protected Manufacturer manufacturer;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected BigDecimal size;
    @XmlElement(required = true)
    protected String productMaterial;
    @XmlElement(required = true)
    protected String model;
    @XmlElement(required = true)
    protected String article;
    @XmlElement(required = true)
    protected Price price;
    @XmlAttribute(name = "id", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger id;

    /**
     * Gets the value of the manufacturer property.
     * 
     * @return
     *     possible object is
     *     {@link Manufacturer }
     *     
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the value of the manufacturer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Manufacturer }
     *     
     */
    public void setManufacturer(Manufacturer value) {
        this.manufacturer = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSize(BigDecimal value) {
        this.size = value;
    }

    /**
     * Gets the value of the productMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductMaterial() {
        return productMaterial;
    }

    /**
     * Sets the value of the productMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductMaterial(String value) {
        this.productMaterial = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the article property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArticle() {
        return article;
    }

    /**
     * Sets the value of the article property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArticle(String value) {
        this.article = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link Price }
     *     
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link Price }
     *     
     */
    public void setPrice(Price value) {
        this.price = value;
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
        return "Shoe{" +
                "manufacturer=" + manufacturer +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", productMaterial='" + productMaterial + '\'' +
                ", model='" + model + '\'' +
                ", article='" + article + '\'' +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
