package dev.diegofernando.banktransactionmanager.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class RequestLogFilter implements Filter {

    protected static final Logger logger = LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        StringBuilder builderRequest = new StringBuilder()
                .append("\nRequest: ")
                .append("\nIP Adress: ")
                .append(request.getRemoteAddr())
                .append("\nPath: ")
                .append(request.getMethod())
                .append(" ")
                .append(request.getRequestURI());


        logger.debug(builderRequest.toString());

        filterChain.doFilter(servletRequest, servletResponse);

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        StringBuilder builderResponse = new StringBuilder()
                .append("\nResponse: ")
                .append("\nIP Adress: ")
                .append(request.getRemoteAddr())
                .append("\nPath: ")
                .append(request.getMethod())
                .append(" ")
                .append(request.getRequestURI())
                .append(" ")
                .append(response.getStatus());


        logger.debug(builderResponse.toString());

    }

    @Override
    public void destroy() {

    }
}
