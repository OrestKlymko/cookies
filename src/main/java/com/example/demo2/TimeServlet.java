package com.example.demo2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");

		String queryTimeZone = req.getParameter("timezone");
		if(queryTimeZone==null) queryTimeZone="UTC";

		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		dateFormat.setTimeZone(TimeZone.getTimeZone(queryTimeZone));
		String currentTime = dateFormat.format(currentDate);



		resp.getWriter().write(currentTime);
		resp.getWriter().close();
	}

}