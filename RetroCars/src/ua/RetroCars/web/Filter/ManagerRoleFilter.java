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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Security filter for manger's part
 */
@WebFilter("/ManagerController")
public class ManagerRoleFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(ManagerRoleFilter.class);
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		LOG.debug("Filter destruction starts");	
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
		String role=(String) session.getAttribute("role");
		if(role==null){
			LOG.debug("role unidentified");	
			resp.sendRedirect("ClientController");
		}else if(role.equals("admin")||role.equals("manager")){
			LOG.debug("Filter finished");	
			chain.doFilter(request, response);
		}else{
			LOG.debug("role don't equals manager");	
			resp.sendRedirect("ClientController");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		LOG.debug("Filter initialization finished");		
		LOG.debug("Filter initialization finished");
	}

}
