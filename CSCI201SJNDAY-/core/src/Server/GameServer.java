package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import com.mygdx.game.ShootsAndLooters;

import gameClass.UserData;
import screens.LoginScreen;

public class GameServer {
	private Vector<GameServerThread> mServerThreads;
	private int colorID;
	private int armorID;
	private int userID;
	private int weaponID;
	private String color;
	private String armor;
	private String weapon;
	private String userInfo;
	private ShootsAndLooters mGame;
	private LoginScreen mLoginScreen;
	private UserData mUserData;
	public GameServer(int port) throws IOException {
		System.out.println("Binding to port " + port);
		ServerSocket ss = new ServerSocket(port); //creates socket or throws exception if no valid socket
		System.out.println("Successfully started the Shoots n Looters server on port " + port);
		mServerThreads = new Vector<GameServerThread>();
		while(true) {
			Socket s = ss.accept(); // blocking so waits for valid ip address and valid port before moving on
			System.out.println("Connection from: " + s.getInetAddress());
			GameServerThread st = new GameServerThread(s, this);
			mGame = new ShootsAndLooters();
			System.out.println("now i'm after creating server thread, maybe create the game here");
			mServerThreads.add(st);
		}
	}
	
	public UserData readData(String username) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Game?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			ps = conn.prepareStatement("SELECT * FROM UserInfo WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {				
				String user = rs.getString("username");
				System.out.println ("username = " + user);
				int armorID = rs.getInt("armorID");
				int colorID = rs.getInt("colorID");
				int weaponID = rs.getInt("weaponID");
				int score = rs.getInt("score");
				String colors = rs.getString("colors");
				Boolean[] ownedColors = stringToColorArray(colors);
				mUserData = new UserData(username, false, score, armorID, weaponID, colorID, ownedColors);
				return mUserData;
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
		return mUserData; //HMM do i want this here??!
	}
	
	public boolean login(String userName, String password) {
		//check sql database for this username and if password matches
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Game?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			ps = conn.prepareStatement("SELECT * FROM UserInfo WHERE username=? and passwords=?");
			ps.setString(1, userName);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while (rs.next()) {				
				String user = rs.getString("username");
				String pass = rs.getString("passwords");
				System.out.println ("username = " + user);
				System.out.println ("password = " + pass);
				return true;
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
		return false;
	}
	
	public boolean signup(String username, String password) {
		//check sql database for this username and if password matches
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/Game?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			//String color = "red";
			// rs = st.executeQuery("SELECT * from Student where fname='" + name + "'");
			ps = conn.prepareStatement("SELECT * FROM UserInfo WHERE username=?");
			//colorPrep = conn.prepareStatement("SELECT * FROM Color WHERE colorType=?");
			//colorPrep.setString(1, color); // set first variable in prepared statement
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("checking if username = username" + rs.getString("username"));
				if(rs.getString("username").equals(username)) {
					return false;
				}
				//String user = rs.getString("username");
				//System.out.println ("username = " + user);
				//return false;
			}
			System.out.println("about to insert into userinfo table");
			ps.close();
			ps = conn.prepareStatement("INSERT INTO UserInfo(username, passwords, armorID, colorID, colors, weaponID, score) VALUES(?,?,?,?,?,?,?)");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.setString(5, "T.F.F.F.F.F");
			ps.setInt(6, 0);
			ps.setInt(7, 500);
			ps.executeUpdate();
			System.out.println("get here?");
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
		return true;
	}
	
	/**
	 * Saves data
	 * @param saveData
	 * @author joshl
	 */
	public void saveData(UserData saveData) {
			Connection conn = null;
			Statement st = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/Game?user=root&password=root&useSSL=false");
				ps = conn.prepareStatement("UPDATE UserInfo s " + 
						"	SET armorID = ?, colorID=?, weaponID=?, score=?, colors=? " + 
						"	WHERE s.username = ?");
				ps.setInt(1, saveData.getArmorLevel());
				ps.setInt(2, saveData.getColorIndex());
				ps.setInt(3, saveData.getWeaponLevel());
				ps.setInt(4, saveData.getCurrentMoney());
				ps.setString(5, colorArrayToString(saveData.getColorOwned()));
				ps.setString(6, saveData.getUserName());			
				ps.executeUpdate();
//				conn.commit();
				System.out.println("did we save the data? current money is: " + saveData.getCurrentMoney());
				System.out.println("did we save the data? current color is: " + colorArrayToString(saveData.getColorOwned()));
				System.out.println("did we save the data? weapon level is: " + saveData.getWeaponLevel());
				System.out.println("did we save the data? armor level is: " + saveData.getArmorLevel());
				System.out.println("did we save the data? username is: " + saveData.getUserName());
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
					sqle.printStackTrace();
				}
			}
	}
	
	/**
	 * Converts a boolean array of colors to a string
	 * @return A string delimited by periods
	 * @author joshl
	 */
	public String colorArrayToString(Boolean[] colorOwned) {
		String colorStringArray = "";
		for(int i = 0; i < 6; i++) {
			if(i == 0) {
				if(colorOwned[i] == true) {
					colorStringArray += "T";
				} else {
					colorStringArray += "F";
				}
			} else {
				if(colorOwned[i] == true) {
					colorStringArray += ".T";
				} else {
					colorStringArray += ".F";
				}
			}
		}
		return colorStringArray;
	}
	
	/**
	 * Converts a boolean array of colors to a string.
	 * Format must be in X.X.X.X.X.X, where X is either T or F.
	 * @return An array of booleans
	 * @author joshl
	 */
	public Boolean[] stringToColorArray (String colorOwned) {
		Boolean[] colorArray = new Boolean[6];
		String[] colorStringArray = colorOwned.split("\\.");
		for(int i = 0; i < 6; i++) {
				if(colorStringArray[i].equals("T")) {
					colorArray[i] = true;
				} else {
					colorArray[i] = false;
				}
		}
		return colorArray;
	}
		
		//deals with the input stream
		//asks for a port number from the user and creates a gameroom with the valid port
		public static void main(String [] args) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			GameServer server;
			boolean isConnected = false;
			System.out.println("Welcome to the Shooters 'n Looters server!");
			do {
				try {
					server = new GameServer(6789);
				}catch(IllegalArgumentException iae) {
					System.out.println("Invalid port number");
				}catch (SocketException se) {
					System.out.println("Invalid port number");
				}catch (IOException e) {
					System.out.println("Invalid port number");
				}
			}while(!isConnected);
		}
}
