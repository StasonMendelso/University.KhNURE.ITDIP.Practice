//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.10.16 at 12:05:38 AM EEST 
//


package org.example.entity.orders;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Total")
public class Total extends CurrencyType {
    public Total() {
    }

    public Total(BigDecimal value, String currency) {
        super(value, currency);
    }
}
