package org.example.http.rest.util;

import org.glassfish.jaxb.runtime.marshaller.NamespacePrefixMapper;

import static org.example.http.rest.Constants.ORDERS_NAMESPACE_URI;
import static org.example.http.rest.Constants.SHOE_NAMESPACE_URI;

/**
 * @author Stanislav Hlova
 */
public class CustomNamespacePrefixMapper extends NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if (ORDERS_NAMESPACE_URI.equals(namespaceUri)) {
            return "tns";
        } else if (SHOE_NAMESPACE_URI.equals(namespaceUri)) {
            return "sh";
        }
        return suggestion;
    }
    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{ORDERS_NAMESPACE_URI, SHOE_NAMESPACE_URI};
    }
}
