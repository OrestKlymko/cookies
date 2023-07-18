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
		String[] split = timezoneParam.split("-+");
		System.out.println();

		if(Integer.parseInt(split[1])>=24)
		{
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
			return;
		}

		chain.doFilter(request, response);
	}

}
