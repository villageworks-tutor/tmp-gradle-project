package sample.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import sample.bean.Category;
import sample.bean.Item;
import sample.dao.CategoryDAO;
import sample.dao.DAOException;
import sample.dao.ItemDAO;

/**
 * Servlet implementation class SampleServlet
 */
@WebServlet("/sample")
public class SampleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// アプリケーションスコープからカテゴリリストを取得
		@SuppressWarnings("unchecked")
		List<Category> list = (List<Category>) getServletContext().getAttribute("categories");
		// カテゴリリストが存在する場合：処理をせずに終了
		if (list != null) {
			return;
		}
		// カテゴリが存在しない場合：データベースから取得
		try {
			// カテゴリリストを初期化
			list = new ArrayList<Category>();
			CategoryDAO dao = new CategoryDAO();
			list = dao.findAll();
			getServletContext().setAttribute("categories", list);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コード設定
		request.setCharacterEncoding("utf-8");
		// リクエストからactionパラメータを取得
		String action = request.getParameter("action");
		// actionパラメータによる処理の分岐
		String nextJSP = "";
		if ("list".equals(action)) {
			// 商品リストを取得
			List<Item> list = new ArrayList<Item>();
			try {
				ItemDAO dao = new ItemDAO();
				list = dao.findAll();
			} catch (DAOException e) {
				e.printStackTrace();
			}
			// 次画面に引き継ぐ値をスコープに登録
			request.setAttribute("title", "データベース連携サンプル");
			request.setAttribute("category", "すべて");
			request.setAttribute("count", list.size());
			request.setAttribute("items", list);
			// 遷移先JSP
			nextJSP = "/WEB-INF/view/sample/db_sample.jsp";
		} else if ("category".equals(action)) {
			// リクエストパラメータを取得
			String categoryIdString = request.getParameter("categoryId");
			
			// 取得した商品カテゴリのデータ型変換
			int categoryId = Integer.parseInt(categoryIdString);
			// アプリケーションスコープに登録されているカテゴリリストからカテゴリ名を取得
			@SuppressWarnings("unchecked")
			List<Category> categories = (List<Category>) getServletContext().getAttribute("categories");
			String categoryName = "";
			for (Category category : categories) {
				if (category.getId() == categoryId) {
					categoryName = category.getName();
					break;
				}
			}
			
			// 商品カテゴリに含まれる商品のリストの初期化
			List<Item> list = new ArrayList<Item>();
			try {
				// 商品カテゴリーに含まれる商品を取得
				ItemDAO dao = new ItemDAO();
				list = dao.findByCategoryId(categoryId);
			} catch (NumberFormatException | DAOException e) {
				e.printStackTrace();
			}
			// 次画面に引き継ぐ値をスコープに登録
			request.setAttribute("title", "データベース連携サンプル");
			request.setAttribute("category", categoryName);
			request.setAttribute("count", list.size());
			request.setAttribute("items", list);
			// 遷移先JSP
			nextJSP = "/WEB-INF/view/sample/db_sample.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextJSP);
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
