package com.kyungiljava4.board.user.domain;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    private int id;
    @NonNull
    private String userId;
    @NonNull
    private String password;
    @NonNull
    private String name;
    @NonNull
    private String phone;
    private String address;
    @NonNull
    private String email;
    private String gitAddress;
    private boolean gender = false;
    private Date birth;
    private Timestamp createdAt;
}