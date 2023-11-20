package com.kyungiljava4.board.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kyungiljava4.board.board.dao.BoardDAO;
import com.kyungiljava4.board.board.domain.Board;

@Service
public class BoardService {
    @Autowired
    private BoardDAO boardDAO;

    public void add(Board board) {
        boardDAO.add(board);
    }

    public List<Board> getAll() {
    	List<Board> board = boardDAO.getAll();	
        return board;
    }
}