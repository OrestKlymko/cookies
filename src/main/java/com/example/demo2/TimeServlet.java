package com.example.demo2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;



@WebServlet("/time")
public class TimeServlet extends HttpServlet {
	private TemplateEngine engine;

	@Override
	public void init() throws ServletException {
		engine = new TemplateEngine();
		FileTemplateResolver resolver = new FileTemplateResolver();
		resolver.setPrefix("/Users/orestklymko/Desktop/asdg/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setOrder(engine.getTemplateResolvers().size());
		resolver.setCacheable(false);
		engine.addTemplateResolver(resolver);
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html; charset=utf-8");

		String queryTimeZone = req.getParameter("timezone");


		if (queryTimeZone == null) {
			Cookie[] cookies = req.getCookies();

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("lastTimezone")) {
					queryTimeZone = cookie.getValue();
					break;
				} else {
					queryTimeZone = "UTC";
				}
			}
		}


		OffsetDateTime now = OffsetDateTime.now(ZoneId.of(queryTimeZone));
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.of(queryTimeZone));
		String formatDate = now.format(dateTimeFormatter);


		Context simpleContext = new Context(req.getLocale());
		simpleContext.setVariable("time", formatDate);


		engine.process("test", simpleContext, resp.getWriter());

		resp.getWriter().close();
	}
}
