package com.kyungiljava4.board.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kyungiljava4.board.comment.dao.CommentDAO;
import com.kyungiljava4.board.comment.domain.Comment;

@Service
public class CommentService {
	@Autowired
	CommentDAO commentDAO;
	
	public void add(Comment commnet) {
		commentDAO.add(commnet);
	}
	public List<Comment> getComments(int boardId, int start){
		List<Comment> list = commentDAO.getParents(boardId, start);
//		for(int i = 0; i<list.size();++i) {
//			list.get(i).setChildren(commentDAO.getChildren(boardId, list.get(i).getId()));
//		}
		list.forEach((item)->{item.setChildren(getChildren(boardId,item));
		});
		
		return list;
	}
	
	private List<Comment> getChildren(int boardId,Comment comment){
		List<Comment> list = commentDAO.getChildren(boardId, comment.getId());
	    list.forEach((item)->{item.setChildren(getChildren(boardId,item));
	    });
		return list;
	}
	public int getCountInBoard(int boardId) {
		return commentDAO.getCountInBoard(boardId);
	}
	
}
