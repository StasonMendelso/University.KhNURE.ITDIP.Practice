
package org.example.entity.orders;

import org.example.entity.CurrencyType;

import java.math.BigDecimal;


/**
 * <p>Java class for Total complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Total"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://stanislav.hlova/shoe-shop&gt;currencyType"&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
public class Total extends CurrencyType {
    public Total() {
    }

    public Total(BigDecimal value, String currency) {
        super(value, currency);
    }
}
