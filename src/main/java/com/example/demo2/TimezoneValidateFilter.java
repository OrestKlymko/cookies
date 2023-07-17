package com.example.demo2;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.TimeZone;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {
	final String DEFAULT_GMT_TIMEZONE = "UTC";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String timezoneParam = httpRequest.getParameter("timezone");

		if (timezoneParam == null || timezoneParam.isEmpty()) {
			timezoneParam=DEFAULT_GMT_TIMEZONE;
		}

		if (!isValidTimezone(timezoneParam)) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
			return;
		}
		chain.doFilter(request, response);
	}

	private boolean isValidTimezone(String timezone) {

		if (timezone.equals(DEFAULT_GMT_TIMEZONE)) {
			return true;
		} else {
			String id = TimeZone.getTimeZone(timezone).getID();
			if (!id.equals(DEFAULT_GMT_TIMEZONE)) {
				return true;
			}
		}
		return false;
	}

}
