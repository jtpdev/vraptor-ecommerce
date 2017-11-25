package br.com.lucy.daos;

import java.sql.SQLException;
import java.util.List;

public interface DAO<C> {

	public C save(C object) throws SQLException;
	
	public C update(C object) throws SQLException;

	public C find(Long id) throws SQLException;

	public List<C> list() throws SQLException;

	public void delete(Long id) throws SQLException;

	public Long nextId() throws SQLException;

}
