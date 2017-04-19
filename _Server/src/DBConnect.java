import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class DBConnect {
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public DBConnect(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza","root","");
			
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
	
	public int contains(String login, String password){
		String query = "SELECT * FROM users WHERE login=? AND password=?";
		try{
			pst = con.prepareStatement(query);
			pst.setString(1, login);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if(rs.next()){
				return 1;
			}
			else return 0;
		}catch(Exception ex){
			System.out.println(ex);
			return 10;
		}
	}
}
