
package org.example.entity.orders;

import java.util.ArrayList;
import java.util.List;



public class Orders {

    protected List<Order> order;

    /**
     * Gets the value of the order property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the order property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrder().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Order }
     */
    public List<Order> getOrder() {
        if (order == null) {
            order = new ArrayList<Order>();
        }
        return this.order;
    }
    public void setOrderList(List<Order> orderList){
        this.order = orderList;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order=" + order +
                '}';
    }
}
