package com.kyungiljava4.board.comment.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseComment {
	private List<Comment> list;
	private boolean isEnd;
}
