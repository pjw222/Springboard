package com.kyungiljava4.board.comment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kyungiljava4.board.comment.domain.Comment;
import com.kyungiljava4.board.comment.domain.ResponseComment;
import com.kyungiljava4.board.comment.service.CommentService;

@Controller
@RequestMapping("/comments")
public class CommentController {
	@Autowired
	CommentService commentService;
	
	@GetMapping
	@ResponseBody
	public ResponseComment getComments(@RequestParam Map<String, String> map) {
		ResponseComment res = new ResponseComment(
				commentService.getComments(Integer.parseInt(map.get("boardId")), 
						Integer.parseInt(map.get("start"))),
						commentService.getCountInBoard(Integer.parseInt(map.get("boardId"))) <= Integer.parseInt(map.get("start")) + 5);

		return res;
	}
	@GetMapping("th")
	public String getComments(Model model,@RequestParam Map<String, String> map) {
		model.addAttribute("commentList",commentService.getComments(Integer.parseInt(map.get("boardId")), 0));
		return "comment/list";
	}

	@PostMapping("add")
	public String add(@RequestParam Map<String, String> map) {
		Comment comment = new Comment(map.get("content"), 
				Integer.parseInt(map.get("user_id")),
				Integer.parseInt(map.get("board_id")));
		if(map.get("comment_id") != null) {			
			comment.setCommentId(Integer.parseInt(map.get("comment_id")));
		}
		System.out.println(map.get("comment_id"));
		commentService.add(comment);
		return "redirect:/detail/" + map.get("board_id");
	}


}
