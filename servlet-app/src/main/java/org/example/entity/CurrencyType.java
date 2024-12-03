package org.example.entity;

import java.math.BigDecimal;


public class CurrencyType {

    protected BigDecimal value;
    protected String currency;

    public CurrencyType() {
    }

    public CurrencyType(BigDecimal value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    /**
     * Gets the value of the value property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Gets the value of the currency property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    @Override
    public String toString() {
        return "CurrencyType{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
