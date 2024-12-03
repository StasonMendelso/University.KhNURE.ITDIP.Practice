
package org.example.entity.shoe;

import org.example.entity.CurrencyType;

import java.math.BigDecimal;


public class Price extends CurrencyType {
    public Price() {
    }

    public Price(BigDecimal value, String currency) {
        super(value, currency);
    }
}
