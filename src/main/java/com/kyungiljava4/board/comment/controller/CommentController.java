package com.kyungiljava4.board.comment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kyungiljava4.board.comment.domain.Comment;
import com.kyungiljava4.board.comment.service.CommentService;

@Controller
@RequestMapping("/comments")
public class CommentController {
	@Autowired
	CommentService commentService;
	
	@GetMapping
	@ResponseBody
	public List<Comment> getComments(@RequestParam Map<String, String> map) {
		return commentService.getComments(Integer.parseInt(map.get("boardId")), 0);
	}

	@PostMapping("add")
	public String add(@RequestParam Map<String, String> map) {
		Comment comment = new Comment(map.get("content"), 
				Integer.parseInt(map.get("user_id")),
				Integer.parseInt(map.get("board_id")));
		commentService.add(comment);
		return "redirect:/detail/" + map.get("board_id");
	}


}
