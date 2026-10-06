package com.mnu.sample.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnu.sample.dto.BoardRequestDTO;
import com.mnu.sample.dto.BoardResponseDTO;
import com.mnu.sample.entity.BoardEntity;
import com.mnu.sample.repository.BoardRepository;
import com.mnu.sample.service.BoardService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Board")
public class BoardController {
	
	private final BoardRepository boardRepository;
	//로그 출력용
	private static final Logger log=
			LoggerFactory.getLogger(BoardController.class);
	private final BoardService boardService;
	// 생성자는 @RequiredArgsConstructor가 (boardRepository, boardService) 둘 다 주입하도록 자동 생성

	@GetMapping("board_list")
	public String boardList(Model model) {
		log.info(" Board call : board_list");
		model.addAttribute("bList", boardService.boardList());
		model.addAttribute("totCount", boardService.boardCount());
		
		return "Board/board_list";
	
	}
	
	@PostMapping("board_list")
	public String boardListSearch(@RequestParam("search") String search,
			@RequestParam("key") String key, Model model) {
		log.info(" Board call : board_list_Search");
		model.addAttribute("bList", boardService.boardListSearch(search, key));
		model.addAttribute("totCount", boardService.boardCountSearch(search, key));
		model.addAttribute("search", search); //검색 조건 유지
		model.addAttribute("key", key); //검색어 유지
		
		return "Board/board_list";
	
	}
	
	
	
	
	
	// 글쓰기 화면
	@GetMapping("board_write")
	public String boardWriteForm() {
		log.info("board call : board_write");
		return "Board/board_write";
	}

	// 글쓰기 처리
	@PostMapping("board_write")
	public String boardWrite(BoardRequestDTO board) {
		log.info("board call : board_write_pro");
		boardService.boardWrite(board);
		return "redirect:/Board/board_list";
	}
	//뷰 처리
	@GetMapping("board_view")
	public String boardView(@RequestParam("idx") int idx, Model model) {
		BoardResponseDTO board = boardService.boardView(idx);
		
		model.addAttribute("board",board);
		model.addAttribute("newlineChar","\n");
		model.addAttribute("page",1); //임시
		
		
		log.info("board call : board_view");
		return "Board/board_view";
	}
	
	//삭제 폼
	@GetMapping("board_delete")
	public String boardDelete(BoardRequestDTO board) {
		log.info("board call : board_delete");
//		boardService.boardDelete(board);
		return "Board/board_delete";
	}
	
	//삭제 처리
	@PostMapping("board_delete")
	public String boardDeletePro(@RequestParam("idx")int idx, @RequestParam("pass") String pass,Model model) {
		log.info("board call : board_deletePro");
		int row = boardService.boardDelete(idx, pass);
		model.addAttribute("row",row);
		return "Board/board_delete_pro"; //경고 출력용
	}
	
	//수정 폼 
	
	//수정 처리
	@Transactional
	public int boardModifyPro(int idx, BoardRequestDTO board) {
		return boardRepository.boardModify(idx,board.getSubject(), board.getContents(), board.getName());
	}
	
	//조건에 맞는 글수 카운트
	@Transactional
	public long boardCountSearch(String search,String key) {
		switch(search) {
		case "name" :
			return boardRepository.countByNameContaining(key);

		case "subject" :
			return boardRepository.countBySubjectContaining(key);

		case "contents" :
			return boardRepository.countByContentsContaining(key);
			default:
				return 0;
		}
		
	}
	
	//조건에 맞는 게시글 목록
	@Transactional
	public List<BoardResponseDTO> boardListSearch(String search, String key){
		switch(search) {
		case "name":
			return boardRepository.findByNameContaining(key)
					.stream()
					.map(BoardResponseDTO::new)
					.toList();
			//BoardRepository 결과고 넘어온 Entity stream을 map을 통해 list로 변환
		case "subject":
			return boardRepository.findBySubjectContaining(key)
					.stream()
					.map(BoardResponseDTO::new)
					.toList();
			//BoardRepository 결과고 넘어온 Entity stream을 map을 통해 list로 변환
			
		case "contents":
			return boardRepository.findByContentsContaining(key)
					.stream()
					.map(BoardResponseDTO::new)
					.toList();
			//BoardRepository 결과고 넘어온 Entity stream을 map을 통해 list로 변환
			
		default:
			return List.of();
		}
	}
	
	//검색 (이름,제목,내용)카운트
	long countByNameContaining(String keyword) {
		return boardRepository.countByNameContaining(keyword);
	}
	//(name like '%keyword%') 쿼리문 이라면
	long countBySubjectContaining(String keyword) {
		return boardRepository.countBySubjectContaining(keyword);
	}

	long countByContentsContaining(String keyword) {
		return boardRepository.countByContentsContaining(keyword);
	}


	//검색 목록


	//검색 (이름,제목,내용)카운트
	List<BoardEntity> findByNameContaining(String keyword) {
		return boardRepository.findByNameContaining(keyword);
	}
	//(name like '%keyword%') 쿼리문 이라면

	List<BoardEntity> findBySubjectContaining(String keyword) {
		return boardRepository.findBySubjectContaining(keyword);
	}


	List<BoardEntity> findByContentsContaining(String keyword) {
		return boardRepository.findByContentsContaining(keyword);
	}
		
	
	//페이징 인덱스
	
	
	
	
}