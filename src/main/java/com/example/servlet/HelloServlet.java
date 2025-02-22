package com.example.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getRequestURI();
		String contextPath = request.getContextPath();
		String servletPath = path.replaceAll(contextPath, "");
		String nextPath = "/WEB-INF/jsp/hello.jsp";
		
		// 遷移先で表示するデータ
		String[] greetings = {"おはようございます", "こんにちは", "こんばんは", "おやすみなさい"};
		String capital = "東京";
		// 遷移先で表示するデータをスコープに登録
		request.setAttribute("greetings", greetings);
		request.setAttribute("capital", capital);
		// 画面遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPath);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
