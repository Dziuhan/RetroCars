package ua.RetroCars.web.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
/**
 * Encoding filter *
 */
@WebFilter("*")
public class EncodingFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(EncodingFilter.class);
	
	public void destroy() {
		LOG.debug("Filter destruction start");	
		LOG.debug("Filter destruction finished");
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");
		request.setCharacterEncoding("UTF-8");
		LOG.trace("set encoding --> UTF-8  request uri -->"+((HttpServletRequest)request).getRequestURI() );		
		LOG.debug("Filter finished");	
		chain.doFilter(request, response);		
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization start");		
		LOG.debug("Filter initialization finished");
	}

}
