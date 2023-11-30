package com.kyungiljava4.board.user.service;

import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kyungiljava4.board.user.dao.UserDAO;
import com.kyungiljava4.board.user.dao.UserDAOMysql;
import com.kyungiljava4.board.user.domain.User;

@Service
public class UserService {
	@Autowired
	private UserDAOMysql userDAO;
	public void regist(User user) {
		user.setPassword(cryptoPassword(user.getPassword()));
		userDAO.regist(user);
		System.out.println(user);
	}
	public int getUserNameById(int userId) {
	    User user = userDAO.get(userId);
	    return user.getId();
	}

	public List<User> getAll(){
		return userDAO.getAll();
	}
	public User login(User user) {
		User tempUser = userDAO.get(user.getUserId());
		if (tempUser != null && tempUser.getPassword().equals(cryptoPassword(user.getPassword()))) {
			return tempUser;
		} else
			return null;
	}
	private String cryptoPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] sha256Hash = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : sha256Hash) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
