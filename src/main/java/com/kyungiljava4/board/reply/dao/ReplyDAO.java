package com.kyungiljava4.board.reply.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kyungiljava4.board.reply.domain.Reply;

@Repository
public class ReplyDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Reply> mapper = new RowMapper<Reply>() {
		@Override
		public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Reply(rs.getInt("id"), rs.getString("content"), rs.getTimestamp("created_at"),
					rs.getInt("reply_id"), rs.getInt("user_id"), rs.getInt("board_id"), rs.getString("userName"));
		}
	};

	public void addReply(Reply reply) {
		jdbcTemplate.update(
				"insert into reply (\"content\", \"user_id\", \"board_id\", \"reply_id\") values (?, ?, ?, ?)",
				reply.getContent(), reply.getUserId(), reply.getBoardId(), reply.getReplyId());
	}

	public List<Reply> getRepliesByBoardId(int boardId) {
		return jdbcTemplate.query("SELECT * FROM reply WHERE \"board_id\" = ?", mapper, boardId);
	}

	public List<Reply> getRepliesByReplyId(int replyId) {
		return jdbcTemplate.query("SELECT * FROM reply WHERE \"reply_id\" = ?", mapper, replyId);
	}
}
