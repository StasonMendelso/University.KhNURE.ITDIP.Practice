

package org.example.entity.orders;


public class Count {

    protected long value;

    public Count() {
    }

    public Count(long value) {
        this.value = value;
    }

    /**
     * Gets the value of the value property.
     */
    public long getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     */
    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Count{" +
                "value=" + value +
                '}';
    }

}
