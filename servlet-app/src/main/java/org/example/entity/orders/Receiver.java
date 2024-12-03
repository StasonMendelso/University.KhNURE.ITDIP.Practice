
package org.example.entity.orders;

import java.math.BigInteger;

public class Receiver extends Contact {

    protected BigInteger id;

    public Receiver(String email, String lastName, String firstName, String middleName, String telephoneNumber) {
        super(email, lastName, firstName, middleName, telephoneNumber);
    }

    public Receiver() {
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
        return "Receiver{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                "} ";
    }
}
