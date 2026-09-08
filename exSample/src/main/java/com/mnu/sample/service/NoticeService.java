package com.mnu.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mnu.sample.domain.NoticeDTO;
import com.mnu.sample.domain.PageSearchDTO;
import com.mnu.sample.mapper.NoticeMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class NoticeService {
	
	@Autowired
	private NoticeMapper noticeMappers;
	
	public List<NoticeDTO> noticeTopList(int num){
		
		return noticeMappers.noticeTopList(num);
	}
	
	//1. 전체 공지사항  리스트
		public int noticeCount() {
			
			return noticeMappers.noticeCount();
			
		}
		//2. 검색조건에 맞는 공지사항  리스트
		public int noticeSearchCount(PageSearchDTO pageSearchDTO) {
			
			return noticeMappers.noticeSearchCount(pageSearchDTO);
			
		}
		//3. 공지사항 목록(검색+페이지) 겸용
		public List<NoticeDTO> noticeList(PageSearchDTO pageSearchDTO){
			
			return noticeMappers.noticeList(pageSearchDTO);
		}
		//4. idx에 해당하는 해당하는 글 목록(view, modify) 사용
		public NoticeDTO noticeSelect(
		        int idx,
		        HttpServletRequest request,
		        HttpServletResponse response) {

		    // 쿠키 조회
		    boolean bool = false;
		    Cookie info = null;

		    Cookie[] cookies = request.getCookies();

		    if(cookies != null) {

		        for(int i = 0; i < cookies.length; i++) {

		            info = cookies[i];

		            if(info.getName().equals("noticeCookie" + idx)) {
		                bool = true;
		                break;
		            }
		        }
		    }

		    // 쿠키가 없다면 조회수 증가
		    if(!bool) {

		        // 조회수 +1
		        noticeMappers.noticeHitUpdate(idx);

		        // 쿠키 생성
		        String str = "" + System.currentTimeMillis();

		        info = new Cookie("noticeCookie" + idx, str);

		        // 5분
		        info.setMaxAge(60 * 5);

		        response.addCookie(info);
		    }

		    // 게시글 조회
		    NoticeDTO notice = noticeMappers.noticeSelect(idx);

		    notice.setContents(
		        notice.getContents().replace("\n", "<br>")
		    );

		    return notice;
		}
		
	

}
