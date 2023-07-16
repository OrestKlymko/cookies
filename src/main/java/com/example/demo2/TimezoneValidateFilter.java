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

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String timezoneParam = httpRequest.getParameter("timezone");

		if (timezoneParam == null || timezoneParam.isEmpty()) {
			timezoneParam="UTC";
		}

		if (!isValidTimezone(timezoneParam)) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
			return;
		}

		chain.doFilter(request, response);
	}

	private boolean isValidTimezone(String timezone) {
		String[] availableTimezones = TimeZone.getAvailableIDs();
		for (String tz : availableTimezones) {
			if (tz.equals(timezone)) {
				return true;
			}
		}
		return false;
	}



}
