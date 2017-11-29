package com.mygdx.game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCBackup {

//	public static void main (String[] args) {
//		Connection conn = null;
//		Statement st = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/Game?user=root&password=Devindaher10&useSSL=false");
//			st = conn.createStatement();
//			String color = "red";
//			// rs = st.executeQuery("SELECT * from Student where fname='" + name + "'");
//			ps = conn.prepareStatement("SELECT * FROM Color WHERE colorType=?");
//			ps.setString(1, color); // set first variable in prepared statement
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				int colorID = rs.getInt("colorID");
//				String colorType = rs.getString("colorType");
//				System.out.println ("colorType = " + colorType);
//				System.out.println("colorID = " + colorID);
//			}
//		} catch (SQLException sqle) {
//			System.out.println ("SQLException: " + sqle.getMessage());
//		} catch (ClassNotFoundException cnfe) {
//			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//				if (ps != null) {
//					ps.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException sqle) {
//				System.out.println("sqle: " + sqle.getMessage());
//			}
//		}
//	}
	
	
	
	
//	public static void main (String[] args) {
//		Connection conn = null;
//		Statement st = null;
//		PreparedStatement ps = null;
//		PreparedStatement ps2 = null;
//		ResultSet rs = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/Game?user=root&password=Devindaher10&useSSL=false");
//			st = conn.createStatement();
//			//String color = "red";
//			// rs = st.executeQuery("SELECT * from Student where fname='" + name + "'");
//			ps = conn.prepareStatement("SELECT * FROM UserInfo WHERE username=?");
//			//colorPrep = conn.prepareStatement("SELECT * FROM Color WHERE colorType=?");
//			//colorPrep.setString(1, color); // set first variable in prepared statement
//			ps.setString(1, "ddaher");
//			System.out.println("just before checking username in while loop");
//			rs = ps.executeQuery();
//			while (rs.next()) {
//				if(rs.getString("username") == "ddaher") {
//					return;
//				}
//				System.out.println("in while loop");
//				//String user = rs.getString("username");
//				//System.out.println ("username = " + user);
//				//return false;
//			}
//			ps.close();
//			ps = conn.prepareStatement("INSERT INTO UserInfo(username, passwords, armorID, colorID, weaponID, score) VALUES(?,?,?,?,?,?)");
//			System.out.println("after creating statement");
//			//ps.setInt(1, 2);
//			ps.setString(1, "dev");
//			System.out.println("after second 1st string");
//			ps.setString(2, "d1");
//			ps.setInt(3, 1);
//			ps.setInt(4, 1);
//			ps.setInt(5, 1);
//			ps.setInt(6, 0);
//			System.out.println("where am i?");
//			ps.executeUpdate();
//		} catch (SQLException sqle) {
//			System.out.println ("SQLException: " + sqle.getMessage());
//		} catch (ClassNotFoundException cnfe) {
//			System.out.println ("ClassNotFoundException: " + cnfe.getMessage());
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (st != null) {
//					st.close();
//				}
//				if (ps != null) {
//					ps.close();
//				}
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException sqle) {
//				System.out.println("sqle: " + sqle.getMessage());
//			}
//		}
//	}
//}
	
	public static void main (String[] args) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Game?user=root&password=Devindaher10&useSSL=false");
			st = conn.createStatement();
			//String color = "red";
			// rs = st.executeQuery("SELECT * from Student where fname='" + name + "'");
//			ps = conn.prepareStatement("UPDATE UserInfo SET armorID=? colorID=? weaponID=? score=? WHERE username=? AND passwords=?");
			ps = conn.prepareStatement("UPDATE UserInfo WHERE username=?");
			//colorPrep = conn.prepareStatement("SELECT * FROM Color WHERE colorType=?");
			//colorPrep.setString(1, color); // set first variable in prepared statement
			System.out.println("before setting");
			ps.setInt(1, 50);
			ps.setString(2,"ddaher");
//			ps.setString(2, "devin1");
//			ps.setInt(3, 4);
//			ps.setInt(4, 10);
//			ps.setInt(5, 100);
//			ps.setInt(6, 55);
			//rs = ps.executeQuery();
			ps.executeUpdate();
			//System.out.println("executed query");
//			while (rs.next()) {					
//				String user = rs.getString("username");
//				System.out.println ("username = " + user);				
//			}
//			ps.setInt(2, 45);
//			ps.setInt(3, 4);
//			ps.setInt(4, 10);
//			ps.setInt(5, 100);
//			ps.setInt(6, 55);
//			ps.executeUpdate();
			System.out.println("executed update");
//			while (rs.next()) {
//				System.out.println("checking if username = username" + rs.getString("username"));
//				if(rs.getString("username").equals("ddaher")) {
//					
//				}
//				//String user = rs.getString("username");
//				//System.out.println ("username = " + user);
//				//return false;
//			}
//			System.out.println("about to insert into userinfo table");
//			ps.close();
//			ps = conn.prepareStatement("INSERT INTO UserInfo(username, passwords, armorID, colorID, weaponID, score) VALUES(?,?,?,?,?,?)");
//			ps.setInt(3, 4);
//			ps.setInt(4, 10);
//			ps.setInt(5, 100);
//			ps.setInt(6, 55);
//			ps.executeUpdate(); //is it updating?
//			System.out.println("get here?");
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
