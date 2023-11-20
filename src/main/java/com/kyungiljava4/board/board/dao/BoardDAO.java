package com.kyungiljava4.board.board.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kyungiljava4.board.board.domain.Board;



@Repository
public class BoardDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Board> mapper = new RowMapper<Board>() {
		@Override
		public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			return new Board(rs.getInt("id"),
			        rs.getString("title"),
			        rs.getString("content"), 
			        rs.getInt("views"),
			        0,
			        0,
			        rs.getTimestamp("created_at"),
			        rs.getInt("is_withdrew")==0,
			        rs.getInt("user_id"));
		}
	};
    public void add(Board board) {
        String sql = "insert into boards (\"title\", \"content\") values (?, ?)";
        jdbcTemplate.update(sql, board.getTitle(), board.getContent());
    }

	public List<Board> getAll() {
		return jdbcTemplate.query("select * from boards order by \"id\"", mapper);
	}
}
