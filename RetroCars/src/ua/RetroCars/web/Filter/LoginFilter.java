package ua.RetroCars.web.Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Security-login filter
 */
public class LoginFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(LoginFilter.class);
   

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		LOG.debug("Filter destruction start");	
		LOG.debug("Filter destruction finished");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOG.debug("Filter starts");
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		HttpSession session=req.getSession();
		if(session.getAttribute("login")!=null){
			LOG.debug("Filter finished");
				chain.doFilter(request, response);		
		}else{
			LOG.debug("Need login");
			session.setAttribute("messageForGuest", "You need login");
			resp.sendRedirect("ClientController");	
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization start");		
		LOG.debug("Filter initialization finished");
	}

}
