package com.kyungiljava4.board.comment.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kyungiljava4.board.comment.domain.Comment;
import com.kyungiljava4.board.reply.domain.Reply;

@Repository
public class CommentDAOMysql {
	@Autowired
	JdbcTemplate jdbcTemplate;

	private RowMapper<Comment> mapper = new RowMapper<Comment>() {
		@Override
		public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Comment(rs.getInt("id"), rs.getString("content"), rs.getTimestamp("created_at"),rs.getInt("is_withdrew") == 1,
					rs.getInt("user_id"),rs.getInt("board_id"),rs.getInt("comment_id"), null,rs.getString("name"));
			
		}
	};
	
	public void add(Comment comment) {
		System.out.println(comment.getBoardId());
		jdbcTemplate.update(
				"insert into comments (content, user_id, board_id, comment_id) values (?, ?, ?, ?)",
				comment.getContent(), comment.getUserId(), comment.getBoardId(), comment.getCommentId() > 0 ? comment.getCommentId() : null); 
	}
    public List<Comment> getParents(int boardId, int start) {
        return jdbcTemplate.query(
            "select comments.*, users.name from comments join users on comments.user_id= users.id where comments.board_id = ? and comments.comment_id is null order by comments.id desc limit ?, 5",
            mapper, boardId, start);
      }

      public List<Comment> getChildren(int boardId, int commentId) {
        return jdbcTemplate.query(
            "select comments.*, users.name from comments join users on comments.user_id=users.id where comments.board_id = ? and comments.comment_id = ? order by comments.id",
            mapper, boardId, commentId);
      }
      public int getCountInBoard(int boardId) {
    	  return jdbcTemplate.queryForObject("select count(*) from comments where board_id =? and comment_id is null", Integer.class,boardId);
      }
	
}
