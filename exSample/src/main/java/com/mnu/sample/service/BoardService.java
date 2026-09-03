package com.mnu.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.BoardDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.BoardMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class BoardService {
	@Autowired
	private BoardMapper Boardmapper;
	
	//메소드 정의
	//1. 전체 글 카운터
	public int BoardCount() {
		return Boardmapper.BoardCount();
	}

	//2. 검색 조건에 해당하는 글수
	public int BoardCountSearch(String search, String key) {

		return Boardmapper.BoardCountSearch(search, key);
	}

	//3. 전체 목록
	public List<BoardDTO> BoardList(){
		return Boardmapper.BoardList();
		
	}
	//3-1. 전체 목록 (패이지 인덱싱)
	public List<BoardDTO> BoardListPage(PageSearchDTO pagesearchDTO){
		
		return Boardmapper.BoardListPage(pagesearchDTO);
	}

	//4. 검색조건에 맞는 목록
	public List<BoardDTO> BoardListSearch(String search, String key){
		return Boardmapper.BoardListSearch(search, key);
	}
	
	//4-1. 검색조건에 맞는 목록(페이지 인덱싱)
	public List<BoardDTO> BoardListSearchPage(PageSearchDTO pagesearchDTO){
		return Boardmapper.BoardListSearchPage(pagesearchDTO);
	}



	//5. 글 등록
	public int BoardWrite(BoardDTO boardDTO) {
		return Boardmapper.BoardWrite(boardDTO);
	}

	//6. 특정글 검색 (view, modify)
	public BoardDTO boardview(int idx, HttpServletRequest request, HttpServletResponse response) {
		//쿠키체크 (조회수 중복 증가 방지)
		boolean bool = false;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("boardCookie" + idx)) {
					bool = true;
					break;
				}
			}
		}
		String str = "" + System.currentTimeMillis();
		if(!bool) {
			//쿠키가 없을 때만 조회수 증가 + 쿠키생성
			
			
			Cookie info = new Cookie("boardCookie" + idx, str);
//			info.setMaxAge(60 * 60 * 24); //하루동안 유지
			info.setMaxAge(60 * 5); //5분동안 유지
			response.addCookie(info);
			Boardmapper.BoardHits(idx);
		}

		BoardDTO board = Boardmapper.boardview(idx);
		board.setContents(board.getContents().replace("\n", "<br>"));

		return board;
	}
	
	

	//7. 수정(폼)
	
	public BoardDTO boardModify(int idx) {
		return Boardmapper.boardview(idx);
	}
	//7. 수정처리(폼)
	
	public int boardModifyPro(BoardDTO boardDTO) {
		return Boardmapper.boardModifyPro(boardDTO);
	}

	//8. 삭제처리
	public int boardDeletePro(int idx, String pass) {
		return Boardmapper.boardDeletePro(idx, pass);
	}

}
