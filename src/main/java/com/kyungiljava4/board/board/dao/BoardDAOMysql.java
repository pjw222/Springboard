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
public class BoardDAOMysql {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Board> mapper = new RowMapper<Board>() {

		@Override
		public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			return new Board(rs.getInt("id"), rs.getString("title"), rs.getString("content"), rs.getInt("views"), 0, 0,
					rs.getTimestamp("created_at"), rs.getInt("is_withdrew") == 1, rs.getInt("user_id"),
					rs.getString("name"), rs.getString("git_address"));
		}
	};
	public void add(Board board) {
		jdbcTemplate.update(
				"insert into boards ( title, content, is_withdrew, user_id) values ( ?, ?, ?, ? )",
				board.getTitle(), board.getContent(), 0, board.getUserId());
	}

	public Board get(int id) {
		return jdbcTemplate.queryForObject(
				"select a.*, b.name,b.git_address from boards a join users b on a.user_id = b.id where a.id = ?",
				mapper, id);
	}

	public List<Board> getAll() {
		return jdbcTemplate.query(
				"select boards.*, users.name,users.git_address from boards join users on boards.user_id=users.id order by boards.id desc limit ?,?",
				mapper);
	}

	public List<Board> getBoardsByPage(int offset, int pageSize) {
		String sql = "select boards.*, users.name,users.git_address from boards join users on boards.user_id=users.id order by boards.id desc limit ?,?";
		return jdbcTemplate.query(sql, mapper, offset, pageSize);
	}

	public int getTotalBoards() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM boards", Integer.class);
	}

	public void update(String title, String content, int id) {
		jdbcTemplate.update("update boards set title=?, content=? where id=?", title, content, id);
	}

	public void delete(int id) {
		jdbcTemplate.update("delete from boards where id = ?", id);
	}

	public int getCount() {
		return jdbcTemplate.queryForObject("select count(*) from boards ", Integer.class);
	}
}