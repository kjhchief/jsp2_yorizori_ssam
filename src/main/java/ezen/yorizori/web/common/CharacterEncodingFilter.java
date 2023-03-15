package ezen.yorizori.web.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import oracle.net.aso.e;

@WebFilter(
		urlPatterns = {"*.do"},
		initParams = {
			@WebInitParam(name = "encoding", value = "utf-8")
		}
)
public class CharacterEncodingFilter implements Filter {
	private String encoding;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		encoding = filterConfig.getInitParameter("encoding");	
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter)
			throws IOException, ServletException {
        // 요청 전처리 공통 기능
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html;charset="+encoding);
        filter.doFilter(request, response);
	}
	
	@Override
	public void destroy() {}

}
