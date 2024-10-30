
package hlova.stanislav.shoe_shop.orders;

import hlova.stanislav.shoe_shop.CurrencyType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Discount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Discount"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://stanislav.hlova/shoe-shop&gt;currencyType"&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Discount")
public class Discount
    extends CurrencyType
{


}
