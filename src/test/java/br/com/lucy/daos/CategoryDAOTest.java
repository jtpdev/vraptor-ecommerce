package br.com.lucy.daos;

import java.sql.SQLException;

import br.com.lucy.builders.CategoryBuilder;
import br.com.lucy.models.Category;

public class CategoryDAOTest {
	
	private static CategoryDAO categoryDAO = new CategoryDAO();
	
	public static void main(String[] args) {
		insertCategories();
//		listCategories();
	}
	
	public static void insertCategories(){
		Category categoryWhite = new CategoryBuilder().setName("White").getValue();
		Category categoryRose = new CategoryBuilder().setName("Rose").getValue();
		Category categoryRed = new CategoryBuilder().setName("Red").getValue();
		
		insertCategories(categoryWhite, categoryRose, categoryRed);
	}

	private static void insertCategories(Category... categories) {
		for (Category category : categories) {
			try {
				Category savedCategory = categoryDAO.save(category);
				System.out.println(savedCategory.toString());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void listCategories(){
//		try {
//			categoryDAO.list().forEach(System.out::println);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
