package ezen.yorizori.web.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ezen.yorizori.domain.member.dto.Member;
import ezen.yorizori.domain.member.service.MemberService;
import ezen.yorizori.domain.member.service.MemberServiceImpl;
import ezen.yorizori.web.common.Pagination;
import ezen.yorizori.web.common.RequestParams;
import ezen.yorizori.web.common.YZRuntimeException;

/**
 * 회원 목록 요청 처리
 */
@WebServlet("/member/list.do")
public class ListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	// 비즈니스 객체 사용
	private MemberService memberService = new MemberServiceImpl();
	
	/**
	 * 회원 목록 처리
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 사용자인 경우 서비스
		// 만약 비로그인 경우 안내메시지 출력
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("loginMember");
		
		// 로그인 중이면
		if(member != null) {
			// 페이징 처리와 관련된 변수
			int requestPage = 1;
			int elementSize = 2; // 한 화면에 보여주는 요소의 수
			int pageSize = 5; // 페이지 번호 한 화면에 몇 개씩 보여주나
			String search = null;
			
			String selectPage = request.getParameter("page");
			if (selectPage != null && !selectPage.equals("")) {
				requestPage = Integer.parseInt(selectPage);
			}
			search = request.getParameter("search");
			if(search != null && search.equals("")) {
				search = null;
			}
			
			// 여러개의 요청 파라메터 정보 저장
			RequestParams params = new RequestParams(requestPage, elementSize, pageSize, search);
			
//			List<Member> list = memberService.getMembers();
			List<Member> list = memberService.getMembers(params);
			
			int selectCount = memberService.getMemberCount(params);
			// 페이지 계산 유틸리티
			Pagination pagination = new Pagination(params, selectCount);
			
			Map<String, Object>  page = new HashMap<>();
			page.put("params", params);         // 요청 파라메터
			page.put("list", list);             // 검색 목록
			page.put("pagination", pagination); // 페이징 계산 결과
			request.setAttribute("page", page);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/views/member/list.jsp");
			rd.forward(request, response);
		}else {
			throw new YZRuntimeException("회원목록은 로그인 사용자만 허용하는 페이지입니다.", "/member/list.do");
		}
		
	}

}












