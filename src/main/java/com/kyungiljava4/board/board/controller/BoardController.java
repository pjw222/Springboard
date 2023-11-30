package com.kyungiljava4.board.board.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kyungiljava4.board.board.domain.Board;
import com.kyungiljava4.board.board.service.BoardService;
import com.kyungiljava4.board.reply.domain.Reply;
import com.kyungiljava4.board.reply.service.ReplyService;
import com.kyungiljava4.board.user.domain.User;
import com.kyungiljava4.board.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReplyService replyService;

	@GetMapping("/")
	public String boardMainPage(Model model, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int pageSize) {

		List<Board> pagedBoards = boardService.getBoardsByPage(page, pageSize);

		int totalBoards = boardService.getTotalBoards();
		int totalPages = (int) Math.ceil((double) totalBoards / pageSize);

		model.addAttribute("title", "게시판");
		model.addAttribute("path", "/board/index");
		model.addAttribute("content", "boardFragment");
		model.addAttribute("contentHead", "boardFragmentHead");
		model.addAttribute("boards", pagedBoards);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		return "/basic/layout";
	}

	@GetMapping("/detail/{boardId}")
	public String showBoardDetail(@PathVariable("boardId") int boardId, Model model, HttpSession session) {
		Board board = boardService.get(boardId);

		Integer userId = (Integer) session.getAttribute("user");

		model.addAttribute("title", "상세보기");
		model.addAttribute("path", "/board/detail");
		model.addAttribute("content", "detailFragment");
		model.addAttribute("contentHead", "detailFragmentHead");
		board.setContent(board.getContent().replace("\n", "<br />"));
		model.addAttribute("boards", board);

		model.addAttribute("userId", userId != null ? userId : 0);

		return "/basic/layout";
	}

	@GetMapping("/delete/{id}")
	public String showBoardDelete(@PathVariable int id, Model model) {
		Board board = boardService.get(id);
		model.addAttribute("title", "삭제하기");
		model.addAttribute("path", "/board/delete");
		model.addAttribute("content", "deleteFragment");
		model.addAttribute("contentHead", "deleteFragmentHead");
		model.addAttribute("board", board);
		return "/basic/layout";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, Model model) {
		boardService.delete(id);
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String showBoardEdit(@PathVariable int id, Model model) {
		Board board = boardService.get(id);
		model.addAttribute("title", "수정하기");
		model.addAttribute("path", "/board/edit");
		model.addAttribute("content", "editFragment");
		model.addAttribute("contentHead", "editFragmentHead");
		model.addAttribute("boards", board);
		return "/basic/layout";
	}

	@PostMapping("/edit/{id}")
	public String edit(@RequestParam Map<String, String> data, @PathVariable int id, Model model) {
		String title = data.get("title");
		String content = data.get("content");
		int editId = Integer.parseInt(data.get("editId"));
		boardService.updateBoard(title, content, editId);
		return "redirect:/detail/" + editId;
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
	public String boardAdd(@RequestParam Map<String, String> data, HttpSession session, Model model) {
		try {
			int userId = (Integer) session.getAttribute("user");

			if (userId == 0) {
				model.addAttribute("msg", "로그인을 안해서 작성못해요");
				return "redirect:/";
			}
			String tempContent = data.get("content");
			tempContent = tempContent.replace("width=\"[0-9]*\"", "width=\"100%\"");
			tempContent = tempContent.replace("height=\"[0-9]*\"", "height=\"auto\"");
			boardService.add(new Board(data.get("title"), tempContent, userId));

			return "redirect:/";

		} catch (Exception e) {
			return "redirect:/";
		}
	}

	@PostMapping("/uploads/image")
	@ResponseBody
	public ModelMap uploadImage(MultipartHttpServletRequest req) {
		ModelMap model = new ModelMap();
		try {
			MultipartFile uploadFile = req.getFile("upload");
			System.out.println(uploadFile.getOriginalFilename());
			String originName = uploadFile.getOriginalFilename();
			String[] tempNames = originName.split("[.]");
//			String ext = originName.substring(originName.indexOf("."));
			String ext ="." + tempNames[tempNames.length -1];
			String randomName = UUID.randomUUID() + ext;
			String savePath = "C:\\Users\\KGA\\eclipse-workspace\\board\\src\\main\\resources\\static\\imgs\\"
					+ randomName;
			String uploadUrl = "/imgs/" + randomName;
			File file = new File(savePath);
			uploadFile.transferTo(file);
			model.addAttribute("uploaded", true);
			model.addAttribute("url",uploadUrl);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

//	@PostMapping("/addReply")
//	public String addReply(@RequestParam Map<String, String> data, Model model, HttpSession session) {
//		int userId = (Integer) session.getAttribute("user");
//		int boardId = Integer.parseInt(data.get("boardId"));
//		String comment = data.get("comment");
//		Integer parentReplyId;
//		String parentReplyIdString = data.get("parentReplyId");
//		if (parentReplyIdString == null || parentReplyIdString.isEmpty()) {
//			parentReplyId = null;
//		} else {
//			parentReplyId = Integer.parseInt(parentReplyIdString);
//		}
//
//		Reply reply = new Reply();
//		reply.setBoardId(boardId);
//		reply.setUserId(userId);
//		reply.setContent(comment);
//		reply.setReplyId(parentReplyId);
//
//		replyService.addReply(reply);
//
//		return "redirect:/detail/" + boardId;
//	}

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

//	@GetMapping("/")
//	public String boardMainPage(Model model) {
//		model.addAttribute("title", "게시판");
//		model.addAttribute("path", "/board/index");
//		model.addAttribute("content", "boardFragment");
//		model.addAttribute("contentHead", "boardFragmentHead");
//		model.addAttribute("boards", boardService.getAll());
//		return "/basic/layout";
//	}
//	@GetMapping("/")
//	public String boardMainPage(Model model) {
//		List<Board> boards = boardService.getAll();
////		List<String> user = boardService.matchUserId();
//		model.addAttribute("boards", boards);
////		model.addAttribute("userList",user);
//		model.addAttribute("title", "게시판");
//		model.addAttribute("path", "/board/index");
//		model.addAttribute("content", "boardFragment");
//		model.addAttribute("contentHead", "boardFragmentHead");
//		
//		return "/basic/layout";
//	}
}
