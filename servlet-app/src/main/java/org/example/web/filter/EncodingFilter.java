package org.example.web.filter;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Stanislav Hlova
 */
public class EncodingFilter implements Filter {
    private String encoding;
    private final Logger log = LoggerFactory.getLogger(EncodingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestEncoding = request.getCharacterEncoding();
        log.debug("Request encoding : {}", requestEncoding);
        if (requestEncoding == null) {
            log.debug("Set encoding : {}", encoding);
            request.setCharacterEncoding(encoding);
        }

        // pass the request along the filter chain
        log.debug("Pass to next filter");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        encoding = fConfig.getInitParameter("encoding");
        log.debug("Get encoding from Init Parameter : {}", encoding);
    }


}
