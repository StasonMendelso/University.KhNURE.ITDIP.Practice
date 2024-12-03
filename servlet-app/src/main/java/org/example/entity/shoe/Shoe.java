package org.example.entity.shoe;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Shoe {

    protected Manufacturer manufacturer;
    protected String name;
    protected BigDecimal size;
    protected String productMaterial;
    protected String model;
    protected String article;
    protected Price price;
    protected BigInteger id;

    public Shoe(Manufacturer manufacturer, String name, BigDecimal size, String productMaterial, String model, String article, Price price, BigInteger id) {
        this.manufacturer = manufacturer;
        this.name = name;
        this.size = size;
        this.productMaterial = productMaterial;
        this.model = model;
        this.article = article;
        this.price = price;
        this.id = id;
    }

    public Shoe() {
    }

    /**
     * Gets the value of the manufacturer property.
     *
     * @return possible object is
     * {@link Manufacturer }
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the value of the manufacturer property.
     *
     * @param value allowed object is
     *              {@link Manufacturer }
     */
    public void setManufacturer(Manufacturer value) {
        this.manufacturer = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the size property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setSize(BigDecimal value) {
        this.size = value;
    }

    /**
     * Gets the value of the productMaterial property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getProductMaterial() {
        return productMaterial;
    }

    /**
     * Sets the value of the productMaterial property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setProductMaterial(String value) {
        this.productMaterial = value;
    }

    /**
     * Gets the value of the model property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the article property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getArticle() {
        return article;
    }

    /**
     * Sets the value of the article property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setArticle(String value) {
        this.article = value;
    }

    /**
     * Gets the value of the price property.
     *
     * @return possible object is
     * {@link Price }
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     *
     * @param value allowed object is
     *              {@link Price }
     */
    public void setPrice(Price value) {
        this.price = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
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
