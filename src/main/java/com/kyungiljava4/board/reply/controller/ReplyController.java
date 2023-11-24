package com.kyungiljava4.board.reply.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.kyungiljava4.board.reply.service.ReplyService;

@Controller
public class ReplyController {
	@Autowired
	private ReplyService replyService;
	
//	@PostMapping("addReply")
//	public String addReply() {
//		return "/";
//	}
}
