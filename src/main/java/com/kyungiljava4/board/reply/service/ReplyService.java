package com.kyungiljava4.board.reply.service;

import com.kyungiljava4.board.reply.dao.ReplyDAO;
import com.kyungiljava4.board.reply.domain.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {

    @Autowired
    private ReplyDAO replyDAO;

    public void addReply(Reply reply) {
        replyDAO.addReply(reply);
    }

    public List<Reply> getRepliesByBoardId(int boardId) {
        return replyDAO.getRepliesByBoardId(boardId);
    }

    public List<Reply> getRepliesByReplyId(int replyId) {
        return replyDAO.getRepliesByReplyId(replyId);
    }


}