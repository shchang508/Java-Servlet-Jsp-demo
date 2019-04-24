package com.seesawin.demo.ch08;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.seesawin.demo.ch08.model.UserBean;

@WebServlet("/ch08/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		if ("init".equals(action)) {
			// 登入畫面
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/ch08/ch08_login.jsp");
			rd.forward(request, response);
			return;
		} else if ("check".equals(action)) {
			String acct = request.getParameter("acct");
			String pwd = request.getParameter("pwd");

			boolean isCorrect = false;

			// check acct and pwd
			if ("Frank".equals(acct) && "1234".equals(pwd)) {
				isCorrect = true;
			} else if ("Alec".equals(acct) && "4321".equals(pwd)) {
				isCorrect = true;
			}

			if (!isCorrect) {
				// 登入畫面
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/ch08/ch08_login.jsp");
				// 設置requetScope變量
				request.setAttribute("acct", acct);
				request.setAttribute("pwd", pwd);
				request.setAttribute("errMsg", "帳號或密碼錯誤!");

				rd.forward(request, response);
			} else {
				// 結果畫面

				UserBean user = new UserBean();
				user.setAcct(acct);
				user.setPwd(pwd);

				// 取得HttpSession物件
				HttpSession session = request.getSession();

				// set user
				session.setAttribute("user", user);
				
				RequestDispatcher rd = request.getRequestDispatcher("/ch08/product");
				rd.forward(request, response);
				return;
			}
		} else if ("logOut".equals(action)) {
			// 取得HttpSession物件
			HttpSession session = request.getSession();

			// clean user
			session.setAttribute("user", null);
			
			RequestDispatcher rd = request.getRequestDispatcher("/jsp/ch08/ch08_index.jsp");
			rd.forward(request, response);
			return;
		}
	}
}
