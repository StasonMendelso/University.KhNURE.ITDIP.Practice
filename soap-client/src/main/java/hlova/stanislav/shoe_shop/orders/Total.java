
package hlova.stanislav.shoe_shop.orders;

import hlova.stanislav.shoe_shop.CurrencyType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


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
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Total")
public class Total
    extends CurrencyType
{


}
