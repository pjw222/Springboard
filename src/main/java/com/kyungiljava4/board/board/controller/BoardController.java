package com.kyungiljava4.board.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kyungiljava4.board.board.domain.Board;
import com.kyungiljava4.board.board.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;

	@GetMapping("/")
	public String boardMainPage(Model model) {
		List<Board> boards = boardService.getAll();
		List<String> user = boardService.matchUserId();
		model.addAttribute("boards", boards);
		model.addAttribute("userList",user);
		model.addAttribute("title", "게시판");
		model.addAttribute("path", "/board/index");
		model.addAttribute("content", "boardFragment");
		model.addAttribute("contentHead", "boardFragmentHead");
		
		return "/basic/layout";
	}
	@GetMapping("/notice")
	public String noticePage(Model model) {
		List<Board> boards = boardService.getAll();
		model.addAttribute("boards", boards);
		model.addAttribute("title", "공지사항");
		model.addAttribute("path", "/board/notice");
		model.addAttribute("content", "noticeFragment");
		model.addAttribute("contentHead", "noticeFragmentHead");
		return "/basic/layout";
	}

	@PostMapping("/add")
	public String boardAdd(@RequestParam Map<String, String> data, HttpSession session,Model model) {
		try {
		int userId = (Integer)session.getAttribute("user"); 

		if(userId == 0) {
			model.addAttribute("msg","로그인을 안해서 작성못해요");
			return "redirect:/";
		}
		
		boardService.add(new Board( data.get("content"), data.get("title"),userId));
		
		
		return "redirect:/";
			
		}catch(Exception e) {
			return "redirect:/";
		}	
	}


//	@GetMapping("/")
//	public String boardMainPage(Model model) {
//	    List<Board> boards = boardService.getAll();
//	    model.addAttribute("boards", boards);
//		model.addAttribute("title", "게시판");
//		model.addAttribute("path", "/board/index");
//		model.addAttribute("content", "boardFragment");
//		model.addAttribute("contentHead", "boardFragmentHead");
//		
//		return "/basic/layout";
//	}
//	@GetMapping("/index")
//	public String boardPage(Model model) {
//	    List<Board> boards = boardService.getAll();
//	    model.addAttribute("boards", boards);
//	    model.addAttribute("title", "게시판");
//	    model.addAttribute("path", "/board/index");
//	    model.addAttribute("content", "boardFragment");
//	    model.addAttribute("contentHead", "boardFragmentHead");
//	    return "/basic/layout";
//	}

//	@PostMapping("/board/create")
//	public String addBoard(@RequestParam String title, @RequestParam String content) {
//		Board board = new Board(title, content);
//		boardService.add(board);
//		return "redirect:/";
//	}
}
