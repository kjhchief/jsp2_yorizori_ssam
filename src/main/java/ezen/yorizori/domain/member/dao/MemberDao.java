package ezen.yorizori.domain.member.dao;

import java.sql.SQLException;
import java.util.List;

import ezen.yorizori.domain.member.dto.Member;
import ezen.yorizori.web.common.RequestParams;

/**
 * 회원 Database 관련 기능 명세(역할)
 * @author 김기정
 * @Date   2023. 3. 8.
 */
public interface MemberDao {
	// 회원 가입
	public void create(Member member) throws SQLException;
	
	// 회원 인증
	public Member isMember(String id, String password) throws SQLException;
	
	// 전체 회원 목록 조회
	public List<Member> findAll() throws SQLException;
	
	// 요청 파라메터에 따른 회원 목록 조회
	public List<Member> findByParams(RequestParams params) throws SQLException;
	
	// 요청 파라메터에 따른 회원수 조회
	public int getCountByParams(RequestParams params) throws SQLException;
		
	// 회원 삭제
	
	// 회원 정보 수정

}






