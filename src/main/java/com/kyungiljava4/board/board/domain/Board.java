package com.kyungiljava4.board.board.domain;

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
public class Board {
    private int id;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private int views = 0;
    private int likes = 0;
    private int hates = 0;
    private Timestamp createdAt; 
    private boolean isWithdrawn = false;
    @NonNull
    private int userId;
    
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
