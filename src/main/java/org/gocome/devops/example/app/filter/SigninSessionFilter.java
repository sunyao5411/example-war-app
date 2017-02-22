package org.gocome.devops.example.app.filter;

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

import org.apache.commons.lang.StringUtils;
import org.gocome.devops.example.app.model.Employee;

/**
 * Servlet Filter implementation class RestUrlFilter
 */
public class SigninSessionFilter implements Filter {
	
	private String excludes;
	private boolean disable;

    /**
     * Default constructor. 
     */
    public SigninSessionFilter() {
    }

	/**
	 * @return Returns the excludes.
	 */
	public String getExcludes() {
		return excludes;
	}

	/**
	 * @param excludes The excludes to set.
	 */
	public void setExcludes(String excludes) {
		this.excludes = excludes;
	}
	
	private boolean isExclude(String uri) {
		// if uri in excludes return true else false
		// TODO
		return StringUtils.isNotBlank(uri) && (uri.endsWith("signin.html")
				|| uri.endsWith("/api/employees/signin"));
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		if (!disable && !isExclude(req.getRequestURI()) && null == session.getAttribute(Employee.class.getSimpleName())) {
			((HttpServletResponse)response).sendRedirect(req.getContextPath() + "/signin.html");
			System.err.println(String.format("[WARN] You must be login before call the resource %s.", req.getRequestURI()));
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
