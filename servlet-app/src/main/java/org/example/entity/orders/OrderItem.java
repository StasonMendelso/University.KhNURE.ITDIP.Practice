
package org.example.entity.orders;

import org.example.entity.shoe.Shoe;

import java.math.BigInteger;


public class OrderItem {

    protected Shoe shoe;
    protected Count count;
    protected Discount discount;
    protected Total total;
    protected BigInteger id;

    public OrderItem(Shoe shoe, Count count, Discount discount, Total total, BigInteger id) {
        this.shoe = shoe;
        this.count = count;
        this.discount = discount;
        this.total = total;
        this.id = id;
    }

    public OrderItem() {
    }

    /**
     * Gets the value of the shoe property.
     *
     * @return possible object is
     * {@link Shoe }
     */
    public Shoe getShoe() {
        return shoe;
    }

    /**
     * Sets the value of the shoe property.
     *
     * @param value allowed object is
     *              {@link Shoe }
     */
    public void setShoe(Shoe value) {
        this.shoe = value;
    }

    /**
     * Gets the value of the count property.
     *
     * @return possible object is
     * {@link Count }
     */
    public Count getCount() {
        return count;
    }

    /**
     * Sets the value of the count property.
     *
     * @param value allowed object is
     *              {@link Count }
     */
    public void setCount(Count value) {
        this.count = value;
    }

    /**
     * Gets the value of the discount property.
     *
     * @return possible object is
     * {@link Discount }
     */
    public Discount getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     *
     * @param value allowed object is
     *              {@link Discount }
     */
    public void setDiscount(Discount value) {
        this.discount = value;
    }

    /**
     * Gets the value of the total property.
     *
     * @return possible object is
     * {@link Total }
     */
    public Total getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     *
     * @param value allowed object is
     *              {@link Total }
     */
    public void setTotal(Total value) {
        this.total = value;
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
        return "OrderItem{" +
                "shoe=" + shoe +
                ", count=" + count +
                ", discount=" + discount +
                ", total=" + total +
                ", id=" + id +
                '}';
    }
}
