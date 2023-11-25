package com.kyungiljava4.board.reply.domain;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Reply {
	private int id;
	@NonNull
	private String content;
	private Timestamp createdAt;
	private Integer replyId;
	private int userId;
	private int boardId;
	private String userName;
}
