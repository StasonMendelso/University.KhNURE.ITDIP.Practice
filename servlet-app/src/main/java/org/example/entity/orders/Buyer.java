
package org.example.entity.orders;

import java.math.BigInteger;

public class Buyer extends Contact {

    protected BigInteger id;

    public Buyer() {
    }

    public Buyer(String email, String lastName, String firstName, String middleName, String telephoneNumber) {
        super(email, lastName, firstName, middleName, telephoneNumber);
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
        return "Buyer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                "} ";
    }
}
