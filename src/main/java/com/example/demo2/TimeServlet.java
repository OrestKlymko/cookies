package com.example.demo2;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;


@WebServlet("/time")
public class TimeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws  IOException {
		resp.setContentType("text/html; charset=utf-8");

		String queryTimeZone = req.getParameter("timezone");

		if (queryTimeZone==null) queryTimeZone = "UTC";
		if (queryTimeZone.contains(" ")) queryTimeZone=queryTimeZone.replace(" ","+");
		OffsetDateTime now = OffsetDateTime.now( ZoneId.of(queryTimeZone));
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.of(queryTimeZone));
		String formatDate = now.format(dateTimeFormatter);


		resp.getWriter().write(formatDate);
		resp.getWriter().close();
	}
}
