package com.kyungiljava4.board.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kyungiljava4.board.board.domain.Board;
import com.kyungiljava4.board.board.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;

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
    @GetMapping("/detail/{id}")
    public String showBoardDetail(@PathVariable int id, Model model) {
        Board board = boardService.get(id);
        model.addAttribute("title", "상세보기");
	    model.addAttribute("path", "/board/detail");
	    model.addAttribute("content", "detailFragment");
	    model.addAttribute("contentHead", "detailFragmentHead");
        model.addAttribute("boards", board);
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
	public String edit(@RequestParam Map<String, String> data,@PathVariable int id, Model model) {
		String title = data.get("title");
		String content = data.get("content");
		int editId = Integer.parseInt(data.get("editId"));
		boardService.updateBoard(title, content, editId);		
		return "redirect:/detail/"+editId;
	}

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

			boardService.add(new Board(data.get("title"), data.get("content"), userId));

			return "redirect:/";

		} catch (Exception e) {
			return "redirect:/";
		}
	}

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
}
