
package hlova.stanislav.shoe_shop.shoe;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hlova.stanislav.shoe_shop.shoe package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hlova.stanislav.shoe_shop.shoe
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Manufacturer }
     * 
     */
    public Manufacturer createManufacturer() {
        return new Manufacturer();
    }

    /**
     * Create an instance of {@link Shoe }
     * 
     */
    public Shoe createShoe() {
        return new Shoe();
    }

    /**
     * Create an instance of {@link Price }
     * 
     */
    public Price createPrice() {
        return new Price();
    }

}
