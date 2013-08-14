package pm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class AuditFilter implements Filter {

	private Logger log = Logger.getLogger("file."+AuditFilter.class.getName()); 

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

		try {
			HttpServletRequest request = (HttpServletRequest) req;
			StringBuffer sb = new StringBuffer();
			sb.append("remoteIP=").append(req.getRemoteAddr())
			  .append(" ")
			  .append("method=").append(request.getMethod())
			  .append(" ")
			  .append("path=").append(request.getPathInfo());
			if (request.getQueryString() != null) {
				sb.append("?").append(request.getQueryString());
			}
			log.info(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		filterChain.doFilter(req, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
	}
}
