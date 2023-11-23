package com.kyungiljava4.board.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kyungiljava4.board.board.dao.BoardDAO;
import com.kyungiljava4.board.board.domain.Board;
import com.kyungiljava4.board.user.dao.UserDAO;
import com.kyungiljava4.board.user.domain.User;
import com.kyungiljava4.board.user.service.UserService;

@Service
public class BoardService {
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserService userService;
	
	public void add(Board board) {
		boardDAO.add(board);
	}
	public Board get(int id) {
		Board board = boardDAO.get(id);
		return board;
	}
    public List<Board> getBoardsByPage(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return boardDAO.getBoardsByPage(offset, pageSize);
    }

    public int getTotalBoards() {
        return boardDAO.getTotalBoards();
    }

	public List<Board> getAll() {
		List<Board> board = boardDAO.getAll();
		return board;
	}
	public void updateBoard(String title, String content, int id) {
		boardDAO.update(title,content,id);
	}
	public void delete(int id) {
		boardDAO.delete(id);
	}


//	public List<String> matchUserId() {
//	    List<Board> list = boardDAO.getAll();
//	    List<String> userList = new ArrayList<>(list.size());
//
//	    for (Board board : list) {
//	        User user = userDAO.get(board.getUserId());
//	        if (user != null) {
//	            userList.add(user.getName());
//	        }
//	    }
//	    return userList;
//	}
}
/*
 * 
 * @Service public class BoardService {
 * 
 * @Autowired BoardDAO boardDAO;
 * 
 * public void add(Board board) { boardDAO.add(board); }
 * 
 * public List<Board> getAll() { return boardDAO.getAll(); } }
 * 
 * 
 * 
 * 
 */
