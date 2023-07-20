package com.example.demo2;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;


		String timezoneParam = httpRequest.getParameter("timezone");

		if(timezoneParam!=null&&!timezoneParam.equals("UTC")) {
			String[] split = timezoneParam.split("[-+]");


			if (!split[0].equals("UTC") || Integer.parseInt(split[1]) >= 19) {
				httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
				return;
			}
		}
		chain.doFilter(request, response);
	}

}
