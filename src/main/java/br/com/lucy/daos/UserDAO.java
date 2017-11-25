package br.com.lucy.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.lucy.models.EType;
import br.com.lucy.models.User;
import br.com.lucy.vos.UserVO;

public class UserDAO extends ConnectionDAO implements DAO<User> {

	@Override
	public User save(User user) throws SQLException {
		if(user == null){
			return null;
		}else if(user.getId() != null){
			return user;
		}
		user.setId(nextId());
		String sql = "insert into users (id, name, email, password, type) values (?,?,?,?,?)";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setLong(1, user.getId());
		stmt.setString(2, user.getName());
		stmt.setString(3, user.getEmail());
		stmt.setString(4, user.getPassword());
		stmt.setLong(5, user.getType().ordinal());
		stmt.execute();
		close(stmt);
		return find(user.getId());
	}
	
	public void login(User user, UserVO vo) throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select id, name, email, password, type from users where email = ? and password = ?");
		stmt.setString(1, user.getEmail());
		stmt.setString(2, user.getPassword());
		ResultSet rs = stmt.executeQuery();
		User selectedUser = new User();
		while (rs.next()) {
			selectedUser.setId(rs.getLong(1));
			selectedUser.setName(rs.getString(2));
			selectedUser.setEmail(rs.getString(3));
			selectedUser.setPassword(rs.getString(4));
			selectedUser.setType(EType.values()[rs.getInt(5)]);
		}
		close(rs, stmt);
		vo.toVO(selectedUser);
	}
	
	public User findByEmail(String email) throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select id, name, email, password, type from users where email = ?");
		stmt.setString(1, email);
		ResultSet rs = stmt.executeQuery();
		User user = new User();
		while (rs.next()) {
			user.setId(rs.getLong(1));
			user.setName(rs.getString(2));
			user.setEmail(rs.getString(3));
			user.setPassword(rs.getString(4));
			user.setType(EType.values()[rs.getInt(5)]);
		}
		close(rs, stmt);
		return user;
	}

	@Override
	public User find(Long id) throws SQLException {
		if(id == null)
			return null;
		PreparedStatement stmt = connect().prepareStatement("select id, name, email, password, type from users where id = ?");
		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		User user = new User();
		while (rs.next()) {
			user.setId(rs.getLong(1));
			user.setName(rs.getString(2));
			user.setEmail(rs.getString(3));
			user.setPassword(rs.getString(4));
			user.setType(EType.values()[rs.getInt(5)]);
		}
		close(rs, stmt);
		return user;
	}

	@Override
	public List<User> list() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long nextId() throws SQLException {
		PreparedStatement stmt = connect().prepareStatement("select max(id) from users");
		ResultSet rs = stmt.executeQuery();
		Long id = 0L;
		while (rs.next()) {
			id = rs.getLong(1);
		}
		close(rs, stmt);
		return id + 1;
	}

	@Override
	public User update(User user) throws SQLException {
		if(user == null){
			return null;
		}else if(user.getId() == null){
			return save(user);
		}
		String sql = "update users set name = ?, email = ?, password = ?, type = ?) where id = ?";
		PreparedStatement stmt = connect().prepareStatement(sql);
		stmt.setString(0, user.getName());
		stmt.setString(1, user.getEmail());
		stmt.setString(2, user.getPassword());
		stmt.setLong(3, user.getType().ordinal());
		stmt.setLong(4, user.getId());
		stmt.execute();
		close(stmt);
		return find(user.getId());
	}

}
