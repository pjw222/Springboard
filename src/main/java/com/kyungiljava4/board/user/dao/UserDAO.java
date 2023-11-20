package com.kyungiljava4.board.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kyungiljava4.board.user.domain.User;

@Repository
public class UserDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> mapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			return new User(rs.getInt("id"), rs.getString("user_id"), rs.getString("password"), rs.getString("name"),
					rs.getString("phone"), rs.getString("address"), rs.getString("email"), rs.getString("git_address"),
					rs.getInt("gender") == 1, rs.getDate("birth"), rs.getTimestamp("created_at"));
		}
	};

	public void regist(User user) {
		int genderValue = user.isGender() ? 1 : 0;
		jdbcTemplate.update(
				"insert into users (\"user_id\"," + "\"password\"," + "\"name\", " + "\"phone\"," + "\"address\","
						+ "\"email\"," + "\"git_address\"," + "\"gender\"," + "\"birth\") values(?,?,?,?,?,?,?,?,?)",
				user.getUserId(), user.getPassword(), user.getName(), user.getPhone(), user.getAddress(),
				user.getEmail(), user.getGitAddress(), genderValue, user.getBirth());
	}

	  public User get(int id) {
	        return jdbcTemplate.queryForObject("select * from users where \"id\"=?", new Object[]{id}, mapper);
	    }

    public User get(String userId) {
        return jdbcTemplate.queryForObject("select * from users where \"user_id\"=?", new Object[]{userId}, mapper);
    }

	public List<User> getAll() {
		return jdbcTemplate.query("select * from users order by \"id\"", mapper);
	}
}
