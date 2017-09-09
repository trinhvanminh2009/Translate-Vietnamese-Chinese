package translate_vietNamese_chinese.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionToSQL {
	public static final String URL = "jdbc:sqlserver://localhost;"
			+ "databaseName=translate_vietnamese_chinese;integratedSecurity=true;";
	public static Connection connection;

	public ConnectionToSQL(){}

	public static boolean connect() {
		 try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(URL);
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return false;
	}
	
	public static ResultSet excuteQuery(PreparedStatement sql){
		try {
			return sql.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void insertFileChinese(String filePath, long fileSize)
	{
		ConnectionToSQL.connect();
		Statement statement = null; 
		try {
			statement = connection.createStatement();
			String sql = "insert into fileChinese values('"+filePath+"',"+(int)fileSize +")";
			statement.executeUpdate(sql);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			  //finally block used to close resources
		      try{
		         if(statement!=null)
		            connection.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		
	}
	
	public static void insertFileVietnamese(String filePathVietnanese, long fileSize, String filePathChinese )
	{
		ConnectionToSQL.connect();
		Statement statement = null; 
		try {
			statement = connection.createStatement();
			String sql = "insert into fileVietnamese values('"+filePathVietnanese+"',"+(int)fileSize +", '"+filePathChinese+"')";
			statement.executeUpdate(sql);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			  //finally block used to close resources
		      try{
		         if(statement!=null)
		            connection.close();
		      }catch(SQLException se){
		      }// do nothing
		      try{
		         if(connection!=null)
		            connection.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
	}
	
	public static void main(String []args)
	{
		//ConnectionToSQL.insertFileChinese("asdadad", 11);
		//ConnectionToSQL.insertFileVietnamese("Dasdasd", 111, "asdadad");
	}
	
}
