package ezen.yorizori.domain.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ezen.yorizori.domain.common.factory.DaoFactory;
import ezen.yorizori.domain.member.dto.Member;
import ezen.yorizori.web.common.RequestParams;

public class JdbcMemberDao implements MemberDao {

	private DataSource dataSource;

	public JdbcMemberDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void create(Member member) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" INSERT INTO member").append(" VALUES(?, ?, ?, ?, ?, SYSDATE)");
		try {
			// 커넥션을 풀링하고 있는 커넥션 팩토리부터 사용하지 않고 있는 커넥션 획득
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.setInt(5, member.getAge());
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw e;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				// 사용후 커넥션 반납 (닫는 것 아님)
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public Member isMember(String id, String password) throws SQLException {
		Member loginMember = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT member_id, name, email, age, regdate").append(" FROM member")
				.append(" WHERE member_id= ? AND password = ?");

		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				loginMember = makeMember(rs);
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return loginMember;
	}

	@Override
	public List<Member> findAll() throws SQLException {
		List<Member> list = new ArrayList<>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT member_id, name, email, age, regdate")
		   .append(" FROM member")
				.append(" ORDER BY regdate DESC");
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Member member = makeMember(rs);
				list.add(member);
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return list;
	}

	private Member makeMember(ResultSet rs) throws SQLException {
		Member member = new Member();
		member.setId(rs.getString("member_id"));
		member.setName(rs.getString("name"));
		member.setEmail(rs.getString("email"));
		member.setAge(rs.getInt("age"));
		member.setRegdate(rs.getDate("regdate"));
		return member;
	}

	@Override
	public List<Member> findByParams(RequestParams params) throws SQLException {
		List<Member> list = new ArrayList<Member>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT member_id, name, email, age, regdate")
		  .append(" FROM ( SELECT CEIL(ROWNUM / ?) page, member_id, name, email, age, regdate")
		  .append("        FROM   ( SELECT member_id, name, email, age, regdate")
		  .append("                 FROM member");
		
		// 검색 존재 여부에 따라 WHERE 동적 추가(동적 SQL)
		if(params.getSearch() != null) {
			sb.append("             WHERE member_id = ? OR name LIKE ?");
		}

		sb.append("                 ORDER  BY regdate DESC))")
		  .append("WHERE  page = ?");

		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setInt(1, params.getElementSize());
			
			if(params.getSearch() != null) {
				pstmt.setString(2, params.getSearch());
				pstmt.setString(3, "%"+params.getSearch()+"%");
				pstmt.setInt(4, params.getRequestPage());
			}else {
				pstmt.setInt(2, params.getRequestPage());
			}
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Member member = makeMember(rs);
				list.add(member);
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return list;
	}
	
	@Override
	public int getCountByParams(RequestParams params) throws SQLException {
		int count = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT COUNT(*) cnt")
		  .append(" FROM member");
		// 검색 존재 여부에 따라 WHERE 동적 추가(동적 SQL)
		if(params.getSearch() != null) {
			sb.append(" WHERE member_id = ? OR name LIKE ?");
		}
		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			
			if(params.getSearch() != null) {
				pstmt.setString(1, params.getSearch());
				pstmt.setString(2, "%"+params.getSearch()+"%");
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt("cnt");				
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	

	// 테스트를 위한 임시 main
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		MemberDao dao = DaoFactory.getInstance().getMemberDao();
		Member loginMember = dao.isMember("bangry", "1111");
		// 비회원인 경우
		if (loginMember == null) {
			System.out.println("회원이 아닌가벼...");
		} else {
			System.out.println(loginMember.toString());
		}

		// 전체목록 조회
		List<Member> list = dao.findAll();
		System.out.println(list);
		
		// 요청 파라메터에 따른 회원수 조회
		RequestParams params =  new RequestParams(1, 10, 10, "테스터");
		System.out.println(dao.getCountByParams(params));
		
		// 요청 파라메터에 따른 회원 목록 조회
		List<Member> list2 = dao.findByParams(params);
		System.out.println(list2);
		

	}

	

}
