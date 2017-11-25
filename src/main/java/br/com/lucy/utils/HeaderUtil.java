package br.com.lucy.utils;

import java.sql.SQLException;

import br.com.lucy.daos.CategoryDAO;
import br.com.lucy.models.Category;

public class HeaderUtil {

	public static String getHeader() {
		String header = "<div id=\"header\">";
		header += "<ul>";
		header += "	<div id=\"logo\"><a href=\"/home\"><img src=\"#\"/></a></div>";
		CategoryDAO dao = new CategoryDAO();
		try {
			for (Category category : dao.list()) {
				header += "		<li><a href=\"\\category\\" + category.getId() + "\">" + category.getName()
						+ "</a></li>";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		header += "	<div id=\"cart\"><button type=\"button\"><img src=\"#\"/></button></div>"; // TODO
																								// change
																								// #
																								// for
																								// image
		header += "</ul>";
		header += "</div>";
		return header;
	}

}
