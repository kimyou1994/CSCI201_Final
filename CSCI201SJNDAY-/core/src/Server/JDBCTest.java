package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {

	public static void main (String[] args) {
		//check sql database for this username and if password matches
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement colorPrep = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Game?user=root&password=Devindaher10&useSSL=false");
			st = conn.createStatement();
			//String color = "red";
			// rs = st.executeQuery("SELECT * from Student where fname='" + name + "'");
			ps = conn.prepareStatement("SELECT * FROM UserInfo WHERE username=? and passwords=?");
//			ps2 = conn.prepareStatement("SELECT * FROM UserInfo WHERE username=?");
			//colorPrep = conn.prepareStatement("SELECT * FROM Color WHERE colorType=?");
			//colorPrep.setString(1, color); // set first variable in prepared statement
			ps.setString(1, "ddaher");
			ps.setString(2, "devin1");
			rs = ps.executeQuery();
			while (rs.next()) {
				String user = rs.getString("username");
				String pass = rs.getString("passwords");
				System.out.println ("username = " + user);
				System.out.println ("password = " + pass);
			}
		} catch (SQLException sqle) {
			System.out.println ("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}
}
