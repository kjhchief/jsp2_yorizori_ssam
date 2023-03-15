package ezen.yorizori.domain.common.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import ezen.yorizori.domain.member.dao.JdbcMemberDao;
import ezen.yorizori.domain.member.dao.MemberDao;

/**
 * 다양한 DAO 객체 생성 팩토리  
 */
public class DaoFactory{
	
	private static DaoFactory factory = new DaoFactory();
	
	private String driver;
	private String url;
	private String username;
	private String password;
	
	// 다양한 영속성 데이터 접근을 위한 표준 명세
	private DataSource dataSource;
	
	private DaoFactory() {
		loadProperties();
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setInitialSize(5);     // 풀의 초기 커넥션 개수
		ds.setMaxTotal(100);       // 최대 커넥션 개수
		ds.setMaxIdle(5);         // Idle 상태에 풀이 소유하는 최대 커넥션 개수
		ds.setMaxWaitMillis(1000); // 커넥션이 존재하지 않을 때 커넥션 획득에 대기할 시간
		dataSource = ds;
	}
	
	private void loadProperties() {
		Properties prop = new Properties();
		InputStream in = getClass().getResourceAsStream("/config/jdbc.properties");
		try {
			prop.load(in);
			this.driver = prop.getProperty("database.driver");
			this.url = prop.getProperty("database.url");
			this.username = prop.getProperty("database.username");
			this.password = prop.getProperty("database.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static DaoFactory getInstance() {
		return factory;
	}
	
	public MemberDao getMemberDao() {
		// 피자 생성 및 토핑
		return new JdbcMemberDao(dataSource);
//		return new MybatisMemberRepository(dataSource);
	}
	
	/*
	public BoardDao getBoardDao() {
		return new JdbcBoardDao(dataSource);
	};
	*/
	
}





