package book.spring.toby.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import book.spring.toby.user.domain.User;

public class UserDao {
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection c=getConnection();
		PreparedStatement ps=c.prepareStatement("insert into users(id,name,password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		ps.close();
		c.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection c=getConnection();
		PreparedStatement ps=c.prepareStatement("select * from users where id=?");
		ps.setString(1, id);
		ResultSet rs=ps.executeQuery();
		User user=null;
		if(rs.next()) {
			user=new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		rs.close();
		ps.close();
		c.close();
		return user;
	}
	
	//공통부분인 Connection 분리
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/toby?useUnicode=true&characterEncoding=utf8&verifyServerCertificate=false&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true","scott","tiger");
		return c;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao=new UserDao();
		
		User user=new User();
		user.setId("test3");
		user.setName("test name");
		user.setPassword("test password");

		dao.add(user);
		
		System.out.println(user.toString()+"UserDao.add 성공");
		
		User user2=dao.get(user.getId());
		System.out.println(user2.toString()+"UserDao.get 성공");
	}
}