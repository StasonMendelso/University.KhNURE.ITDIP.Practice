

package org.example.entity.orders;

import java.util.ArrayList;
import java.util.List;



public class OrderItems {

    protected List<OrderItem> orderItem;

    public OrderItems() {
    }

    public OrderItems(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    /**
     * Gets the value of the orderItem property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the orderItem property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderItem().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderItem }
     */
    public List<OrderItem> getOrderItem() {
        if (orderItem == null) {
            orderItem = new ArrayList<OrderItem>();
        }
        return this.orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "orderItem=" + orderItem +
                '}';
    }
}
