package br.com.lucy.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.lucy.models.Category;

public class CategoryDAO extends ConnectionDAO implements DAO<Category> {

	@Override
	public Category save(Category category) throws SQLException {
		if (category == null) {
			return null;
		} else if (category.getId() != null) {
			return category;
		}
		category.setId(nextId());
		if (category.getCategoryUp() != null) {
			String sql = "insert into category (id,name,categoryup_id) values (?,?,?)";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setLong(1, category.getId());
			stmt.setString(2, category.getName());
			stmt.setLong(3, category.getCategoryUp().getId());
			stmt.execute();
			close(stmt);
		} else {
			String sql = "insert into category (id,name) values (?,?)";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setLong(1, nextId());
			stmt.setString(2, category.getName());
			stmt.execute();
			close(stmt);
		}
		return find(category.getId());
	}

	@Override
	public Category find(Long id) throws SQLException {
		if (id == null)
			return null;
		PreparedStatement stmt = connect().prepareStatement("select id, name from category where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		Category category = new Category();
		while (rs.next()) {
			category.setId(rs.getLong(1));
			category.setName(rs.getString(2));
		}
		close(rs, stmt);
		return category;
	}

	public Category find(Connection connection, Long id) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("select id, name from category where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		Category category = new Category();
		while (rs.next()) {
			category.setId(rs.getLong(1));
			category.setName(rs.getString(2));
		}
		stmt.close();
		rs.close();
		return category;
	}

	@Override
	public void delete(Long id) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Long nextId() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select max(id) from category");
		ResultSet rs = stmt.executeQuery();
		Long id = 0L;
		while (rs.next()) {
			id = rs.getLong(1);
		}
		close(rs, stmt);
		return id + 1;
	}

	@Override
	public List<Category> list() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select id, name from category");
		ResultSet rs = stmt.executeQuery();
		List<Category> categories = new ArrayList<>();
		while (rs.next()) {
			Category category = new Category();
			category.setId(rs.getLong(1));
			category.setName(rs.getString(2));
			categories.add(category);
		}
		close(rs, stmt);
		return categories;
	}

	@Override
	public Category update(Category category) throws SQLException {
		if (category == null) {
			return null;
		} else if (category.getId() == null) {
			return save(category);
		}
		if (category.getCategoryUp() != null) {
			String sql = "update category set name = ?, categoryup_id = ? where id = ?";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setString(0, category.getName());
			stmt.setLong(1, category.getCategoryUp().getId());
			stmt.setLong(2, category.getId());
			stmt.execute();
			close(stmt);
		} else {
			String sql = "update category set name = ? where id = ?";
			PreparedStatement stmt = connect().prepareStatement(sql);
			stmt.setString(0, category.getName());
			stmt.setLong(1, category.getId());
			stmt.execute();
			close(stmt);
		}
		return find(category.getId());
	}

}
